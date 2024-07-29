package com.example.financialplanner.ui.theme.view

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.financialplanner.databinding.ItemPagerBinding
import com.example.financialplanner.ui.theme.HomeActivity
import com.example.financialplanner.ui.theme.SELECTED_DATE
import com.example.financialplanner.ui.theme.adapter.HomeContentAdapter
import com.example.financialplanner.ui.theme.base.BaseFragment
import com.example.financialplanner.ui.theme.base.BaseViewModel
import com.example.financialplanner.ui.theme.viewmodel.HomeViewModel

class HomeContentFragment : BaseFragment<ItemPagerBinding>(ItemPagerBinding::inflate){

    override val viewModel: BaseViewModel by viewModels()
    private val parentVM: HomeViewModel by activityViewModels()

    private var monthYear: String = ""

    private val adapter : HomeContentAdapter by lazy {
        HomeContentAdapter()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        monthYear = arguments?.getString("monthYear") ?: ""
    }

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        initRcv()
        initObserver()
    }

    private fun initRcv(){
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.isVerticalScrollBarEnabled = false
    }

    private fun initObserver(){
        parentVM.listTransaction.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }
}