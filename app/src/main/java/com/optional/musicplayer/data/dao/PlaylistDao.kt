package com.optional.musicplayer.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.optional.musicplayer.data.entities.PlaylistEntity

@Dao
interface PlaylistDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun createPlaylist(playlist: PlaylistEntity)

    @Query("UPDATE playlists SET name= :name WHERE id = :id")
    fun updateName(name: String, id: Int)

    @Query("DELETE FROM playlists WHERE id = :id")
    fun deletePlaylist(id: Int)
}