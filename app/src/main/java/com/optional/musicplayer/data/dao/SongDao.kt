package com.optional.musicplayer.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.optional.musicplayer.data.entities.SongEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {

    @Transaction
    fun updateSongData(songs: List<SongEntity>){
        deleteAllSongs()
        insertAll(songs)
    }

    @Insert
    fun insertAll(songs: List<SongEntity>)

    @Query("SELECT * FROM songs WHERE id= :songId")
    fun getSongById(songId: Int) : Flow<SongEntity>

    @Query("DELETE FROM songs WHERE id = :songId")
    fun deleteSongById(songId: Int)

    @Query("DELETE FROM songs")
    fun deleteAllSongs()
}