package com.example.ifit.models.modelsArch

data class SpotifyPlaylist(
    val id: String,
    val name: String,
    val uri: String,
    val images: List<SpotifyPlaylistImage>
)

data class SpotifyPlaylistImage(
    val height: Int,
    val width: Int,
    val uri: String
)