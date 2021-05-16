package com.example.data1.network

import com.example.data1.network.models.UserPlaylistApiResponse
import com.example.data1.network.models.spotify.GetCurrentPlaybackApiResponse
import com.example.data1.network.models.spotify.PlayPauseApiResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface SpotifyApi {

    @GET ("v1/me/player")
    suspend fun getCurrentPlayback (
        @Header ("Authorization") token: String
    ): Response<GetCurrentPlaybackApiResponse>

    @GET("v1/me/playlists")
    suspend fun getUserPlaylist (
        @Header("Authorization") accessToken: String
    ): Response<UserPlaylistApiResponse>

    @PUT("v1/me/player/play")
    suspend fun play(
        @Body playSong: JsonObject,
        @Header("Authorization") token: String = "Bearer "
    ): Response<PlayPauseApiResponse>

    @POST("v1/me/player/next")
    suspend fun playNext (
        @Header ("Authorization") token: String
    ): Response<PlayPauseApiResponse>

    @POST("v1/me/player/previous")
    suspend fun playPrevious (
        @Header ("Authorization") token: String
    ): Response<PlayPauseApiResponse>

    @PUT ("v1/me/player/pause")
    suspend fun pause (
        @Header ("Authorization") token: String
    ): Response<PlayPauseApiResponse>
}