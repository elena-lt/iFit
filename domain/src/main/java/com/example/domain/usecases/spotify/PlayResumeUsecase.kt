package com.example.domain.usecases.spotify

import com.example.domain.models.spotify.PlayPause
import com.example.domain.respositories.spotify.SpotifyRepository
import com.example.domain.utils.Resource
import com.google.gson.JsonObject
import javax.inject.Inject

class PlayResumeUsecase @Inject constructor(val spotifyRepository: SpotifyRepository) {

    suspend fun play(playlist: JsonObject, accessToken: String): Resource<PlayPause> = spotifyRepository.play(playlist, accessToken)

    suspend fun playNext(accessToken: String): Resource<PlayPause> = spotifyRepository.playNext(accessToken)
    suspend fun playPrevious(accessToken: String): Resource<PlayPause> = spotifyRepository.playPrevious(accessToken)
}