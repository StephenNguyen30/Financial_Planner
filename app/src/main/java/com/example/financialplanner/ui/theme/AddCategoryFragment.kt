package com.example.financialplanner.ui.theme

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.financialplanner.R
import com.example.financialplanner.databinding.AddCategoryFragmentBinding
import com.example.financialplanner.ui.theme.base.BaseFragment
import com.example.financialplanner.ui.theme.base.BaseViewModel
import com.example.financialplanner.ui.theme.model.CategoryModel
import com.example.financialplanner.ui.theme.viewmodel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCategoryFragment : BaseFragment<AddCategoryFragmentBinding>(AddCategoryFragmentBinding::inflate) {
    override val viewModel: TransactionViewModel by activityViewModels()

    override fun onFragmentCreated(savedInstanceState: Bundle?) {
        initUi()
        initListener()
    }
    private fun initUi(){
        (activity as HomeActivity).showTopAppBar(false,"")
        binding.addAppBar.tvTitle.text = getString(R.string.add)
    }

    private fun initListener(){
        binding.addAppBar.ivBack.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.tvSave.setOnClickListener{
            val editText = binding.etName.text ?: ""
            if(editText.isEmpty()) {
                Toast.makeText(activity, "Enter name to add", Toast.LENGTH_SHORT).show()
            }
            else{
                val newCategory = CategoryModel(binding.etName.text.toString(), 0, false)
                viewModel.addCategory(newCategory)
                Log.d("KKK button on click", "Clicked successfully")
                findNavController().popBackStack()
            }
        }
    }
}