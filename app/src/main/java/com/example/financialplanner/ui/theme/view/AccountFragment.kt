package com.example.financialplanner.ui.theme.view

import android.os.Bundle
import com.example.financialplanner.databinding.AccountsFragmentBinding
import com.example.financialplanner.ui.theme.base.BaseFragment
import com.example.financialplanner.ui.theme.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment: BaseFragment<AccountsFragmentBinding>(AccountsFragmentBinding::inflate) {
    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
    }

}