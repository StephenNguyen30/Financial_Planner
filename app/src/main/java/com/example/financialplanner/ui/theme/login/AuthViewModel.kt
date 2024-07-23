package com.example.financialplanner.ui.theme.login


import android.util.Log
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.financialplanner.ui.theme.base.BaseViewModel
import com.example.financialplanner.ui.theme.base.asLiveData
import com.example.financialplanner.ui.theme.datastore.DataStorePreference
import com.example.financialplanner.ui.theme.model.UserModel
import com.example.financialplanner.ui.theme.respository.FirebaseRepository
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebase: FirebaseRepository,
    private val dataStore: DataStorePreference
) : BaseViewModel() {

    private val _userLiveData = MutableLiveData<UserModel>()
    val userLiveData = _userLiveData.asLiveData()

    fun handleSignIn(result: GetCredentialResponse){
        viewModelScope.launch {
            when (val credential = result.credential) {
                is CustomCredential -> {
                    if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        try {
                            val googleIdTokenCredential = GoogleIdTokenCredential
                                .createFrom(credential.data)
                            val userModel = UserModel(
                                id = googleIdTokenCredential.idToken,
                                imageUrl = googleIdTokenCredential.profilePictureUri.toString(),
                                phoneNumber = googleIdTokenCredential.phoneNumber,
                                firstName = googleIdTokenCredential.givenName,
                                lastName = googleIdTokenCredential.familyName,
                                email = googleIdTokenCredential.id,
                            )
                            _userLiveData.value = userModel
                            try {
                                addUser(userModel)
                            }catch (e: Exception){
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
    private fun addUser(user: UserModel) {
        viewModelScope.launch {
            try {
                firebase.addUser(user)
            } catch (e: Exception) {
                Log.e("Exception", "Unable to save user")
            }
        }
    }
}

