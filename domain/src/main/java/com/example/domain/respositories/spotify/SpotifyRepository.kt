package com.example.domain.respositories.spotify

import com.example.domain.models.spotify.CurrentPlayback
import com.example.domain.models.spotify.PlayPause
import com.example.domain.models.spotify.UsersPlaylist
import com.example.domain.utils.Resource
import com.google.gson.JsonObject
import org.json.JSONObject

interface SpotifyRepository {

    suspend fun getUserPlaylist(accessToken: String): Resource<UsersPlaylist>

    suspend fun getCurrentPlayback (accessToken: String): Resource<CurrentPlayback>

    suspend fun play(playlist: JsonObject, accessToken: String): Resource<PlayPause>

    suspend fun playNext(accessToken: String): Resource<PlayPause>

    suspend fun playPrevious(accessToken: String): Resource<PlayPause>
}