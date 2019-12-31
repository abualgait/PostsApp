package com.abualgait.msayed.thiqah.shared.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBindView(`object`: Any, position: Int)


}