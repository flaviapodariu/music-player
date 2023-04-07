package com.optional.musicplayer.ui.fragments

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.optional.musicplayer.R
import com.optional.musicplayer.adapters.SongAdapter
import com.optional.musicplayer.data.entities.Song
import com.optional.musicplayer.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment: Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    lateinit var permissionsLauncher: ActivityResultLauncher<Array<String>>
    private var readExternalGranted = false
    private var granularAudioPermission = false
    private lateinit var songAdapter: SongAdapter
    private var externalAudioGranted = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        permissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->

            readExternalGranted = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: readExternalGranted

            hasSdk33 {
                granularAudioPermission = permissions[Manifest.permission.READ_MEDIA_AUDIO] ?:granularAudioPermission
            }

            songAdapter = SongAdapter()



            if(readExternalGranted || granularAudioPermission) {
                createSongAdapter()
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
                MediaStore.Audio.Media.DURATION,
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

                    songs.add(Song(id, title, albumId, artist, duration, imagePath = ""))
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
            binding.songsRecyclerView.adapter = songAdapter
            binding.songsRecyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun requestPermissions() {
        val hasReadExternalPermission = ContextCompat.checkSelfPermission(
            this.requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        var hasReadPermission = hasReadExternalPermission

        hasSdk33 {
            val hasAudioPermission = ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.READ_MEDIA_AUDIO
            ) == PackageManager.PERMISSION_GRANTED

            hasReadPermission = hasReadExternalPermission && hasAudioPermission
        }

        val requestList = mutableListOf<String>()

        if(!hasReadPermission) {
            requestList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            hasSdk33 {
                requestList.add(Manifest.permission.READ_MEDIA_AUDIO)
            }
        }

        if(requestList.isNotEmpty()) {
            permissionsLauncher.launch(requestList.toTypedArray())
        }
    }
}

inline fun <T> hasSdk33(onHasSdk33: () -> T) : T? {
    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) onHasSdk33() else null
}
inline fun <T> hasSdk29(onHasSdk29: () -> T) : T? {
    return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) onHasSdk29() else null
}