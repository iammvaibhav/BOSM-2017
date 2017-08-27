package com.bitspilanidvm.bosm2017.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bitspilanidvm.bosm2017.Modals.FixtureSportsData
import com.bitspilanidvm.bosm2017.R
import com.bitspilanidvm.bosm2017.ViewHolder.ScheduleFixture

class ScheduleFixture(val data: List<FixtureSportsData>) : RecyclerView.Adapter<ScheduleFixture>(){

    override fun onBindViewHolder(holder: ScheduleFixture, position: Int) {
        holder.round.text = data[position].round
        holder.team1.text = data[position].teamA
        holder.team2.text = data[position].teamB
        holder.date.text = data[position].date
        holder.time.text = data[position].time
        holder.venue.text = data[position].venue
    }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleFixture {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.schedule_fixture_item_layout, parent, false)
        return com.bitspilanidvm.bosm2017.ViewHolder.ScheduleFixture(view)
    }
}