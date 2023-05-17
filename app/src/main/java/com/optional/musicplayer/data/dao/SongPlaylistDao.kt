package com.optional.musicplayer.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.optional.musicplayer.data.entities.SongPlaylistEntity

@Dao
interface SongPlaylistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSong(song: SongPlaylistEntity)

    @Query("DELETE FROM songs_playlists WHERE songId = :songId AND trackOrder= :track")
    fun deleteSong(songId: Int, track: Int)
}