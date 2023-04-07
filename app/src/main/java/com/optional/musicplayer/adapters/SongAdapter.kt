package com.optional.musicplayer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.provider.MediaStore
import com.bumptech.glide.Glide
import com.optional.musicplayer.R
import com.optional.musicplayer.data.entities.Song
import com.optional.musicplayer.databinding.SongItemBinding
import com.optional.musicplayer.ui.fragments.HomeFragment
import com.optional.musicplayer.ui.viewmodels.HomeViewModel
import kotlinx.coroutines.withContext

class SongAdapter() : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {
    inner class SongViewHolder(val binding: SongItemBinding) : RecyclerView.ViewHolder(binding.root)

    private var songList = mutableListOf<Song>()
    fun setList(songs: List<Song>) {
        songList.clear()
        songList.addAll(songs)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SongItemBinding.inflate(layoutInflater, parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.binding.apply {
            songTitle.text = songList[position].title
            songArtist.text = songList[position].artist

            Glide.with(holder.itemView.rootView.context)
                .load(songList[position].imagePath)
                .placeholder(R.drawable.basic_artwork).into(imageView)
        }
    }

    override fun getItemCount(): Int {
        return songList.size
    }




}