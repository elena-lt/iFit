package com.example.ifit.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.ifit.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_join_us.*

@AndroidEntryPoint
class JoinUsFragment : Fragment(R.layout.fragment_join_us) {

    private val viewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_joinUsFragment_to_loginFragment)
        }

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val repeatPassword = etRepeatPassword.text.toString().trim()

            signUpUSer(email, password, repeatPassword)

        }

        subscribeToObservers()
    }

    private fun signUpUSer(email: String, password: String, repeatPassword: String) {
        viewModel.signUpUser(email, password, repeatPassword)
    }

    private fun subscribeToObservers() {

        viewModel.dataLoading.observe(viewLifecycleOwner, { loading ->
            when (loading) {
                true -> showProgressBar()
            }
        })

        viewModel.isUserAuthenticated.observe(viewLifecycleOwner, { userAuthenticated ->
            when (userAuthenticated) {
                true -> {
                    moveToNextScreen()
                }
                false -> {/*NO-OPS*/
                }
            }
        })

        viewModel.error.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), "An error occured: $it")
        })
    }

    private fun moveToNextScreen() {
        findNavController().navigate(R.id.action_joinUsFragment_to_extraUserDataFragment)
    }

    private fun showProgressBar() {
        progrss_bar.visibility = View.VISIBLE
    }

}