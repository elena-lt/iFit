package com.example.ifit.ui.main.runFragment

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ifit.R
import com.example.ifit.other.Const
import com.example.ifit.other.Const.SPOTIFY_LOGON_REQUEST_CODE
import com.example.ifit.other.LocationTrackingUtility
import com.example.ifit.ui.main.spotifyMusic.SpotifyViewModel
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_run.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class RunFragment : Fragment(R.layout.fragment_run), EasyPermissions.PermissionCallbacks {

    private val spotifyViewModel: SpotifyViewModel by viewModels()

    private var accessToken: String? = null
    var isSpotifyAuthSuccessful: Boolean = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val firebase = FirebaseAuth.getInstance()
//        val user = firebase.currentUser
//        if (user == null){
//            Log.d ("FirebaseAuth", "No user logged in")
//        }else {
//            Log.d ("FirebaseAuth", "User is logged in: ${user.email!!}")
//        }

        btnGoToProfile.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_userProfileFragment)
        }

        btnStartRun.setOnClickListener {
            //if statement if permissions are granted
            checkPermissions()
            findNavController().navigate(R.id.action_runFragment_to_runTrackingFragment)
        }

        btnPlayMusic.setOnClickListener {
            getSpotifyAccessToken()
        }
    }

    private fun checkPermissions() {
        if (LocationTrackingUtility.hasLocationPermissions(requireContext())) {

            //TODO
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                EasyPermissions.requestPermissions(
                    this, "You need to accept location permission to use the app",
                    Const.REQUEST_CODE_LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } else {
                EasyPermissions.requestPermissions(
                    this, "You need to accept location permission to use the app",
                    Const.REQUEST_CODE_LOCATION_PERMISSION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
            }
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        /*NO-OP*/
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else checkPermissions()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //    private fun checkSettingAndStartLocationUpdates() {
//        val locationSettingRequest = LocationSettingsRequest.Builder()
//            .addLocationRequest(mLocationRequest).build()
//        val client = LocationServices.getSettingsClient(requireActivity())
//
//        val locationSettingsResponse = client.checkLocationSettings(locationSettingRequest)
//        locationSettingsResponse.addOnSuccessListener {
//            startLocationUpdates()
//            Log.d (TAG, "On success")
//        }
//        locationSettingsResponse.addOnFailureListener{
//            Log.d (TAG, "${it.message}")
//            //RESOLUTION_REQUIRED error
//
//        }
//    }

    private fun getSpotifyAccessToken() {
        val request = spotifyViewModel.provideSpotifyAuthorizationRequest()

        val intent = AuthorizationClient.createLoginActivityIntent(
            activity,
            request
        ) as Intent
        startActivityForResult(intent, SPOTIFY_LOGON_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == SPOTIFY_LOGON_REQUEST_CODE) {
            val response: AuthorizationResponse = AuthorizationClient.getResponse(resultCode, data)

            when (response.type) {
                AuthorizationResponse.Type.ERROR -> {
                    isSpotifyAuthSuccessful = false
                    Log.d("Spotify", "Error: ${response.error}, ${response.code}")
                }
                AuthorizationResponse.Type.TOKEN -> {
                    accessToken = response.accessToken
                    Log.d("Spotify", "Token: ${response.accessToken}")
                    isSpotifyAuthSuccessful = true
                    openSpotifyDialogFragment()
                }
            }
        }
    }

    private fun openSpotifyDialogFragment() {
        val action = accessToken?.let { RunFragmentDirections.actionRunFragmentToSpotifyPlayer(it) }
        if (action != null) {
            findNavController().navigate(action)
        }
    }

    //    private fun checkIfSpotifyInstalled(){
//        val pm: PackageManager = requireContext().packageManager
//        var isSpotifyInstalled: Boolean
//        try {
//            pm.getPackageInfo("com.spotify.music", 0)
//            isSpotifyInstalled = true
//
//        }catch (e: PackageManager.NameNotFoundException){
//            isSpotifyInstalled = false
//        }
//    }
//

    companion object {
        const val TAG = "Tracking"
    }
}