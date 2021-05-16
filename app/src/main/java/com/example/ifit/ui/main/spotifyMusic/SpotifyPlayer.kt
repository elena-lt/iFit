package com.example.ifit.ui.main.spotifyMusic

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.ifit.R
import com.example.ifit.adapters.SpotifyPlayerAdapter
import com.example.ifit.models.modelsArch.SpotifyPlaylist
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dialog_spotify_player.*

/**
 * method to set the width of the dialog fragment to a @percentage of the current screen width
 */
fun DialogFragment.setWidthPercent(percentage: Int) {
    val percent = percentage.toFloat() / 100
    val dm = Resources.getSystem().displayMetrics
    val rect = dm.run {
        Rect(0, 0, widthPixels, heightPixels)
    }
    val percentWidth = rect.width() * percent
    dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
}

@AndroidEntryPoint
class SpotifyPlayer : DialogFragment(R.layout.fragment_dialog_spotify_player) {

    private val args: SpotifyPlayerArgs by navArgs()
    private val viewModel: SpotifyViewModel by viewModels()

    private lateinit var playerAdapter: SpotifyPlayerAdapter
    private lateinit var accessToken: String

    private var isDeviceActive: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accessToken = args.accessToken

        loadPlaylists(accessToken)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accessToken = args.accessToken

        setupRecycler()

//        getCurrentPlayback(accessToken)

        subscribeToObservers()

        btnPlayNext.setOnClickListener {
            playNext(accessToken)
        }

        btnPlayPrevious.setOnClickListener {
            playPrevious(accessToken)
        }
    }

    private fun subscribeToObservers(){
        viewModel.dataLoading.observe(viewLifecycleOwner, Observer {
            when (it){
                true -> {
                    Log.d ("CleanArch", "Loading")
                }
                false -> { }
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Log.d ("CleanArch", "Error: $it")
            Toast.makeText(requireContext(), "Error occurred \n$it ", Toast.LENGTH_SHORT).show()
        })

        viewModel.userPlaylists.observe(viewLifecycleOwner, Observer {
            Log.d ("CleanArch", "${it.size}")
            playerAdapter.submitList(it)
        })
    }

//    private fun getCurrentPlayback(accessToken: String) {
//        viewModel.getCurrentPlayback(accessToken)
//    }

    private fun playPrevious(accessToken: String) {
        viewModel.playPrevious(accessToken)
    }

    private fun playNext(accessToken: String) {
        viewModel.playNext(accessToken)
    }

    private fun loadPlaylists(accessToken: String) {
        viewModel.getUserPlaylists(accessToken)
    }

    private fun playPlaylist(playlist: SpotifyPlaylist) {
            viewModel.playPlaylist(playlist, accessToken)
    }


    private fun setupRecycler() = rvUserPlaylists.apply {
        playerAdapter = SpotifyPlayerAdapter { playlist ->
            playPlaylist(playlist)
        }
        adapter = playerAdapter
    }

    companion object {
        const val TAG = "SpotifyDialog"
    }
}
