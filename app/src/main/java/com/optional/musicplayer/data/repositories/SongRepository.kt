package com.optional.musicplayer.data.repositories

import com.optional.musicplayer.data.MusicDatabase
import com.optional.musicplayer.data.Song
import com.optional.musicplayer.data.entities.FavouriteEntity
import com.optional.musicplayer.data.entities.SongEntity
import com.optional.musicplayer.data.mappers.songEntityToSong
import kotlinx.coroutines.flow.first

class SongRepository(
     db: MusicDatabase
) {
    private val songDao = db.songDao
    private val favDao = db.favouritesDao

    fun addAllSongs(songs: List<SongEntity>) {
        songDao.updateSongData(songs)
    }

    suspend fun getSongById(songId: Int) : Song {
        return songEntityToSong(
            songDao.getSongById(songId).first()
        )
    }

    fun addSongToFavourites(songId: Int) {
        favDao.addSongToFavourites(
            FavouriteEntity(songId)
        )
    }

    fun removeSongFromFavourites(songId: Int) {
        favDao.removeSongFromFavourites(songId)
    }
}