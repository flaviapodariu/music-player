package com.optional.musicplayer.util

import com.optional.musicplayer.data.Song
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class PlayedSongState {

    private val _currentSong = MutableSharedFlow<Song>()
    val currentSong: SharedFlow<Song> = _currentSong

    private val _playerClicked = MutableSharedFlow<Boolean>()
    val playerClicked: SharedFlow<Boolean> = _playerClicked


    suspend fun onSongPlayed(song: Song) {
        _currentSong.emit(song)
    }

    suspend fun onPlayerClicked() {
        _playerClicked.emit(true)
    }
}