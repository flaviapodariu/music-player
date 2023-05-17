package com.optional.musicplayer.data

data class Song(
    val id: Int,
    val title: String,
    val albumId: Int?,
    val artist: String,
    val duration: Int,
    val imagePath: String
)
