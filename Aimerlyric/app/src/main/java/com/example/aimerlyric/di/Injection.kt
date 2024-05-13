package com.example.aimerlyric.di

import com.example.aimerlyric.data.LyricRepository

object Injection {
    fun provideRepository(): LyricRepository {
        return LyricRepository.getInstance()
    }
}