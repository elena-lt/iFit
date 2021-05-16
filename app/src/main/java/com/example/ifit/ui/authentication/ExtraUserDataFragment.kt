package com.example.ifit.ui.authentication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.example.ifit.R
import com.example.ifit.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_extra_user_data.*

@AndroidEntryPoint
class ExtraUserDataFragment : Fragment(R.layout.fragment_extra_user_data) {

    private val viewModel: AuthenticationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSaveData.setOnClickListener {
            val firstName = etFirstName.text.toString().trim()
            val lastName = etLastName.text.toString().trim()
            val weight = (etWeight.text.toString().trim()).toDouble()

            saveUserData(firstName, lastName,  weight)
        }

        subscribeToObservers()
    }

    private fun saveUserData(firstName: String, lastName: String,weight: Double) {
        viewModel.saveUserData(firstName, lastName, weight)
        moveToMainActivity()
    }

    private fun subscribeToObservers() {

    }

    private fun moveToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

}