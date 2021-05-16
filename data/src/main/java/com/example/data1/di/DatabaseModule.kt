package com.example.data1.di

import android.content.Context
import androidx.room.Room
import com.example.data1.database.RunDatabase
import com.example.data1.utils.Const
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, RunDatabase::class.java, Const.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideDao (db: RunDatabase) = db.runDao()
}