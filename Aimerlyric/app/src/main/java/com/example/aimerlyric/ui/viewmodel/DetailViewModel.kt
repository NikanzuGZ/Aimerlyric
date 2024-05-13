package com.example.aimerlyric.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.aimerlyric.data.LyricRepository
import com.example.aimerlyric.model.Lyric
import com.example.aimerlyric.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel {
    private val repository: LyricRepository
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<UiState<Lyric>> =
            MutableStateFlow(UiState.Loading)
        val uiState: StateFlow<UiState<Lyric>>
        get() = _uiState

        fun getPlayerById(id: Int) = viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getPlayerById(id))
        }


        fun updatePlayer(id: Int, newState: Boolean) = viewModelScope.launch {
            repository.updatePlayer(id, !newState)
                .collect { isUpdated ->
                    if (isUpdated) getPlayerById(id)
                }
        }
}