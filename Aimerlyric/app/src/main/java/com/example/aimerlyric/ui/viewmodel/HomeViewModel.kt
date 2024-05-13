package com.example.aimerlyric.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aimerlyric.data.LyricRepository
import com.example.aimerlyric.model.Lyric
import com.example.aimerlyric.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel { class HomeViewModel(
    private val repository: LyricRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Lyric>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Lyric>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchPlayer(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updatePlayer(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updatePlayer(id, newState)
            .collect { isUpdated ->
                if (isUpdated) search(_query.value)
            }
    }
}
}