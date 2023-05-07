package com.alican.mvvm_starter.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alican.mvvm_starter.data.local.model.SatelliteModel
import com.alican.mvvm_starter.databinding.ItemSatelliteBinding

class SatelliteAdapter(val onClick: (SatelliteModel) -> Unit) :
    ListAdapter<SatelliteModel, SatelliteAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<SatelliteModel>() {
        override fun areItemsTheSame(oldItem: SatelliteModel, newItem: SatelliteModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: SatelliteModel,
            newItem: SatelliteModel
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    class ViewHolder(val binding: ItemSatelliteBinding, onClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSatelliteBinding.inflate(LayoutInflater.from(parent.context))

        return ViewHolder(binding) {
            onClick(getItem(it))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.data = getItem(position)
        holder.binding.executePendingBindings()
    }
}