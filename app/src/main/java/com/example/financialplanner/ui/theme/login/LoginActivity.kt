package com.example.financialplanner.ui.theme.login

import android.content.Intent
import androidx.credentials.CredentialManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatButton
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.financialplanner.R
import com.example.financialplanner.databinding.LoginFragmentBinding
import com.example.financialplanner.ui.theme.HomeActivity
import com.example.financialplanner.ui.theme.base.BaseActivity
import com.example.financialplanner.ui.theme.model.UserModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<LoginFragmentBinding>(LoginFragmentBinding::inflate) {

    override val viewModel: AuthViewModel by viewModels()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        initUI()
        initObserver()
        initListener()
    }

    private fun initUI() {
        binding.tvNameApp.text = getString(R.string.app_login_name)
        binding.tvNoAccount.text = getString(R.string.do_not_have_account)
        binding.tvSignUpOnClick.text = getString(R.string.sign_up)
    }

    private fun initObserver() {
        viewModel.userLiveData.observe(this) {
            if (it.id.isNotEmpty()) {
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            Log.d("KKK retrieve data", "$it")
        }
    }

    private fun initListener() {
        findViewById<AppCompatButton>(R.id.btnSignInGoogle).setOnClickListener {
            googleSignInButton()
        }
    }

    private fun googleSignInButton() {
        val credentialManager = CredentialManager.create(this)
        val signInWithGoogleOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(CLIENT_ID)
            .setAutoSelectEnabled(false)
            .build()


        val request = androidx.credentials.GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()
        lifecycleScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = this@LoginActivity,
                )

                Log.d(TAG, "KKK result $request")
                viewModel.handleSignIn(result)
            } catch (e: Exception) {
                Log.d(TAG, "$e")
            }
        }
    }


    companion object {
        private val TAG = LoginActivity::class.java.simpleName
        const val CLIENT_ID =
            "984528066018-ia3v09b3mplakva3k85kdcib98jpfr60.apps.googleusercontent.com"
    }
}