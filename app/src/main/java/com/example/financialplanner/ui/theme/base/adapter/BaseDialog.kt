package com.example.financialplanner.ui.theme.base.adapter

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.viewbinding.ViewBinding

abstract class BaseDialog<VB : ViewBinding>(
    context: Context, private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Dialog(context) {

    private lateinit var _binding: VB
    private val binding: VB
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate.invoke(layoutInflater, null, false)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setContentView(binding.root)
        onDialogCreated(savedInstanceState)
    }

    protected abstract fun onDialogCreated(savedInstanceState: Bundle?)

}

interface BaseBuilder {
    fun build(context: Context): BaseDialog<*>

}
