package com.example.ifit.ui.main.spotifyMusic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.spotify.GetUserPlaylistsUsecase
import com.example.domain.usecases.spotify.PlayResumeUsecase
import com.example.domain.utils.Resource
import com.example.ifit.mappers.SpotifyMapper
import com.example.ifit.models.modelsArch.SpotifyPlaylist
import com.example.ifit.services.SpotifyService
import com.google.gson.JsonObject
import com.spotify.sdk.android.auth.AuthorizationRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpotifyViewModel @Inject constructor(
    private val getUserPlayLists: GetUserPlaylistsUsecase,
    private val playResume: PlayResumeUsecase,
    private val mapper: SpotifyMapper
) : ViewModel() {

    private val _dataLoading = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _playlists = MutableLiveData<List<SpotifyPlaylist>>()
    val userPlaylists: LiveData<List<SpotifyPlaylist>> = _playlists

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun provideSpotifyAuthorizationRequest(): AuthorizationRequest {
        return SpotifyService.provideSpotifyAuthorizationRequest()
    }

    fun getUserPlaylists(accessToken: String) {
        viewModelScope.launch {
            _dataLoading.postValue(true)

            when (val result = getUserPlayLists.invoke("Bearer $accessToken")) {
                is Resource.SUCCESS -> {
                    _playlists.postValue(mapper.toSpotifyPlaylist(result.data!!))
                    _dataLoading.postValue(true)
                }
                is Resource.ERROR -> {
                    _dataLoading.postValue(true)
                    _error.postValue(result.message!!)
                }
            }
        }
    }

    fun playPlaylist(playlist: SpotifyPlaylist, accessToken: String) {
        val playlistJson = JsonObject().apply {
            addProperty("context_uri", playlist.uri)
            addProperty("position_ms", 0)
        }
        viewModelScope.launch {
            playResume.play(playlistJson, "Bearer $accessToken")
        }
    }

    fun playNext(accessToken: String) {
        viewModelScope.launch {
            playResume.playNext("Bearer $accessToken")
        }
    }

    fun playPrevious(accessToken: String) {
        viewModelScope.launch {
            playResume.playPrevious("Bearer $accessToken")
        }
    }

}