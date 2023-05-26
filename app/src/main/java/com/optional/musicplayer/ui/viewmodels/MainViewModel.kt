package com.optional.musicplayer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.optional.musicplayer.data.Song
import com.optional.musicplayer.util.PlayedSongState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
     val playedSongState: PlayedSongState
): ViewModel() {


    fun onSongPlayed(song: Song) {
        viewModelScope.launch {
            playedSongState.onSongPlayed(song)
        }
    }

    fun onPlayerClicked() {
        viewModelScope.launch {
            playedSongState.onPlayerClicked()
        }
    }
}