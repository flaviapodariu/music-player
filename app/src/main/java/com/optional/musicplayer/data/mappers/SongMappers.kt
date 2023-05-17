package com.optional.musicplayer.data.mappers

import com.optional.musicplayer.data.Song
import com.optional.musicplayer.data.entities.SongEntity

fun songToSongEntity(song: Song) = SongEntity(
    id = song.id,
    title = song.title,
    albumId = song.albumId,
    artist = song.artist,
    duration = song.duration,
    imagePath = song.imagePath
)

fun songEntityToSong(songEntity: SongEntity) = Song(
    id = songEntity.id,
    title = songEntity.title,
    albumId = songEntity.albumId,
    artist = songEntity.artist,
    duration = songEntity.duration,
    imagePath = songEntity.imagePath
)