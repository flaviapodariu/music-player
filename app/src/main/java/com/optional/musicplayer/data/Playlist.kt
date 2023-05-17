package com.optional.musicplayer.data

import com.optional.musicplayer.data.Song

data class Playlist(
    val id: Int,
    val name: String,
    val songs: List<Song>
)
