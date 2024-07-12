package com.example.financialplanner.ui.theme.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

abstract class BaseActivity<VB : ViewBinding>(
    private val inflate: (LayoutInflater) -> VB
) : AppCompatActivity() {
    var _binding: VB? = null
    val binding: VB
        get() = _binding!!

    abstract val viewModel: BaseViewModel

    private val loadingDialog by lazy { LoadingDialog() }
    lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        _binding = inflate.invoke(layoutInflater)
        setContentView(binding.root)
        observeBase()
        if (savedInstanceState == null) {
            onActivityInitialized()
        }
        onActivityCreated(savedInstanceState)
    }

    protected fun trackingEvent(eventName: String, value: String) = firebaseAnalytics.run {
        val bundle = Bundle()
        bundle.putString(eventName, value)
        logEvent(eventName, bundle)
    }

    protected open fun onActivityInitialized() {
        // for override
    }

    protected abstract fun onActivityCreated(savedInstanceState: Bundle?)

    protected open fun onThrowableComplete(throwable: Throwable) {
        // for override
    }

    open fun setLoading(isLoading: Boolean, alignCenterRight: Boolean = false) {
        if (isLoading) showLoading(alignCenterRight)
        else hideLoading()
    }

    private fun showLoading(alignCenterRight: Boolean = true) {
        loadingDialog.show(this, alignCenterRight)
    }

    private fun hideLoading() {
        loadingDialog.dismiss()
    }

    fun showShareBottomSheet(url: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun observeBase() {
        viewModel.throwable.observe(this) {
            if (it == null) return@observe
            handleThrowable(it)
        }
    }

    open fun onNetworkFailure(e: NoInternetException) {
        // For override
    }

}

fun BaseActivity<*>.handleThrowable(it: Throwable, onComplete: () -> Unit = {}) {
    when (it) {
        is NoInternetException -> {
            this.onNetworkFailure(it)
        }

        else -> {}
    }
}
