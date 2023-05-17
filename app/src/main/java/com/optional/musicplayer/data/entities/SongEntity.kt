package com.optional.musicplayer.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val albumId: Int?,
    val artist: String,
    val duration: Int,
    val imagePath: String,
)
