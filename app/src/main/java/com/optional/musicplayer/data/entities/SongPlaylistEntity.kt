package com.optional.musicplayer.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "songs_playlists", foreignKeys = [ForeignKey(
    entity= SongEntity::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("songId"),
    onDelete = ForeignKey.CASCADE
),
    ForeignKey(
        entity = PlaylistEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("playlistId"),
        onDelete = ForeignKey.CASCADE
    )
],
    primaryKeys = ["songId", "playlistId", "trackOrder"]
)
data class SongPlaylistEntity(
    val songId: Int,
    val playlistId: Int,
    val trackOrder: Int
)
