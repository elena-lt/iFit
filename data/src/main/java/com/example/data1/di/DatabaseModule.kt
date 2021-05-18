package com.example.data1.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data1.database.RunDao
import com.example.data1.database.RunDatabase
import com.example.data1.repositories.firebase.RoomDataSource
import com.example.data1.repositories.firebase.RoomDataSourceImp
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

    @Provides
    @Singleton
    fun provideRoomDataSource (db: RunDao): RoomDataSource = RoomDataSourceImp(db)
}