package com.optional.musicplayer.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.optional.musicplayer.data.MusicDatabase
import com.optional.musicplayer.data.Song
import com.optional.musicplayer.data.mappers.songToSongEntity
import com.optional.musicplayer.data.repositories.SongRepository
import com.optional.musicplayer.ui.fragments.SongFragment
import com.optional.musicplayer.util.PlayedSongState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
   val playedSongState: PlayedSongState,
   private val songRepository: SongRepository
): ViewModel() {

   fun onSongPlayed(song: Song) {
      Log.d("homevm", song.title)
      viewModelScope.launch {
         playedSongState.onSongPlayed(song)
      }
   }
   fun addAllSongs(songs: List<Song>) {
      val songEntities = songs.map { songToSongEntity(it) }
      songRepository.addAllSongs(songEntities)
   }

}