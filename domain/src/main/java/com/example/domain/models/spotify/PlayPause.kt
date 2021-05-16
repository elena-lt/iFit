package com.example.domain.models.spotify

data class PlayPause(

    val error: Error? = null
)

data class Error(
    val status: Int,
    val message: String,
    val reason: String
)