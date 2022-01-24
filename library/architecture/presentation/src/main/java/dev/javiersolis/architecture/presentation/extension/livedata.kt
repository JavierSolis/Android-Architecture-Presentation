package dev.javiersolis.architecture.presentation.extension

import androidx.lifecycle.MutableLiveData

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { postValue (initialValue) }
