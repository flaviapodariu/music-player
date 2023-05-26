package com.optional.musicplayer.ui

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.optional.musicplayer.R
import com.optional.musicplayer.data.Song
import com.optional.musicplayer.databinding.ActivityMainBinding
import com.optional.musicplayer.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            mainViewModel.playedSongState.currentSong.collectLatest { songPlayed ->
                Log.d("lower", songPlayed.title)
                binding.currentSongArtist.text = songPlayed.artist
                binding.currentSongTitle.text = songPlayed.title
                Glide.with(applicationContext)
                    .load(songPlayed.imagePath)
                    .placeholder(R.drawable.basic_artwork).into(binding.artworkThumbnail)
            }
        }

        binding.playerWrapper.setOnClickListener{
            mainViewModel.onPlayerClicked()
        }

        val searchIconAnimator = ObjectAnimator.ofFloat(binding.mainToolbar.searchIcon, "translationX", -400f)
        binding.mainToolbar.searchIcon.setOnClickListener {
            searchIconAnimator.start()
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.songFragment) {
                binding.playerWrapper.visibility = View.GONE
                binding.songCollectionWrapper.visibility = View.GONE
                binding.mainToolbar.toolbar.visibility = View.GONE
            } else {
                binding.playerWrapper.visibility = View.VISIBLE
                binding.songCollectionWrapper.visibility = View.VISIBLE
                binding.mainToolbar.toolbar.visibility = View.VISIBLE

            }
        }
    }


}