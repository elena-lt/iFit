package com.example.ifit.ui.main.statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.ifit.R
import com.example.ifit.adapters.RunHistoryAdapter
import com.example.ifit.models.Run
import com.example.ifit.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_statistics.*

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    val viewModel: MainViewModel by viewModels()

    private lateinit var runAdapter: RunHistoryAdapter

    private lateinit var runList: List<Run>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        subscribeToObservers()

    }

    private fun subscribeToObservers(){
//        viewModel.allRuns.observe(viewLifecycleOwner, Observer {
//            runAdapter.submitList(it)
//        })
    }

    private fun setupRecyclerView() = rvRuns.apply {
        runAdapter = RunHistoryAdapter()
        adapter = runAdapter
    }

}