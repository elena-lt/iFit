package com.example.ifit.ui.main.runFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ifit.R
import com.example.ifit.other.TimerUtil
import kotlinx.android.synthetic.main.fragment_summary_complete.*

class SummaryCompleteFragment : Fragment(R.layout.fragment_summary_complete) {

    val args: SummaryCompleteFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgRun.setImageBitmap(args.run.image)

        val avgSpeedText = (args.run.avgSpeed).toString()
        txtAvgSpeed.text = avgSpeedText

        val time = args.run.timeInMillis?.let { TimerUtil.getFormattedTime(it, false) }
        txtDuration.text = time

        btnGoToMainPage.setOnClickListener {
            findNavController().navigate(R.id.action_summaryCompleteFragment_to_runFragment)
        }
    }
    companion object {
        const val TAG = "Tracking"
    }
}