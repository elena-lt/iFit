package com.example.data1.repositories.spotify

import com.example.domain.models.spotify.CurrentPlayback
import com.example.domain.models.spotify.PlayPause
import com.example.domain.models.spotify.UsersPlaylist
import com.example.domain.respositories.spotify.SpotifyRepository
import com.example.domain.utils.Resource
import com.google.gson.JsonObject

class SpotifyRepositoryImp(
    private val spotifyDataSource: SpotifyDataSource
) : SpotifyRepository {

    override suspend fun getUserPlaylist(accessToken: String): Resource<UsersPlaylist> =
        spotifyDataSource.getUserPlaylists(accessToken)

    override suspend fun getCurrentPlayback(accessToken: String): Resource<CurrentPlayback> =
        spotifyDataSource.getCurrentPlayback(accessToken)

    override suspend fun play(playlist: JsonObject, accessToken: String): Resource<PlayPause> =
        spotifyDataSource.play(playlist, accessToken)

    override suspend fun playNext(accessToken: String): Resource<PlayPause> = spotifyDataSource.playNext(accessToken)

    override suspend fun playPrevious(accessToken: String): Resource<PlayPause> =
        spotifyDataSource.playPrevious(accessToken)
}