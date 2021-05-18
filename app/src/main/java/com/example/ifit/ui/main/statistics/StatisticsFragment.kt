package com.example.ifit.ui.main.statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.domain.utils.Resource
import com.example.ifit.R
import com.example.ifit.adapters.RunHistoryAdapter
import com.example.ifit.models.Run
import com.example.ifit.ui.main.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_statistics.*
import javax.inject.Inject

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    val viewModel: MainViewModel by viewModels()

    private lateinit var runAdapter: RunHistoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val user = firebaseAuth.currentUser

        viewModel.loadRuns(user?.email!!)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        viewModel.runs.observe(viewLifecycleOwner, { runs ->
            runAdapter.submitList(runs)
        })

        viewModel.error.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), "An error occurred: $it", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupRecyclerView() = rvRuns.apply {
        runAdapter = RunHistoryAdapter()
        adapter = runAdapter
    }

}