package com.bitspilanidvm.bosm2017.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bitspilanidvm.bosm2017.NonFixtureSportsDataDecoupled
import com.bitspilanidvm.bosm2017.R
import com.bitspilanidvm.bosm2017.ViewHolder.ResultsNonFixture

class ResultsNonFixture(val data: List<NonFixtureSportsDataDecoupled>) : RecyclerView.Adapter<ResultsNonFixture>(){

    override fun onBindViewHolder(holder: ResultsNonFixture, position: Int) {
        holder.categoryName.text = data[position].categoryName
        holder.categoryDescription.text = data[position].categoryDescription
        holder.round.text = data[position].round
        holder.date.text = data[position].date
        holder.time.text = data[position].time
        holder.venue.text = data[position].venue
        holder.first.text = data[position].categoryResult[0]
        holder.second.text = data[position].categoryResult[1]
        holder.third.text = data[position].categoryResult[2]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsNonFixture {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.results_nonfixture_item_layout, parent, false)
        return com.bitspilanidvm.bosm2017.ViewHolder.ResultsNonFixture(view)
    }

    override fun getItemCount() = data.size
}