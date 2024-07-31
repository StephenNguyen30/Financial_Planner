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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebase: FirebaseRepository,
    private val dataStore: DataStorePreference,
) : BaseViewModel() {

    private val _userLiveData = MutableLiveData<UserModel>()
    val userLiveData = _userLiveData.asLiveData()

    var userId: String = ""

    private val _listTransactionByDate = MutableLiveData<Map<String, List<TransactionModel>>>()
    val listTransactionByDate = _listTransactionByDate.asLiveData()

    private val timeZone = TimeZone.getDefault()
    private val sdf = SimpleDateFormat("MMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(timeZone, Locale.ENGLISH)

    var previousPage = 0

    init {
        viewModelScope.launch {
            dataStore.getUser().collect {
                _userLiveData.value = it
                userId = it.id
                preloadTransactions()
            }
        }
    }

    fun getTransactionsByDate(userId: String, monthYear: String) {
        viewModelScope.launch {
            val transactions = withContext(Dispatchers.IO) {
                firebase.getTransactionByDate(userId, monthYear).first()
            }
            _listTransactionByDate.value =
                _listTransactionByDate.value.orEmpty().plus(monthYear to transactions)
        }
    }

    private fun preloadTransactions() {
        val currentMonth = sdf.format(cal.time)
        val adjacentMonths = listOf(
            getMonthOffset(currentMonth, -1),
            currentMonth,
            getMonthOffset(currentMonth, 1)
        )

        viewModelScope.launch {
            val transactionsMap = adjacentMonths.associateWith { month ->
                withContext(Dispatchers.IO) {
                    firebase.getTransactionByDate(userId, month).last()
                }
            }
            _listTransactionByDate.value = transactionsMap
        }
    }

    fun getMonthOffset(monthYear: String, offset: Int): String {
        cal.time = sdf.parse(monthYear)!!
        cal.add(Calendar.MONTH, offset)
        return sdf.format(cal.time)
    }
}