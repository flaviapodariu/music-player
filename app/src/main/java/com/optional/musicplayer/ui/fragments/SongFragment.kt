package com.optional.musicplayer.ui.fragments

import android.os.Bundle
import android.view.View
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
            songViewModel.getSongById(args.songId).also {
                binding.songTitleFullscreen.text = it.title
                binding.songArtistFullscreen.text = it.artist
            }
        }

        binding.dropSongFragment.setOnClickListener {
            val action = SongFragmentDirections.actionSongFragmentToHomeDest()
            findNavController().navigate(action)
        }


    }


}