package com.example.aimerlyric.model

data class Lyric(
    val id: Int,
    val image: Int,
    val name: String,
    val album: String,
    val titel: String,
    val year: Int,
    val lirik: String,
    var isFavorite: Boolean = false
)
