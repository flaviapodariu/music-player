package com.optional.musicplayer.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "favourites", foreignKeys = [
    ForeignKey(
        entity = SongEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("songId"),
        onDelete = ForeignKey.CASCADE
    )
])
data class FavouriteEntity(
    val songId: Int,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
