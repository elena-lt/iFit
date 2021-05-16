package com.example.ifit.services

import com.example.ifit.other.Const
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse

object SpotifyService {

    fun provideSpotifyAuthorizationRequest(): AuthorizationRequest {
        return AuthorizationRequest.Builder(
            Const.SPOTIFY_CLIENT_ID,
            AuthorizationResponse.Type.TOKEN,
            Const.SPOTIFY_REDIRECT_URI
        )
            .setScopes(
                arrayOf(
                    "user-modify-playback-state",
                    "user-read-currently-playing",
                    "user-modify-playback-state",
                    "playlist-read-private",
                    "user-read-playback-state"
                )
            )
            .build()
    }

}