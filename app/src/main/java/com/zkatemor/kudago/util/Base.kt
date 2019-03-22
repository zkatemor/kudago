package com.zkatemor.kudago.util

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class Base<T>(val creator: () -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creator() as T
    }
}