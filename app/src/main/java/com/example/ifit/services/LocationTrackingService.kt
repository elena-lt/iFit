package com.example.ifit.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.ifit.R
import com.example.ifit.models.TimerEvent
import com.example.ifit.other.Const
import com.example.ifit.other.Const.NOTIFICATION_ID
import com.example.ifit.other.LocationTrackingUtility
import com.example.ifit.other.TimerUtil
import com.example.ifit.ui.main.MainActivity
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias Polyline = MutableList<LatLng>

class LocationTrackingService : LifecycleService() {

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    var timeRun = MutableLiveData<Long>()

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.locations?.let {
                for (location in it) {
                    addPathPoint(location)
                }
            }
        }
    }

    companion object {
        private const val TAG = "TrackingFragment"

        private var timerEvent = MutableLiveData<TimerEvent>()

        private var _timerMillis = MutableLiveData<Long>()
        val timerMillis: LiveData<Long> = _timerMillis

        private var _pathPoints = MutableLiveData<MutableList<LatLng>>()
        val pathPoints: LiveData<MutableList<LatLng>> = _pathPoints

        private var _isTracking = MutableLiveData<Boolean>()
        val isTracking: LiveData<Boolean> = _isTracking

        private var _distanceSpherical = MutableLiveData<Double>()
        val distanceSpherical: LiveData<Double> = _distanceSpherical
    }


    override fun onCreate() {
        super.onCreate()
        initValues()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            when (it.action) {
                Const.ACTION_START_OR_RESUME_SERVICE -> {
                    Log.d(TAG, "Tracking Started")
                    startService()
                }
                Const.ACTION_PAUSE_SERVICE -> {
                    Log.d(TAG, "Tracking paused")

                }
                Const.ACTION_STOP_SERVICE -> {
                    Log.d(TAG, "Tracking stopped")
                    stopService()
                }
                else -> {}
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun initValues() {
        _isTracking.postValue(false)
        _pathPoints.postValue(mutableListOf())
        _timerMillis.postValue(0)
        timerEvent.postValue(TimerEvent.STOP)
        timeRun.postValue(0)
    }

    private fun startService() {
        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }
        startForeground(Const.NOTIFICATION_ID, createNotificationBuilder().build())

        startLocationTracking()
        startTimer()

        timeRun.observe(this, {
                val notification = createNotificationBuilder()
                    .setContentText(TimerUtil.getFormattedTime(it * 1000L, false))
                notificationManager.notify (NOTIFICATION_ID, notification.build())

        })
    }

    @SuppressLint("MissingPermission")
    private fun startLocationTracking() {

        if (LocationTrackingUtility.hasLocationPermissions(this)) {
            val locationRequest = LocationRequest.create().apply {
                interval = Const.LOCATION_REQUEST_INTERVAL
                fastestInterval = Const.LOCATION_FASTEST_REQUEST_INTERVAL
                priority = PRIORITY_HIGH_ACCURACY
            }
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    private fun stopLocationTracking() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun stopService() {
        stopLocationTracking()
        stopForeground(true)
        initValues()
        stopSelf()
    }

    private val positionsList = mutableListOf<LatLng>()
    private fun addPathPoint(location: Location?) {
        location?.let {
            val pos = LatLng(location.latitude, location.longitude)
            positionsList.add(pos)
            _pathPoints.value = positionsList
            _pathPoints.postValue(pathPoints.value)

            if (positionsList.size > 2) {
                calculateTheDistance(positionsList[positionsList.size-2], positionsList.last())
            }
        }
    }

    var distance = 0.0
    private fun calculateTheDistance(prevPos: LatLng, currPos: LatLng) {
        distance += SphericalUtil.computeDistanceBetween(prevPos, currPos)
        _distanceSpherical.postValue(distance)

    }

    private fun startTimer() {
        var lastSecondTmeStamp = 0L
        timerEvent.postValue(TimerEvent.START)
        val timerStarted = System.currentTimeMillis()
        CoroutineScope(Dispatchers.Main).launch {
            while (timerEvent.value == TimerEvent.START) {
                val lapTime = System.currentTimeMillis() - timerStarted
                _timerMillis.postValue(lapTime)

                if(timerMillis.value!! >= lastSecondTmeStamp + 1000L){
                    timeRun.postValue(timeRun.value!! +1)
                    lastSecondTmeStamp+= 1000L
                }
                delay(50L)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            Const.NOTIFICATION_CHANNEL_ID,
            Const.NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotificationBuilder() : NotificationCompat.Builder =
        NotificationCompat.Builder (this, Const.NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_running)
            .setContentTitle("Continuing workout")
            .setContentText("00:00")
            .setContentIntent(getPendingIntent())

    private fun getPendingIntent() = PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java).also {
        it.action = Const.ACTION_SHOW_TRACKING_FRAGMENT
    },
        PendingIntent.FLAG_UPDATE_CURRENT
    )
}