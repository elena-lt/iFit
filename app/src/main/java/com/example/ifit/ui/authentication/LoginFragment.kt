package com.example.ifit.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.ifit.R
import com.example.ifit.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.btnLogin
import kotlinx.android.synthetic.main.fragment_login.btnRegister
import kotlinx.android.synthetic.main.fragment_login.etEmail
import kotlinx.android.synthetic.main.fragment_login.etPassword
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

//    @Inject
//    lateinit var firebaseAuth: FirebaseAuth
//    var user: FirebaseUser? = null

    private val viewModel: AuthenticationViewModel by viewModels()

//
//    val authState = FirebaseAuth.AuthStateListener { state ->
//        user = firebaseAuth.currentUser
//        if (user != null) {
//            startActivity(Intent(requireContext(), MainActivity::class.java))
//            requireActivity().finish()
//        }
//    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        firebaseAuth.addAuthStateListener(this.authState)
//    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //user = firebaseAuth.currentUser

//       if (user != null){
//           Log.d ("FirebaseAuth", "User is still logged in: ${user!!.email}")
//       }else {
//           Log.d ("FirebaseAuth", "No active user")
//       }

        btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_joinUsFragment)
        }

        btnLogin.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            loginUser(email, password)
        }
        subscribeToObservers()
    }

    override fun onStop() {
        super.onStop()
        //firebaseAuth.removeAuthStateListener(this.authState)
    }

    private fun loginUser(email: String, password: String) {
        viewModel.signInUser(email, password)
    }

    private fun subscribeToObservers() {
        viewModel.dataLoading.observe(viewLifecycleOwner, {
            when (it) {
                true -> showProgressBar()
                false -> {/*NO-OP*/
                }
            }
        })

        viewModel.isUserAuthenticated.observe(viewLifecycleOwner, {
            when (it){
                true -> startMainActivity()
                false -> {/*NO-OP*/}
            }
        })

        viewModel.error.observe(viewLifecycleOwner, {
            Log.d (TAG, "Error while logging: $it")
            Toast.makeText(requireContext(), "An error occured: $it", Toast.LENGTH_SHORT).show()
        })
    }

    private fun showProgressBar() {
        progress_bar.visibility = View.VISIBLE
    }


    private fun startMainActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        activity?.finish()
    }

    companion object {
        private const val TAG = "LoginFragment"
    }

}