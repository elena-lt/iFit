package com.example.data1.network.models.spotify

data class PlayPauseApiResponse(
    val error: Error? = null
)

data class Error(
    val status: Int,
    val message: String,
    val reason: String
)
