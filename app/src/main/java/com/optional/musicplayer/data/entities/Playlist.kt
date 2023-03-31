package com.optional.musicplayer.data.entities

data class Playlist(
    val id: Int,
    val name: String,
    val songs: List<Song>
)
