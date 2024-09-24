package com.jge.androidfetch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jge.androidfetch.data.FetchRepoImpl
import com.jge.androidfetch.presentation.FetchViewModel.FetchUIState.Success
import com.jge.androidfetch.domain.FetchItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FetchViewModel(fetchRepoImpl: FetchRepoImpl):ViewModel() {

    private val _uiState = MutableStateFlow<FetchUIState>(FetchUIState.Loading(true))
    val uiState:StateFlow<FetchUIState> = _uiState

    init {
        viewModelScope.launch {
            fetchRepoImpl.getFetchItems().collectLatest {
                if(it.isNotEmpty()){
                    _uiState.value = Success(it)
                } else {
                    _uiState.value = FetchUIState.Error("Failed to fetch items")
                }
            }
        }
    }

    sealed class FetchUIState{
        data class Loading(val loading:Boolean): FetchUIState()
        data class Success(val fetchItems: List<FetchItem>): FetchUIState()
        data class Error(val errorMessage:String): FetchUIState()
    }
}