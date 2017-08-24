package com.bitspilanidvm.bosm2017.ViewHolder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.bitspilanidvm.bosm2017.R

class ScheduleFixture(itemView: View) : RecyclerView.ViewHolder(itemView){
    val round: TextView
    val team1: TextView
    val team2: TextView
    val date: TextView
    val time: TextView
    val venue: TextView

    init {
        round = itemView.findViewById(R.id.round)
        team1 = itemView.findViewById(R.id.team1)
        team2 = itemView.findViewById(R.id.team2)
        date = itemView.findViewById(R.id.date)
        time = itemView.findViewById(R.id.time)
        venue = itemView.findViewById(R.id.venue)
    }
}