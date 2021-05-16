package com.example.ifit.ui.main.runFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ifit.R
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_run_summary.*
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class RunSummaryFragment: Fragment(R.layout.fragment_run_summary) {

    val args: RunSummaryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_run_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val img = args.run.image
        imgRun.setImageBitmap(img)

//        val userName = if (mAuth.currentUser !=null) mAuth.currentUser!!.displayName else ""
//        txtGreatJob.text = "Great Job, $userName"


        val distanceText = "%.2f".format(args.run.distanceInKilometers)
        txtDistance.text = "$distanceText km"

        btnMoreData.setOnClickListener {
            val action = RunSummaryFragmentDirections.actionRunSummaryFragmentToSummaryCompleteFragment(args.run)
            findNavController().navigate(action)
        }

        btnGoToMainPage.setOnClickListener {
            findNavController().navigate(R.id.action_runSummaryFragment_to_runFragment)
        }
    }

}