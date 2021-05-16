package com.example.ifit.other

import android.graphics.Color
import com.google.firebase.database.collection.LLRBNode

object Const {

    const val RUNNING_DATABASE_NAME = "run_database"

    const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
    const val ACTION_PAUSE_SERVICE = "ACTION_PAUSE_SERVICE"
    const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    const val ACTION_SHOW_TRACKING_FRAGMENT = "ACTION_SHOW_TRACKING_FRAGMENT"

    const val REQUEST_CODE_LOCATION_PERMISSION = 0
    const val LOCATION_REQUEST_INTERVAL = 5000L
    const val LOCATION_FASTEST_REQUEST_INTERVAL = 3000L

    const val NOTIFICATION_CHANNEL_ID = "Notification channel"
    const val NOTIFICATION_CHANNEL_NAME = "Notification channel_name"
    const val NOTIFICATION_ID = 1
    const val POLYLINE_COLOR = Color.YELLOW
    const val POLYLINE_WIDTH = 18f

    const val SPOTIFY_CLIENT_ID = "c969e27aa90247d189ae30603054dfc5"
    const val SPOTIFY_REDIRECT_URI = "com.example.ifit://callback"
    const val SPOTIFY_LOGON_REQUEST_CODE = 1111

    const val SPOTIFY_BASE_URL = "https://api.spotify.com/"
}