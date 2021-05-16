package com.example.ifit.models.spotify

data class CurrentPlayback(
    var device: Device,
    var timestamp: Long,
    var progress_ms: Int? = 0,
    var itemCurrentlyPlaying: ItemCurrentlyPlaying,
    var is_playing: Boolean
)

class Device(
    var id: String? = null,
    var is_active: Boolean? = false,
    var volume_percent: Int? = 0
)


class Artist(
    var name: String? = null
)

data class ItemCurrentlyPlaying(
    var artists: List<Artist>? = null,
    var duration_ms: Int? = 0,
    var name: String? = null,
    )
