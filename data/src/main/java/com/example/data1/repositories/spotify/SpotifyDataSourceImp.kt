package com.example.data1.repositories.spotify

import com.example.data1.mappers.SpotifyApiResponseMapper
import com.example.data1.network.SpotifyApi
import com.example.domain.models.spotify.CurrentPlayback
import com.example.domain.models.spotify.PlayPause
import com.example.domain.models.spotify.UsersPlaylist
import com.example.domain.utils.Resource
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SpotifyDataSourceImp
   @Inject constructor(
       private val spotifyApiService: SpotifyApi,
       private val mapper: SpotifyApiResponseMapper
) : SpotifyDataSource {

    override suspend fun getUserPlaylists(accessToken: String): Resource<UsersPlaylist> = withContext(Dispatchers.IO) {
        try {
            val response = spotifyApiService.getUserPlaylist(accessToken)
            if (response.isSuccessful) {
                return@withContext Resource.SUCCESS(mapper.toUserPlaylists(response.body()!!))

            } else {
                return@withContext Resource.ERROR(response.message(), null)
            }
        } catch (e: Exception) {
            return@withContext Resource.ERROR(e.localizedMessage)
        }
    }

    override suspend fun getCurrentPlayback(accessToken: String): Resource<CurrentPlayback> = withContext(Dispatchers.IO) {
        try {
            val response = spotifyApiService.getCurrentPlayback(accessToken)
            if (response.isSuccessful){
               return@withContext Resource.SUCCESS (mapper.toCurrentPlayback(response.body()!!))
            }else{
                return@withContext Resource.ERROR (response.message())
            }
        }catch (e: Exception){
            return@withContext Resource.ERROR(e.localizedMessage)
        }
    }

    override suspend fun play(playlist: JsonObject, accessToken: String): Resource<PlayPause> = withContext(Dispatchers.IO){
        try {
            val response = spotifyApiService.play(playlist, accessToken)
            if (response.isSuccessful){
                return@withContext Resource.SUCCESS (mapper.toPlayPause(response.body()!!))
            }else{
                return@withContext Resource.ERROR (response.message())
            }
        }catch (e: Exception){
            return@withContext Resource.ERROR(e.localizedMessage)
        }
    }

    override suspend fun playNext(accessToken: String): Resource<PlayPause> = withContext(Dispatchers.IO){
        try {
            val response = spotifyApiService.playNext( accessToken)
            if (response.isSuccessful){
                return@withContext Resource.SUCCESS (mapper.toPlayPause(response.body()!!))
            }else{
                return@withContext Resource.ERROR (response.message())
            }
        }catch (e: Exception){
            return@withContext Resource.ERROR(e.localizedMessage)
        }
    }

    override suspend fun playPrevious(accessToken: String): Resource<PlayPause> = withContext(Dispatchers.IO) {
        try {
            val response = spotifyApiService.playPrevious(accessToken)
            if (response.isSuccessful){
                return@withContext Resource.SUCCESS (mapper.toPlayPause(response.body()!!))
            }else{
                return@withContext Resource.ERROR (response.message())
            }
        }catch (e: Exception){
            return@withContext Resource.ERROR(e.localizedMessage)
        }
    }


}