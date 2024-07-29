package com.example.financialplanner.ui.theme.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.financialplanner.ui.theme.HomeUiModel
import com.example.financialplanner.ui.theme.base.BaseViewModel
import com.example.financialplanner.ui.theme.base.asLiveData
import com.example.financialplanner.ui.theme.datastore.DataStorePreference
import com.example.financialplanner.ui.theme.model.CalendarModel
import com.example.financialplanner.ui.theme.model.CategoryModel
import com.example.financialplanner.ui.theme.model.TransactionModel
import com.example.financialplanner.ui.theme.model.UserModel
import com.example.financialplanner.ui.theme.respository.FirebaseRepository
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
    private val firebase: FirebaseRepository,
    private val dataStore: DataStorePreference,
) : BaseViewModel() {

    private val _userLiveData = MutableLiveData<UserModel>()
    val userLiveData = _userLiveData.asLiveData()

    var userId: String = ""

    private val _listTransaction = MutableLiveData<List<TransactionModel>>()
    val listTransaction = _listTransaction.asLiveData()

    init {
        viewModelScope.launch {
            dataStore.getUser().collect {
                _userLiveData.value = it
                userId = it.id
                firebase.getTransactionsById(userId).collect{transactions->
                    _listTransaction.value = transactions
                    Log.d("Firebase", "${listTransaction.value}")
                }
            }
        }
    }
}