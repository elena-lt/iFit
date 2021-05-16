package com.example.domain.usecases.spotify

import com.example.domain.respositories.spotify.SpotifyRepository
import javax.inject.Inject

class GetUserPlaylistsUsecase @Inject constructor(private val spotifyRepository: SpotifyRepository) {

    suspend fun invoke (accessToken: String) = spotifyRepository.getUserPlaylist(accessToken)

}