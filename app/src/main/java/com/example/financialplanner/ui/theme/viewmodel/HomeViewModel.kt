package com.example.financialplanner.ui.theme.viewmodel


import androidx.lifecycle.MutableLiveData
import com.example.financialplanner.ui.theme.HomeUiModel
import com.example.financialplanner.ui.theme.base.BaseViewModel
import com.example.financialplanner.ui.theme.base.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : BaseViewModel() {
    private val _uiModel = MutableLiveData<List<HomeUiModel>>()
    val uiModel = _uiModel.asLiveData()


}