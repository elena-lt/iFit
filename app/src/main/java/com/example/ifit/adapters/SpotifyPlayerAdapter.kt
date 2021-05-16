package com.example.ifit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ifit.R
import com.example.ifit.models.modelsArch.SpotifyPlaylist
import kotlinx.android.synthetic.main.item_playlist.view.*

class SpotifyPlayerAdapter (val onClickListener: (SpotifyPlaylist) -> Unit) : RecyclerView.Adapter<SpotifyPlayerAdapter.PlaylistViewHolder>() {

    inner class PlaylistViewHolder(itemView: View, onClickListener: (SpotifyPlaylist) -> Unit) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onClickListener(differ.currentList[adapterPosition])
            }
        }
    }

    val diffUtil = object : DiffUtil.ItemCallback<SpotifyPlaylist>() {
        override fun areItemsTheSame(oldItem: SpotifyPlaylist, newItem: SpotifyPlaylist): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SpotifyPlaylist, newItem: SpotifyPlaylist): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    fun submitList(list: List<SpotifyPlaylist>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist, parent, false)
        return PlaylistViewHolder(view){
            onClickListener(it)
        }
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlist = differ.currentList[position]
        holder.itemView.apply {
            txtPlaylistName.text = playlist.name

            Glide.with(this).load(playlist.images[0].uri)
                .apply(RequestOptions.overrideOf(100, 100))
                .into(imgPlaylist)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}