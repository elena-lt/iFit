package com.example.domain.models.spotify

data class UsersPlaylist(
    val href: String,
    val playlists: List<Playlist>,
    val limit: Int,
    val total: Int
)

data class Image(
    val height: Int,
    val url: String,
    val width: Int,
)

data class Playlist(
    val id: String,
    val images: List<Image>,
    val name: String,
    val uri: String  //spotify:playlist:37i9dQZF1DX2sUQwD7tbmL
)