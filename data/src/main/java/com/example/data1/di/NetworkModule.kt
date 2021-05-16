package com.example.data1.di

import com.example.data1.mappers.FirebaseUserMapper
import com.example.data1.network.SpotifyApi
import com.example.data1.repositories.firebase.FirebaseAuthRepositoryImp
import com.example.data1.repositories.firebase.FirebaseAuthSource
import com.example.data1.repositories.firebase.FirebaseAuthSourceImp
import com.example.data1.utils.Const
import com.example.domain.respositories.firebase.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

    @Provides
    @Singleton
    fun provideFirebaseStore() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseUserMapper() = FirebaseUserMapper()

    @Provides
    @Singleton
    fun provideFireBaseAuthSource(firebaseAuth: FirebaseAuth, mapper: FirebaseUserMapper): FirebaseAuthSource =
        FirebaseAuthSourceImp(firebaseAuth, mapper)

    @Provides
    @Singleton
    fun provideFirebaseAuthRepository(firebaseAuthSource: FirebaseAuthSource): AuthenticationRepository =
        FirebaseAuthRepositoryImp(firebaseAuthSource)
}