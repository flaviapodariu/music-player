package com.optional.musicplayer.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.optional.musicplayer.data.Song
import com.optional.musicplayer.data.repositories.SongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    private val songRepository: SongRepository
) : ViewModel() {

    lateinit var song: Song

    suspend fun getSongById(songId: Int) : Song {
        return songRepository.getSongById(songId)
    }
}