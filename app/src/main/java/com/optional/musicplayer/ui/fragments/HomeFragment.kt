package com.optional.musicplayer.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.optional.musicplayer.R
import com.optional.musicplayer.adapters.SongAdapter
import com.optional.musicplayer.data.entities.Song
import com.optional.musicplayer.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        val songList = mutableListOf(
            Song(1, "Aces High", 2, "Iron Maiden", "path"),
            Song(1, "Aces High", 2, "Iron Maiden", "path"),
            Song(1, "Aces High", 2, "Iron Maiden", "path"),
            Song(1, "Aces High", 2, "Iron Maiden", "path"),
            Song(1, "Aces High", 2, "Iron Maiden", "path"),
            Song(1, "Aces High", 2, "Iron Maiden", "path"),
            Song(1, "Aces High", 2, "Iron Maiden", "path"),
            Song(1, "Aces High", 2, "Iron Maiden", "path")
        )

        val adapter = SongAdapter(songList)
        binding.songsRecyclerView.adapter = adapter
        binding.songsRecyclerView.layoutManager = LinearLayoutManager(this.context)
    }



}