package com.vhirata.booklist.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vhirata.booklist.R
import com.vhirata.booklist.model.DoggosItem

class ApiAdapter(private val context: Context,
                 private val dataset: List<DoggosItem>
                 ): RecyclerView.Adapter<ApiAdapter.ApiViewHolder>() {


    class ApiViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        var breedNameTV: TextView = view.findViewById(R.id.breedNameTV)
        var breedHeightTV: TextView = view.findViewById(R.id.breedHeightTV)
        var breedWeightTV: TextView = view.findViewById(R.id.breedWeightTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApiAdapter.ApiViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.api_items, parent, false)
        return ApiViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ApiAdapter.ApiViewHolder, position: Int) {
        val apiItem = dataset[position]

        holder.breedNameTV.text = apiItem.name
        holder.breedHeightTV.text = apiItem.life_span
        holder.breedWeightTV.text = apiItem.temperament

    }

    override fun getItemCount() = dataset.size


}