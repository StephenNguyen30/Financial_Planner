package com.example.financialplanner.ui.theme.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.financialplanner.ui.theme.base.BaseViewModel
import com.example.financialplanner.ui.theme.base.asLiveData
import com.example.financialplanner.ui.theme.datastore.DataStorePreference
import com.example.financialplanner.ui.theme.model.CalendarModel
import com.example.financialplanner.ui.theme.model.CategoryModel
import com.example.financialplanner.ui.theme.model.TransactionModel
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
class TransactionViewModel @Inject constructor(
    private val getTransAccountUseCase: GetTransAccountUseCase,
    private val getTransIncomeCategory: GetTransIncomeCategoryUseCase,
    private val getTransCategoriesUseCase: GetTransCategoriesUseCase,
    private val firebase: FirebaseRepository,
    private val dataStore: DataStorePreference
) : BaseViewModel() {

    private val _account = MutableLiveData<List<CategoryModel>>()
    val account = _account.asLiveData()

    private val _days = MutableLiveData<List<CalendarModel>>()
    val days = _days.asLiveData()

    private val _yearMonth = MutableLiveData(YearMonth.now())
    val yearMonth = _yearMonth.asLiveData()

    private val _incomeCategories = MutableLiveData<List<CategoryModel>>()
    val incomeCategories = _incomeCategories.asLiveData()

    private val _categories = MutableLiveData<List<CategoryModel>>()
    val categories = _categories.asLiveData()

    var userId: String = ""

    var selectedCategory: CategoryModel? = null

    var originalList = emptyList<CategoryModel>()


    init {
        viewModelScope.launch {
            dataStore.getUser().collect {
                userId = it.id
            }
        }
        getTransCategory()
        getTransAccount()
        getIncomeCategory()
    }

    private fun updateCalendar() {
        getTransCalendar(_yearMonth.value!!)
    }

    fun getTransCategory() {
        viewModelScope.launch {
            val category = getTransCategoriesUseCase.invoke()
            _categories.value = category
        }
    }

    fun getTransCalendar(yearMonth: YearMonth) {
        viewModelScope.launch {
            val days = mutableListOf<CalendarModel>()
            val today = LocalDate.now()

            val firstDayOfMonth = yearMonth.atDay(1)
            val daysInMonth = yearMonth.lengthOfMonth()

            val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
            val totalDays = 42

            //days in a week
            val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
            for (day in daysOfWeek) {
                days.add(CalendarModel(day))
            }

            //previous month
            val previousMonth = yearMonth.minusMonths(1)
            val daysInPreviousMonth = previousMonth.lengthOfMonth()
            for (i in 0 until firstDayOfWeek) {
                val date = previousMonth.atDay(daysInPreviousMonth - firstDayOfWeek + i + 1)
                days.add(CalendarModel(date, isCurrentMonth = false))
            }

            //current month
            for (i in 1..daysInMonth) {
                val date = yearMonth.atDay(i)
                val isToday = date.isEqual(today)
                days.add(CalendarModel(date, isCurrentMonth = true, isToday))
            }

            //next month
            val nextMonth = yearMonth.plusMonths(1)
            val daysToAddFromNextMonth = totalDays - days.size
            for (i in 1..daysToAddFromNextMonth) {
                val date = nextMonth.atDay(i)
                days.add(CalendarModel(date, isCurrentMonth = false))
            }
            _days.value = days
        }
    }

    fun getIncomeCategory() {
        viewModelScope.launch {
            val incomeCategories = getTransIncomeCategory.invoke()
            _incomeCategories.value = incomeCategories
        }
    }

    fun moveToPreviousMonth() {
        _yearMonth.value = _yearMonth.value?.minusMonths(1)
        updateCalendar()
    }

    fun moveToNextMonth() {
        _yearMonth.value = _yearMonth.value?.plusMonths(1)
        updateCalendar()
    }

    fun getTransAccount() {
        viewModelScope.launch {
            val account = getTransAccountUseCase.invoke()
            _account.value = account
        }
    }

    fun addTransaction(userId: String, transaction: TransactionModel) {
        viewModelScope.launch {
            try {
                firebase.addTransaction(userId, transaction)
            } catch (e: Exception) {
                Log.e("Exception", "Unable to save transactions")
            }
        }
    }

}