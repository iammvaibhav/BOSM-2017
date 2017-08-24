package com.bitspilanidvm.bosm2017.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.bitspilanidvm.bosm2017.R

class ScheduleNonFixture(itemView: View) : RecyclerView.ViewHolder(itemView){
    val round: TextView
    val categoryName: TextView
    val categoryDescription: TextView
    val date: TextView
    val time: TextView
    val venue: TextView

    init {
        round = itemView.findViewById(R.id.round)
        categoryName = itemView.findViewById(R.id.team1)
        categoryDescription = itemView.findViewById(R.id.team2)
        date = itemView.findViewById(R.id.date)
        time = itemView.findViewById(R.id.time)
        venue = itemView.findViewById(R.id.venue)
    }
}