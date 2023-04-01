package com.optional.musicplayer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.optional.musicplayer.R
import com.optional.musicplayer.data.entities.Song
import com.optional.musicplayer.databinding.SongItemBinding

class SongAdapter(private var songList: List<Song>) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {
    inner class SongViewHolder(val binding: SongItemBinding) : RecyclerView.ViewHolder(binding.root)

    fun setList(songs: List<Song>) {
        songList = songs
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
            imageView.setImageResource(R.drawable.basic_artwork)
        }
    }

    override fun getItemCount(): Int {
        return songList.size
    }




}