package com.optional.musicplayer.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.optional.musicplayer.data.entities.FavouriteEntity

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSongToFavourites(favEntry: FavouriteEntity)

    @Query("DELETE FROM favourites WHERE songId= :songId")
    fun removeSongFromFavourites(songId: Int)
}