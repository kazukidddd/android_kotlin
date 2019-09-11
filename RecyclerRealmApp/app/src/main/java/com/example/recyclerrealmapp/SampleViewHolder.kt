package com.example.recyclerrealmapp

import android.view.View
import android.widget.TextView

class HomeViewHolder(itemView: View) : MyAdapter.ViewHolder(itemView){
    val titleView: TextView = itemView.findViewById(R.id.row_title)
    val detailView: TextView = itemView.findViewById(R.id.row_detail)
}