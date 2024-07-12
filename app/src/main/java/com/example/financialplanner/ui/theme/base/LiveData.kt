package com.example.financialplanner.ui.theme.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> {
    return this
}

fun <T> LiveData<T>.asFlow(): Flow<T> {
    return flow {
        value?.let { emit(it) }
    }
}

fun <T> useMediatorOf(
    vararg dependencies: LiveData<*>,
    onChanged: MediatorLiveData<T>.() -> T?
): MediatorLiveData<T> {
    val mediator = MediatorLiveData<T>()

    val onChange: (Any?) -> Unit = {
        val value = mediator.onChanged()
        value?.let {
            mediator.postValue(it)
        }
    }

    dependencies.forEach {
        mediator.addSource(it, onChange)
    }
    return mediator
}
