package com.example.financialplanner.ui.theme.login


import android.util.Log
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.financialplanner.ui.theme.base.BaseViewModel
import com.example.financialplanner.ui.theme.base.asLiveData
import com.example.financialplanner.ui.theme.base.shortenId
import com.example.financialplanner.ui.theme.datastore.DataStorePreference
import com.example.financialplanner.ui.theme.model.UserModel
import com.example.financialplanner.ui.theme.respository.FirebaseRepository
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebase: FirebaseRepository,
    private val dataStore: DataStorePreference,
) : BaseViewModel() {

    private val _userLiveData = MutableLiveData<UserModel?>()
    val userLiveData = _userLiveData.asLiveData()


    fun handleSignIn(result: GetCredentialResponse) {
        viewModelScope.launch {
            when (val credential = result.credential) {
                is CustomCredential -> {
                    if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        try {
                            val googleIdTokenCredential = GoogleIdTokenCredential
                                .createFrom(credential.data)

                            val userModel = UserModel(
                                token = googleIdTokenCredential.idToken.shortenId(),
                                imageUrl = googleIdTokenCredential.profilePictureUri.toString(),
                                phoneNumber = googleIdTokenCredential.phoneNumber,
                                firstName = googleIdTokenCredential.givenName,
                                lastName = googleIdTokenCredential.familyName,
                                email = googleIdTokenCredential.id,
                            )
                            try {
                                verifyUser(userModel)
                            } catch (e: Exception) {
                                Log.d("Exception", "Unable to save login credentials bundle")
                            }

                        } catch (
                            e: GoogleIdTokenParsingException
                        ) {
                            Log.e("Exception", "Receive invalid google id token response", e)
                        }
                    }
                }

                else -> {
                    // Catch any unrecognized credential type here.
                    Log.e("Exception", "Unexpected type of credential")
                }
            }
        }

    }

    private fun verifyUser(user: UserModel) {
        viewModelScope.launch {
            try {
                var userModel = firebase.getUserByEmail(user.email)
                Log.d("Firebase", "$userModel")
                if (userModel != null) {
                    Log.d("Firebase", "User exist in firebase: ${user.email}")
                    if (user.token != userModel.token) {
                        Log.d("Firebase", "Ids are different: ${user.token} & ${userModel.token}")
                        //todo update token and add to datastore
                    }
                    dataStore.saveUser(userModel)
                } else {
                    userModel = firebase.addUser(user)
                }
                _userLiveData.value = userModel
            } catch (e: Exception) {
                Log.e("Exception", "Unable to save user")
            }
        }
    }
}

