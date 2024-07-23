package com.example.financialplanner.ui.theme.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.financialplanner.ui.theme.HomeUiModel
import com.example.financialplanner.ui.theme.base.BaseViewModel
import com.example.financialplanner.ui.theme.base.asLiveData
import com.example.financialplanner.ui.theme.datastore.DataStorePreference
import com.example.financialplanner.ui.theme.model.CalendarModel
import com.example.financialplanner.ui.theme.model.CategoryModel
import com.example.financialplanner.ui.theme.model.UserModel
import com.example.financialplanner.ui.theme.usecases.GetTransAccountUseCase
import com.example.financialplanner.ui.theme.usecases.GetTransCategoriesUseCase
import com.example.financialplanner.ui.theme.usecases.GetTransIncomeCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dataStore: DataStorePreference,
    private val getTransCategoriesUseCase: GetTransCategoriesUseCase,
) : BaseViewModel() {

    private val _userLiveData = MutableLiveData<UserModel>()
    val userLiveData = _userLiveData.asLiveData()

    private val _categories = MutableLiveData<List<CategoryModel>>()
    val categories = _categories.asLiveData()

    private var originalList: List<CategoryModel> = emptyList()


    init {
        viewModelScope.launch {
            getTransCategory()
            dataStore.getUser().collect {
                _userLiveData.value = it
            }
        }
    }

    fun getTransCategory() {
        viewModelScope.launch {
            val category = getTransCategoriesUseCase.invoke()
            _categories.value = category
        }
    }

}