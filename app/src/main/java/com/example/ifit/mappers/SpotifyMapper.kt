package com.example.ifit.mappers

import com.example.domain.models.spotify.CurrentPlayback
import com.example.domain.models.spotify.PlayPause
import com.example.domain.models.spotify.UsersPlaylist
import com.example.ifit.models.modelsArch.SpotifyPlaylist
import com.example.ifit.models.modelsArch.SpotifyPlaylistImage
import com.example.ifit.models.spotify.Artist
import com.example.ifit.models.spotify.Device
import com.example.ifit.models.spotify.ItemCurrentlyPlaying
import com.example.ifit.models.spotify.Play

class SpotifyMapper {

    fun toSpotifyPlaylist(userPlaylist: UsersPlaylist): List<SpotifyPlaylist> {
        return userPlaylist.playlists.map { playlist ->
            SpotifyPlaylist(
                playlist.id,
                playlist.name,
                playlist.uri,
                playlist.images.map { image ->
                    SpotifyPlaylistImage(
                        image.height,
                        image.width,
                        image.url
                    )

                })
        }
    }

    fun toCurrentPlayback(currentPlayback: CurrentPlayback): com.example.ifit.models.spotify.CurrentPlayback {
        return com.example.ifit.models.spotify.CurrentPlayback(
            Device(currentPlayback.device.id, currentPlayback.device.is_active, currentPlayback.device.volume_percent),
            currentPlayback.timestamp,
            currentPlayback.progress_ms,
            ItemCurrentlyPlaying(
                currentPlayback.itemCurrentlyPlaying.artists?.map { Artist(it.name) },
                currentPlayback.itemCurrentlyPlaying.duration_ms,
                currentPlayback.itemCurrentlyPlaying.name
            ),
            currentPlayback.is_playing
        )
    }

    fun toPlayResume(playResume: PlayPause): Play {
        return Play (
            playResume.error?.let { com.example.ifit.models.spotify.Error(it.status, it.message, it.reason) }
                )
    }
}