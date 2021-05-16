package com.example.data1.network.models.spotify

class GetCurrentPlaybackApiResponse(
    var device: Device,
    var shuffle_state: Boolean,
    var timestamp: Long,
    var progress_ms: Int? = 0,
    var itemCurrentlyPlaying: ItemCurrentlyPlaying,
    var is_playing: Boolean
)

class Device {
    var id: String? = null
    var is_active: Boolean? = false
    var name: String? = null
    var type: String? = null
    var volume_percent: Int? = 0
}


class Artist {
    var href: String? = null
    var id: String? = null
    var name: String? = null
    var type: String? = null
    var uri: String? = null
}

data class ItemCurrentlyPlaying(
    var artists: List<Artist>? = null,
    var duration_ms: Int? = 0,
    var href: String? = null,
    var id: String? = null,
    var name: String? = null,
    var preview_url: String? = null,
    var uri: String? = null
)
