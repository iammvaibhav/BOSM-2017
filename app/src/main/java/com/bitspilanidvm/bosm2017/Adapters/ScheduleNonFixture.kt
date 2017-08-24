package com.bitspilanidvm.bosm2017.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bitspilanidvm.bosm2017.NonFixtureSportsDataDecoupled
import com.bitspilanidvm.bosm2017.R
import com.bitspilanidvm.bosm2017.ViewHolder.ScheduleNonFixture

class ScheduleNonFixture(val data: List<NonFixtureSportsDataDecoupled>) : RecyclerView.Adapter<ScheduleNonFixture>(){
    override fun onBindViewHolder(holder: ScheduleNonFixture, position: Int) {
        holder.categoryName.text = data[position].categoryName
        holder.categoryDescription.text = data[position].categoryDescription
        holder.round.text = data[position].round
        holder.date.text = data[position].date
        holder.time.text = data[position].time
        holder.venue.text = data[position].time
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleNonFixture {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.schedule_nonfixture_item_layout, parent, false)
        return com.bitspilanidvm.bosm2017.ViewHolder.ScheduleNonFixture(view)
    }

    override fun getItemCount() = data.size
}