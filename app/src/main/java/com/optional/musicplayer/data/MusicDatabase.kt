package com.optional.musicplayer.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.optional.musicplayer.data.dao.FavouritesDao
import com.optional.musicplayer.data.dao.PlaylistDao
import com.optional.musicplayer.data.dao.SongDao
import com.optional.musicplayer.data.dao.SongPlaylistDao
import com.optional.musicplayer.data.entities.FavouriteEntity
import com.optional.musicplayer.data.entities.PlaylistEntity
import com.optional.musicplayer.data.entities.SongEntity
import com.optional.musicplayer.data.entities.SongPlaylistEntity

@Database(
    entities=[SongEntity::class,
        PlaylistEntity::class,
        SongPlaylistEntity::class,
        FavouriteEntity::class],
    version= 1,
)
abstract class MusicDatabase : RoomDatabase() {

    abstract val songDao: SongDao
    abstract val playlistDao: PlaylistDao
    abstract val songPlaylistDao: SongPlaylistDao
    abstract val favouritesDao: FavouritesDao

}