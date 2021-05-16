package com.example.ifit.models.spotify

data class Play(
    val error: Error? = null
)

data class Error(
    val status: Int,
    val message: String,
    val reason: String
)
