package com.example.domain.usecases.spotify

import com.example.domain.models.spotify.CurrentPlayback
import com.example.domain.respositories.spotify.SpotifyRepository
import com.example.domain.utils.Resource
import javax.inject.Inject

class GetCurrentPlaybackUsecase @Inject constructor(val spotifyRepository: SpotifyRepository) {

    suspend fun invoke (accessToken: String) : Resource<CurrentPlayback> = spotifyRepository.getCurrentPlayback(accessToken)
}