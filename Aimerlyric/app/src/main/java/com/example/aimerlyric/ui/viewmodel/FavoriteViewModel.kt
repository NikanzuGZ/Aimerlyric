package com.example.aimerlyric.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.aimerlyric.data.LyricRepository
import com.example.aimerlyric.model.Lyric
import com.example.aimerlyric.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel {
    private val repository: LyricRepository
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<UiState<List<Lyric>>> = MutableStateFlow(UiState.Loading)
        val uiState: StateFlow<UiState<List<Lyric>>>
        get() = _uiState

        fun getFavoritePlayer() = viewModelScope.launch {
            repository.getFavoritePlayer()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }

        fun updatePlayer(id: Int, newState: Boolean) {
            repository.updatePlayer(id, newState)
            getFavoritePlayer()
        }
}