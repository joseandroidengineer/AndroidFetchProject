package com.jge.androidfetch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jge.androidfetch.data.FetchRepoImpl
import javax.inject.Inject

class FetchViewModelFactory @Inject constructor(val fetchRepoImpl: FetchRepoImpl):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FetchViewModel::class.java)) {
            return FetchViewModel(fetchRepoImpl)  as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}