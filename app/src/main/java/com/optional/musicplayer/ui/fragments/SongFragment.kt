package com.optional.musicplayer.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.optional.musicplayer.R
import com.optional.musicplayer.databinding.FragmentSongBinding
import com.optional.musicplayer.ui.viewmodels.SongViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SongFragment : Fragment(R.layout.fragment_song) {

    private lateinit var binding: FragmentSongBinding
    private val songViewModel: SongViewModel by viewModels()
    val args: SongFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSongBinding.bind(view)

        lifecycleScope.launch {
            songViewModel.getSongById(args.songId).also { song ->
                binding.songTitleFullscreen.text = song.title
                binding.songArtistFullscreen.text = song.artist

                binding.shareIcon.setOnClickListener {
                    val sendIntent = songViewModel.shareSong(song)
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                }
            }
        }

        binding.dropSongFragment.setOnClickListener {
            val action = SongFragmentDirections.actionSongFragmentToHomeDest()
            findNavController().navigate(action)
        }




    }


}