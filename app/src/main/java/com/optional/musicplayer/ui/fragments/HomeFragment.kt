package com.optional.musicplayer.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.optional.musicplayer.R
import com.optional.musicplayer.adapters.SongAdapter
import com.optional.musicplayer.data.entities.Song
import com.optional.musicplayer.databinding.FragmentHomeBinding
import com.optional.musicplayer.ui.viewmodels.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeFragment: Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    lateinit var permissionsLauncher: ActivityResultLauncher<Array<String>>
    private var readExternalGranted = false
    private lateinit var songAdapter: SongAdapter
    private var externalAudioGranted = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        permissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            readExternalGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: readExternalGranted

            // add any permissions here ...

            if(readExternalGranted) {
                createSongAdapter()
                binding.songsRecyclerView.adapter = songAdapter
                binding.songsRecyclerView.layoutManager = LinearLayoutManager(this.context)
            }
            else {
                Toast.makeText(this.requireContext(), "Can't load songs without permission.", Toast.LENGTH_LONG).show()
            }
        }
        requestPermissions()


    }







    private suspend fun loadSongsList() : List<Song> {

            return withContext(Dispatchers.IO) {

            val collection = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            else MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

            val projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION
            )

            val songs = mutableListOf<Song>()

            activity?.contentResolver?.query(
                collection,
                projection,
                null,
                null,
                "${MediaStore.Audio.Media.TITLE} ASC"
            )?.use { cursor ->
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
                val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

                while(cursor.moveToNext()) {
                    val id = cursor.getInt(idColumn)
                    val title = cursor.getString(titleColumn)
                    val albumId = cursor.getInt(albumIdColumn)
                    val artist = cursor.getString(artistColumn)
                    val duration = cursor.getInt(durationColumn)

                    songs.add(Song(id, title, albumId, artist, duration))
                }
                songs.toList()
            } ?: listOf()
        }

    }
    private fun createSongAdapter() {
        var songs = listOf<Song>()
        lifecycleScope.launch {
            songs = loadSongsList()
            songAdapter.setList(songs)
        }
    }

    private fun requestPermissions() {
        val hasReadPermission = ContextCompat.checkSelfPermission(
            this.requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val requestList = mutableListOf<String>()
        readExternalGranted = hasReadPermission

        if(!readExternalGranted) {
            requestList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if(requestList.isNotEmpty()) {
            permissionsLauncher.launch(requestList.toTypedArray())
        }
    }
}

