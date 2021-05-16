package com.example.data1.network.models

data class UserPlaylistApiResponse(
    val href: String,
    val items: List<Playlist>,
    val limit: Int,
    val total: Int
)

data class Image(
    val height: Int,
    val url: String,
    val width: Int,
)


data class Tracks(
    val href: String,
    val total: Int
)

data class Playlist(
    val description: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val snapshot_id: String,
    val tracks: Tracks,
    val uri: String  //spotify:playlist:37i9dQZF1DX2sUQwD7tbmL
)