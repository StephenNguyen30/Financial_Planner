package com.example.financialplanner.ui.theme.base


import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


infix fun <T : ViewBinding> ViewGroup?.get(
    inflate: (LayoutInflater, ViewGroup?, Boolean) -> T,
): T = inflate.invoke(LayoutInflater.from(this?.context), this, false)

fun ImageView.loadImageFromUrl(
    url: String?,
    requestOptions: com.bumptech.glide.request.RequestOptions? = null
) {
    val requestOptionsBuilder =
        com.bumptech.glide.request.RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
    requestOptions?.let { requestOptionsBuilder.apply(it) }

    Glide.with(this)
        .load(url.orEmpty())
        .apply(requestOptionsBuilder)
        .into(this)
}

fun ConstraintLayout.addViewWithConstraints(
    viewToAdd: View,
    constraints: ConstraintSet.() -> Unit
) {
    addView(viewToAdd)

    val constraintSet = ConstraintSet()
    constraintSet.clone(this)
    constraintSet.constraints()
    constraintSet.applyTo(this)
}

fun View.getBitmapFromView(): Bitmap {
    val bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    this.draw(canvas)
    return bitmap
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun EditText.setTextAmount(amount: String) {
    val validateAmount = amount.formatAmount()
    this.setText(validateAmount)
}


fun EditText.disableEditText() {
    this.isCursorVisible = false
    this.isFocusable = true
    this.setCustomSelectionActionModeCallback(object : ActionMode.Callback {
        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(
            mode: ActionMode?,
            item: MenuItem?
        ): Boolean {
            return false
        }
    })
}

fun EditText.setTextEditView(string: String) {
    this.setText(string)
    this.clearFocus()
}


