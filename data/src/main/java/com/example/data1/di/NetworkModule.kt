package com.example.data1.di

import com.example.data1.mappers.FirebaseRunMapper
import com.example.data1.mappers.FirebaseUserMapper
import com.example.data1.network.SpotifyApi
import com.example.data1.repositories.firebase.FirebaseRepositoryImp
import com.example.data1.repositories.firebase.FirebaseSource
import com.example.data1.repositories.firebase.FirebaseSourceImp
import com.example.data1.repositories.firebase.RoomDataSource
import com.example.data1.utils.Const
import com.example.domain.respositories.firebase.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = Const.BASE_URL

    @Provides
    @Singleton
    fun provideSpotifyApiService(retrofit: Retrofit): SpotifyApi = retrofit.create(SpotifyApi::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

//    @Provides
//    fun provideFirebaseStorage () = Firebase.storage

    @Provides
    @Singleton
    fun provideFirebaseStore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseUserMapper() = FirebaseUserMapper()

    @Provides
    fun provideRunMapper() = FirebaseRunMapper()

    @Provides
    @Singleton
    fun provideFireBaseAuthSource(
        firebaseAuth: FirebaseAuth,
        firebase: FirebaseFirestore,
        mapper: FirebaseUserMapper,
        runMapper: FirebaseRunMapper
    ): FirebaseSource =
        FirebaseSourceImp(firebaseAuth, firebase, mapper, runMapper)

    @Provides
    @Singleton
    fun provideFirebaseAuthRepository(firebaseSource: FirebaseSource, roomDatabaseSource: RoomDataSource): FirebaseRepository =
        FirebaseRepositoryImp(firebaseSource, roomDatabaseSource)
}