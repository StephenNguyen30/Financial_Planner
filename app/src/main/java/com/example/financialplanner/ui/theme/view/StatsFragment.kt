package com.example.financialplanner.ui.theme.view

import android.os.Bundle
import com.example.financialplanner.databinding.StatsFragmentBinding
import com.example.financialplanner.ui.theme.base.BaseFragment
import com.example.financialplanner.ui.theme.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatsFragment : BaseFragment<StatsFragmentBinding>(StatsFragmentBinding::inflate) {
    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
}