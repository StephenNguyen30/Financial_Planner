package com.example.financialplanner.ui.theme

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.financialplanner.R
import com.example.financialplanner.databinding.HomeActivityBinding
import com.example.financialplanner.ui.theme.base.BaseActivity
import com.example.financialplanner.ui.theme.login.AuthViewModel
import com.example.financialplanner.ui.theme.viewmodel.HomeViewModel
import com.example.financialplanner.ui.theme.viewmodel.TransactionViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.database
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class HomeActivity : BaseActivity<HomeActivityBinding>(HomeActivityBinding::inflate) {
    override val viewModel: HomeViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        initNavHost()
        initUi()
        initObservers()
    }

    private fun initUi() {
        initBottomBar()
    }

    private fun initNavHost() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun initObservers() {
        viewModel.userLiveData.observe(this) {
            if (it.id.isNotEmpty()) {
                navController.navigate(R.id.homeFragment)
            }
            initTopAppBar(it.id.isNotEmpty())
        }

    }

    private fun initTopAppBar(isLogged: Boolean = false) {
        val avaItem = binding.layoutTopAppBar.topAppBar.menu.findItem(R.id.itemAva)
        avaItem.isVisible = isLogged
        Log.d("KKK retrieve data to Home", "${viewModel.userLiveData.value?.id}")

        val avatarUrl = viewModel.userLiveData.value?.imageUrl ?: R.drawable.ic_def_player
        val borderDrawable = ContextCompat.getDrawable(this, R.drawable.bg_border_avatar)
        Glide.with(this)
            .asBitmap()
            .load(avatarUrl)
            .placeholder(R.drawable.ic_def_player)
            .error(R.drawable.ic_def_player)
            .circleCrop()
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    val layer = arrayOf(BitmapDrawable(resources, resource), borderDrawable)
                    val layerDrawable = LayerDrawable(layer)
                    avaItem?.icon = layerDrawable
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    avaItem.setIcon(R.drawable.ic_def_player)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    avaItem.setIcon(R.drawable.ic_def_player)
                }
            })
    }

    private fun initBottomBar() {
        val timeZone = TimeZone.getDefault()
        val sdf = SimpleDateFormat("dd/M", Locale.ENGLISH)
        val cal = Calendar.getInstance(timeZone, Locale.ENGLISH)
        binding.layoutBottomAppBar.menu.findItem(R.id.navItemHome).title = sdf.format(cal.time)
        binding.layoutBottomAppBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navItemHome -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.navItemStats -> {
                    navController.navigate(R.id.statsFragment)
                    true
                }

                R.id.navItemAccount -> {
                    navController.navigate(R.id.accountFragment)
                    true
                }

                else -> false
            }
        }
    }

    fun showBottomNavigation(isShow: Boolean) {
        binding.layoutBottomAppBar.isVisible = isShow
    }


    fun showTopAppBar(isShow: Boolean, title: String = "") {
        binding.layoutTopAppBar.appBar.isVisible = isShow
        binding.layoutTopAppBar.topAppBar.title = ""
        binding.layoutTopAppBar.tvTitle.text = title
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (view != null) {
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
        return super.onTouchEvent(event)
    }

}