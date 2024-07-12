package com.example.financialplanner.ui.theme.base

import android.app.Dialog
import android.content.Context
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import androidx.core.os.HandlerCompat
import com.example.financialplanner.R
import com.example.financialplanner.databinding.DialogLoadingBinding

class LoadingDialog {
    private var dialog: Dialog? = null
    private val timeoutHandler by lazy { HandlerCompat.createAsync(Looper.getMainLooper()) }
    private var dismissAction = Runnable {
        dismiss()
    }

    /**
     * @param alignCenterRight: Set dialog align center-right of content
     * alignCenterRight = true => dialog show on center of right content
     * alignCenterRight = false => dialog show on center of screen
     */
    fun show(context: Context, alignCenterRight: Boolean = false) {
        if (dialog == null) {
            val inflater = LayoutInflater.from(context)
            val binding = DialogLoadingBinding.inflate(inflater)
            dialog = Dialog(context).apply {
                setCancelable(false)
                setContentView(binding.root)
                if (alignCenterRight) {
                    binding.lnContainer.setBackgroundResource(R.drawable.inset_progress_dialog)
                }
                create()
            }
            dialog?.window?.apply {
                setGravity(Gravity.CENTER)
                decorView.setBackgroundResource(android.R.color.transparent)
                setDimAmount(0.5F)
            }
        }
        if (dialog?.isShowing == false) dialog?.show()
        timeoutHandler.removeCallbacks(dismissAction)
        timeoutHandler.postDelayed(dismissAction, TIME_OUT)
    }

    fun dismiss() {
        if (dialog?.isShowing == true) dialog?.dismiss()
    }

    fun onDestroy() {
        timeoutHandler.removeCallbacks(dismissAction)
        dismiss()
    }

    companion object{
        const val TIME_OUT = 30_000L
    }
}
