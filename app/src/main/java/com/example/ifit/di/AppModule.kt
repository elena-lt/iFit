package com.example.ifit.di

import com.example.data1.mappers.SpotifyApiResponseMapper
import com.example.data1.network.SpotifyApi
import com.example.data1.repositories.spotify.SpotifyDataSource
import com.example.data1.repositories.spotify.SpotifyDataSourceImp
import com.example.data1.repositories.spotify.SpotifyRepositoryImp
import com.example.domain.respositories.spotify.SpotifyRepository
import com.example.ifit.mappers.SpotifyMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideSpotifyPlaylistMapper(): SpotifyMapper = SpotifyMapper()

    @Provides
    @Singleton
    fun provideSpotifyRepository(spotifyDataSource: SpotifyDataSource): SpotifyRepository = SpotifyRepositoryImp(spotifyDataSource)

    @Provides
    @Singleton
    fun provideSpotifyDataSource(spotifyApi: SpotifyApi, mapper: SpotifyApiResponseMapper ): SpotifyDataSource = SpotifyDataSourceImp(spotifyApi, mapper)

    @Provides
    fun provideMapper(): SpotifyApiResponseMapper = SpotifyApiResponseMapper()

}