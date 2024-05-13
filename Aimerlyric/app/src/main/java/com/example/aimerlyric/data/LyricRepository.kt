package com.example.aimerlyric.data

import com.example.aimerlyric.model.Lyric
import com.example.aimerlyric.model.LyricData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class LyricRepository {
    private val dummyLyric = mutableListOf<Lyric>()

    init {
        if (dummyLyric.isEmpty()) {
            LyricData.dummyLyric.forEach {
                dummyLyric.add(it)
            }
        }
    }

    fun getPlayerById(playerId: Int): Lyric {
        return dummyLyric.first {
            it.id == playerId
        }
    }

    fun getFavoritePlayer(): Flow<List<Lyric>> {
        return flowOf(dummyLyric.filter { it.isFavorite })
    }

    fun searchPlayer(query: String) = flow {
        val data = dummyLyric.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun updatePlayer(playerId: Int, newState: Boolean): Flow<Boolean> {
        val index = dummyLyric.indexOfFirst { it.id == playerId }
        val result = if (index >= 0) {
            val player = dummyLyric[index]
            dummyLyric[index] = player.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: LyricRepository? = null

        fun getInstance(): LyricRepository =
            instance ?: synchronized(this) {
                LyricRepository().apply {
                    instance = this
                }
            }
    }
}
