package com.example.financialplanner.ui.theme.base

import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


open class BaseViewModel : ViewModel() {


    private val _throwable = MutableLiveData<Throwable?>()
    val throwable = _throwable.asLiveData()

    private val _loading = MutableLiveData(listOf<Long>())
    val loading = _loading.asLiveData()

    val customScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    fun setExceptionAsync(error: Throwable) {
        Log.e("Network fail with error $error", "error")
        error.printStackTrace()
        if (_throwable.value != null) return
        _throwable.postValue(error)
    }
    fun CoroutineScope.load(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit,
    ): Job {
        return launch(context, start) {
            val uniqueID = SystemClock.elapsedRealtime()
            _loading.value = loading.value?.toMutableList().apply {
                this?.add(uniqueID)
            }
            block.invoke(this)

            val finishMillis = SystemClock.elapsedRealtime()
            _loading.value = loading.value?.filter {
                finishMillis - it < LoadingDialog.TIME_OUT
            }?.toMutableList().apply {
                this?.remove(uniqueID)
            }
        }
    }



}