package com.example.ifit.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.ifit.R
import com.example.ifit.other.Const
import com.example.ifit.ui.main.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import javax.inject.Singleton

@Module
@InstallIn(ServiceComponent::class)
class ServiceModule {

    @Provides
    @ServiceScoped
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context) = FusedLocationProviderClient(context)

    @Provides
    @ServiceScoped
    fun provideMainActivityPendingIntent(@ApplicationContext context: Context) = PendingIntent.getActivity(
        context,
        5,
        Intent(context, MainActivity::class.java).also {
            it.action = Const.ACTION_SHOW_TRACKING_FRAGMENT
        },
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    @Provides
    @ServiceScoped
    fun provideBaseNotificationBuilder(@ApplicationContext context: Context, pendingIntent: PendingIntent) =
        NotificationCompat.Builder (context, Const.NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_running)
            .setContentTitle("Continuing workout")
            .setContentText("00:00")
            .setContentIntent(pendingIntent)

}