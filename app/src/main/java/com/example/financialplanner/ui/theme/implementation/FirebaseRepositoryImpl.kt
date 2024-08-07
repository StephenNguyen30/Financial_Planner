package com.example.financialplanner.ui.theme.implementation


import android.util.Log
import com.example.financialplanner.ui.theme.datastore.DataStorePreference
import com.example.financialplanner.ui.theme.model.CategoryModel
import com.example.financialplanner.ui.theme.model.TransactionModel
import com.example.financialplanner.ui.theme.model.UserModel
import com.example.financialplanner.ui.theme.model.toModel
import com.example.financialplanner.ui.theme.respository.FirebaseRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseRepositoryImpl @Inject constructor(
    firebase: FirebaseDatabase,
    private val dataStore: DataStorePreference
) : FirebaseRepository {
    private val userReference: DatabaseReference = firebase.getReference("users")
    private val repositoryScope = CoroutineScope(Dispatchers.IO)
    private var currentListener: ValueEventListener? = null

    override suspend fun addUser(user: UserModel): UserModel {
        val userId =
            userReference.push().key ?: ""
        user.id = userId
        var updatedUser = UserModel()
        suspendCancellableCoroutine { continuation ->
            userReference.child(userId).setValue(user)
                .addOnSuccessListener {
                    Log.d("Firebase", "User added successfully")
                    updatedUser = user.copy(id = userId)
                    repositoryScope.launch {
                        dataStore.saveUser(updatedUser)
                    }
                    continuation.resume(Unit)
                }
                .addOnFailureListener { e ->
                    Log.e("Firebase", "Error adding user", e)
                    continuation.resumeWithException(e)
                }
        }
        return updatedUser
    }


    override suspend fun addTransaction(userId: String, transaction: TransactionModel) =
        suspendCancellableCoroutine { continuation ->
            val transactionId =
                userReference.child(userId).child("transactions").push().key
            val trans = transaction.copy(categories = transaction.categories.toModel())
            if (transactionId != null) {
                userReference.child(userId).child("transactions").child(transactionId)
                    .setValue(trans)
                    .addOnSuccessListener {
                        Log.d("Firebase", "Transaction added successfully")
                        continuation.resume(Unit)
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firebase", "Error adding transaction", e)
                        continuation.resumeWithException(e)
                    }
            } else {
                continuation.resumeWithException(Exception("Failed to generate transaction ID"))
            }
        }

    override suspend fun addCategories(
        userId: String,
        transactionId: String,
        categories: List<CategoryModel>
    ) = suspendCancellableCoroutine { continuation ->
        userReference.child(userId).child("categories")
            .child(transactionId).child("categories").setValue(categories)
            .addOnSuccessListener {
                Log.d("Firebase", "Categories added successfully")
                continuation.resume(Unit)
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Error adding categories", e)
                continuation.resumeWithException(e)
            }
    }

    override suspend fun addAccounts(userId: String, accounts: List<CategoryModel>) {
        val categoriesReference = userReference.child(userId).child("transactions/accounts")
        accounts.forEach { account ->
            val categoryId = categoriesReference.push().key ?: "" // Ensure the userOwnerId is set
            categoriesReference.child(categoryId).setValue(account)
                .addOnSuccessListener {
                    Log.d("Firebase", "Account added successfully")
                }
                .addOnFailureListener { e ->
                    Log.e("Firebase", "Error adding account", e)
                }
        }
    }

    override suspend fun getUserById(userId: String): Flow<UserModel?> =
        callbackFlow {
            val listener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(UserModel::class.java)
                    trySend(user).isSuccess
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w("Firebase", "Error getting user", databaseError.toException())
                }
            }

            userReference.child(userId).addValueEventListener(listener)

            awaitClose {
                userReference.child(userId).removeEventListener(listener)
            }
        }

    override suspend fun getTransactionsById(userId: String) = callbackFlow {
        currentListener?.let {
            userReference.child(userId).child("transactions").removeEventListener(it)
        }
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val transactions = dataSnapshot.children.mapNotNull { childSnapshot ->
                    childSnapshot.getValue(TransactionModel::class.java)
                }.map {
                    it.copy(categories = it.categories.toModel())
                }
                trySend(transactions)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("Firebase", "Error getting transactions", databaseError.toException())
                close(databaseError.toException())
            }
        }
        val transactionRef = userReference.child(userId).child("transactions")
        Log.d("Firebase ref", "$transactionRef")
        transactionRef.addValueEventListener(listener)
        awaitClose {
            userReference.child(userId).child("transactions").removeEventListener(listener)
        }
    }


    override suspend fun getUserByEmail(email: String): UserModel? =
        suspendCancellableCoroutine { continuation ->
            userReference.orderByChild("email").equalTo(email)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (userSnapshot in snapshot.children) {
                                val user = userSnapshot.getValue(UserModel::class.java)
                                continuation.resume(user)
                                return
                            }
                        } else {
                            continuation.resume(null)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("Firebase", "Error fetching user by email", error.toException())
                        continuation.resumeWithException(error.toException())
                    }
                })
        }

    override suspend fun updateUser(email: String, userIdNew: String) {
        //todo not done yet
    }

    override suspend fun getTransactionByDate(userId: String, monthYear: String) = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val transactions = dataSnapshot.children.mapNotNull { childSnapshot ->
                    val transaction = childSnapshot.getValue(TransactionModel::class.java)
                    if (transaction?.dayDate?.endsWith(monthYear) == true) transaction
                    else null
                }.map {
                    it.copy(categories = it.categories.toModel())
                }

                trySend(transactions)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("Firebase", "Error getting transactions", databaseError.toException())
            }
        }

        val userTransactionRef = userReference.child(userId).child("transactions")

        userTransactionRef.addValueEventListener(listener)

        awaitClose {
            userTransactionRef.removeEventListener(listener)
        }

    }

}