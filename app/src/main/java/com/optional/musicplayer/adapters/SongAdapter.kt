package com.optional.musicplayer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.optional.musicplayer.R
import com.optional.musicplayer.data.Song
import com.optional.musicplayer.databinding.SongItemBinding

class SongAdapter(
    private val onClickListener: (Song) -> Unit
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {
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
        holder.itemView.setOnClickListener { onClickListener(songList[position]) }

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