package com.optional.musicplayer.di

import android.app.Application
import androidx.room.Room
import com.optional.musicplayer.data.MusicDatabase
import com.optional.musicplayer.data.repositories.SongRepository
import com.optional.musicplayer.util.PlayedSongState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePlayedSongState() = PlayedSongState()

    @Provides
    @Singleton
    fun provideRoomDb(app: Application) : MusicDatabase {
        return Room.databaseBuilder(app, MusicDatabase::class.java,"music_database").build()
    }
    @Provides
    @Singleton
    fun provideSongRepository(db: MusicDatabase) : SongRepository {
        return SongRepository(db)
    }
}