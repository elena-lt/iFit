package com.example.ifit.ui.main.runFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.ifit.R
import com.example.ifit.models.Run
import com.example.ifit.other.Const
import com.example.ifit.other.Const.POLYLINE_COLOR
import com.example.ifit.other.Const.POLYLINE_WIDTH
import com.example.ifit.other.TimerUtil
import com.example.ifit.other.VectorAssetToBitmapConverter
import com.example.ifit.services.LocationTrackingService
import com.example.ifit.ui.main.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_run_tracking.*
import java.util.*
import kotlin.math.round

@AndroidEntryPoint
class RunTrackingFragment : Fragment(R.layout.fragment_run_tracking) {

    private val viewModel: MainViewModel by viewModels()

    private var mGoogleMap: GoogleMap? = null
    private var marker: Marker? = null
    private var pathPoints = mutableListOf<LatLng>()

    private var timeRun = 0L
    private var timeRunText: String = ""
    private var distanceRunInKm: Double = 0.0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync {
            mGoogleMap = it
            val success = mGoogleMap?.setMapStyle(MapStyleOptions(resources.getString(R.string.google_map_night_mode)))
            if (success == false) {
                Log.d(TAG, "Map initializing failed")
            }
            addPolyline()
        }

        startTracking()
        subscribeToObservers()

        btnPause.setOnClickListener {
            sendCommandToService(Const.ACTION_PAUSE_SERVICE)
        }
        btnStop.setOnClickListener {
            Snackbar.make(requireView(), "Tap and hold to stop run", Snackbar.LENGTH_LONG).show()
        }

        btnStop.setOnLongClickListener {
            zoomToSeeWholeTrack()
            saveRunToDB()
            true
        }
    }

    private fun subscribeToObservers() {
        LocationTrackingService.pathPoints.observe(viewLifecycleOwner, {
            pathPoints = it
            addPathPoints()
            if (pathPoints.isNotEmpty()) {
                updateUserLocationMarker(pathPoints.last())
            }
            moveCameraToUser()
        })

        LocationTrackingService.timerMillis.observe(viewLifecycleOwner, {
            timeRun = it
            timeRunText = TimerUtil.getFormattedTime(it, false)
            txtTime.text = timeRunText
        })

        LocationTrackingService.distanceSpherical.observe(viewLifecycleOwner, {
            distanceRunInKm = it / 1000
            val distance = "%.2f".format(distanceRunInKm)
            txtDistance.text = "$distance km"
        })
    }

    private fun sendCommandToService(action: String) =
        Intent(requireContext(), LocationTrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }

    private fun startTracking() {
        sendCommandToService(Const.ACTION_START_OR_RESUME_SERVICE)
    }

    private fun stopTracking() {
        sendCommandToService(Const.ACTION_STOP_SERVICE)
    }

    private fun addPathPoints() {
        if (pathPoints.isNotEmpty() && pathPoints.size > 1) {
            val preLatLng = pathPoints[pathPoints.size - 2]
            val currLatLng = pathPoints.last()
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .add(preLatLng)
                .add(currLatLng)

            mGoogleMap?.apply {
                addPolyline(polylineOptions)
            }
        }
    }

    private fun addPolyline() {
        val polylineOptions = PolylineOptions()
            .addAll(pathPoints)
            .color(POLYLINE_COLOR)
            .width(POLYLINE_WIDTH)
        mGoogleMap?.addPolyline(polylineOptions)

    }

    private fun moveCameraToUser() {
        if (pathPoints.isNotEmpty()) {
            mGoogleMap?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(pathPoints.last(), 15F)
            )
        }

        if (pathPoints.isNotEmpty()) {
            mGoogleMap?.apply {
                animateCamera(CameraUpdateFactory.newLatLngZoom(pathPoints.last(), 18F))

            }
        }
    }

    private fun zoomToSeeWholeTrack() {
        val bound = LatLngBounds.Builder()
        for (pathPoint in pathPoints) {
            bound.include(pathPoint)
        }

        mGoogleMap?.moveCamera(
            CameraUpdateFactory.newLatLngBounds(
                bound.build(),
                mapView.width,
                mapView.height,
                (mapView.height * 0.05f).toInt()
            )
        )
    }

    private fun updateUserLocationMarker(latLng: LatLng) {
        if (pathPoints.isNotEmpty()) {
            if (marker == null) {
                val markerOptions = MarkerOptions()
                    .position(latLng)
                    .anchor(0.5f, 0.5f)
                    .icon(
                        VectorAssetToBitmapConverter.bitmapDescriptorFromVector(
                            requireContext(),
                            R.drawable.ic_running_google_map_merker
                        )
                    )
                marker = mGoogleMap?.addMarker(markerOptions)
            } else {
                marker!!.position = latLng
            }
        }
    }

    private fun saveRunToDB() {
        mGoogleMap?.snapshot {
            val timeStamp = Calendar.getInstance().timeInMillis
            val distance = "%.2f".format(distanceRunInKm).toDouble()
            val timeInHours = (timeRun / 1000f / 60 / 60)
            val time = "%.2f".format(timeInHours).toDouble()

            val avgSpeed = round(distance / time)

            val run = Run(it, timeStamp, avgSpeed, 0, distance, timeRun)
            //viewModel.insertRunIntoDB(run)

            stopTracking()

            val action = RunTrackingFragmentDirections.actionRunTrackingFragmentToRunSummaryFragment(run)
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    companion object {
        private const val TAG = "TrackingFragment"
    }

}