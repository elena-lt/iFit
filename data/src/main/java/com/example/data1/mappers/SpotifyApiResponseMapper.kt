package com.example.data1.mappers

import com.example.data1.network.models.UserPlaylistApiResponse
import com.example.data1.network.models.spotify.GetCurrentPlaybackApiResponse
import com.example.data1.network.models.spotify.PlayPauseApiResponse
import com.example.domain.models.spotify.*

class SpotifyApiResponseMapper {

    fun toUserPlaylists(response: UserPlaylistApiResponse): UsersPlaylist {
        return UsersPlaylist(
            response.href,
            response.items.map {
                Playlist(
                    it.id,
                    it.images.map { Image(it.height, it.url, it.width) },
                    it.name,
                    it.uri
                )
            },
            response.limit,
            response.total
        )
    }

    fun toCurrentPlayback(response: GetCurrentPlaybackApiResponse): CurrentPlayback {
        return CurrentPlayback(
            Device( response.device.id, response.device.is_active, response.device.volume_percent),
            response.timestamp,
            response.progress_ms,
            ItemCurrentlyPlaying(response.itemCurrentlyPlaying.artists?.map {
                Artist(it.name)
            }),
            response.is_playing
        )
    }

    fun toPlayPause (response: PlayPauseApiResponse): PlayPause {
        return PlayPause(
            response.error?.let { Error(response.error?.status, response.error?.message, response.error?.reason) }
        )
    }
}