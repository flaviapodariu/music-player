package com.optional.musicplayer.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val artworkPath: String

)