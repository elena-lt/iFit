package com.example.ifit.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.ifit.R
import com.example.ifit.other.Const
import com.example.ifit.other.Const.ACTION_SHOW_TRACKING_FRAGMENT
import com.example.ifit.ui.authentication.AuthenticationActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    var user: FirebaseUser? = null

//    val authState = FirebaseAuth.AuthStateListener { state ->
//        user = firebaseAuth.currentUser
//        if (user == null) {
//            startActivity(Intent(this, AuthenticationActivity::class.java))
//            finish()
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragmentContainerMain) as NavHostFragment
        navController = navHostFragment.navController

        //firebaseAuth.addAuthStateListener(this.authState)

        navigateToTrackingFragmentIfNeeded(intent)

        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnNavigationItemReselectedListener { /* NO-OP */ } //not to reload the same fragment when reselected

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.runSummaryFragment, R.id.runTrackingFragment, R.id.settingsFragment -> bottomNavigationView.visibility =
                    View.GONE
            }
        }
    }

    override fun onStop() {
        super.onStop()
        //firebaseAuth.removeAuthStateListener(this.authState)
    }


    private fun navigateToTrackingFragmentIfNeeded(intent: Intent?) {
        if (intent?.action == ACTION_SHOW_TRACKING_FRAGMENT) {
            navController.navigate(R.id.action_runFragment_to_runTrackingFragment)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }
}