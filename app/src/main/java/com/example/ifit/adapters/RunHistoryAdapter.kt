package com.example.ifit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ifit.R
import com.example.ifit.models.Run
import com.example.ifit.other.TimerUtil
import kotlinx.android.synthetic.main.item_run.view.*
import java.text.SimpleDateFormat
import java.util.*

class RunHistoryAdapter : RecyclerView.Adapter<RunHistoryAdapter.RunViewHolder>() {

    inner class RunViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    private val diffUtil = object : DiffUtil.ItemCallback<Run>(){
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }
    private val differ = AsyncListDiffer(this, diffUtil)

    fun submitList (list: List<Run>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_run, parent, false))
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val runItem = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this).load(runItem.image).into(imgMapImage)

            val calendar = Calendar.getInstance().apply {
                timeInMillis = runItem.timeStamp
            }
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            txtRunDate.text = dateFormat.format(calendar.time)

            val time = TimerUtil.getFormattedTime(runItem.timeInMillis, false)
            txtDuration.text = time

            val avgSpeed = "${runItem.avgSpeed} km/h"
            txtAvgSpeed.text = avgSpeed

            val calories = "${runItem.caloriesBurned} kcal"
            txtCaloriesBurned.text = calories
        }
    }

    override fun getItemCount(): Int = differ.currentList.size


}