package com.example.ifit.ui.main.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ifit.R
import com.example.ifit.ui.authentication.AuthenticationActivity
import com.example.ifit.ui.main.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_profile.*
import javax.inject.Inject

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {


    private val viewModel: MainViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_userProfileFragment_to_settingsFragment)
        }
    }


}