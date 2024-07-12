package com.example.financialplanner.ui.theme.base

import android.Manifest
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.viewbinding.ViewBinding
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.financialplanner.ui.theme.base.adapter.BaseBuilder
import com.example.financialplanner.ui.theme.base.adapter.BaseDialog
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase


abstract class BaseFragment<VB : ViewBinding>(private val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> VB) :
    Fragment() {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    abstract val viewModel: BaseViewModel
    private var notifyDialog: Dialog? = null

    private val Fragment.baseActivity: BaseActivity<*>?
        get() = activity as? BaseActivity<*>


    // Make it open, so it can be overridden in child fragments
    open fun VB.initialize() {}

    protected open var onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                onFragmentBackPressed()
            }
        }

    open fun onFragmentBackPressed() {
        onBackPressedCallback.isEnabled = false
        activity?.onBackPressedDispatcher?.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseActivity?.firebaseAnalytics = Firebase.analytics
        activity?.onBackPressedDispatcher?.addCallback(this, onBackPressedCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("KKK view created", "onViewCreated...")

        setCurrentScreen(this@BaseFragment::class.java.simpleName)

        viewModel.throwable.observe(viewLifecycleOwner) {
            if (it == null) return@observe
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            val now = SystemClock.elapsedRealtime()
            val isLoading = it.any { millis -> now - millis < LoadingDialog.TIME_OUT }
            setLoading(isLoading, false)
        }
        if (savedInstanceState == null) {
            onFragmentInitialized()
        }

        onFragmentCreated(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateMethod.invoke(inflater, container, false)

        // Calling the extension function
        binding.initialize()

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("onDestroy", "onDestroy...")
    }

    override fun onPause() {
        super.onPause()
        Log.i("onPause", "onPause...")
    }

    override fun onResume() {
        super.onResume()
        Log.i("onResume", "onResume...")
    }

    fun showToast(msg: String) {
        val context = context ?: return
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun BaseBuilder.bind(): BaseDialog<*>? {
        val context = context ?: return null
        val dialog = build(context)
        dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON)
        notifyDialog?.dismiss()
        notifyDialog = dialog
        return dialog
    }

    private fun setCurrentScreen(screenName: String) = baseActivity?.firebaseAnalytics?.let {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenName)
        it.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    abstract fun onFragmentCreated(savedInstanceState: Bundle?)

    open fun onFragmentInitialized() {}

    fun Fragment.getFragmentNavController(@IdRes id: Int) = activity?.let {
        return@let Navigation.findNavController(it, id)
    }
}

fun BaseFragment<*>.setLoading(isLoading: Boolean, alignCenterRight: Boolean = true) {
    if (activity is BaseActivity<*>) {
        (activity as BaseActivity<*>).setLoading(isLoading, alignCenterRight)
    }
}