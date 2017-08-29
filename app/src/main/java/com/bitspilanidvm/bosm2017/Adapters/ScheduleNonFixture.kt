package com.bitspilanidvm.bosm2017.Adapters

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bitspilanidvm.bosm2017.R
import com.bitspilanidvm.bosm2017.Universal.NonFixtureSportsDataDecoupled
import com.bitspilanidvm.bosm2017.ViewHolder.ScheduleNonFixture
import java.text.SimpleDateFormat

class ScheduleNonFixture(val data: List<NonFixtureSportsDataDecoupled>, val typeface: Typeface) : RecyclerView.Adapter<ScheduleNonFixture>(){

    val dateFormat = SimpleDateFormat("MMM dd yyyy HH:mm:ss")
    val formattedDate = SimpleDateFormat("MMMM dd")
    val formattedTime = SimpleDateFormat("HH:mm")

    override fun onBindViewHolder(holder: ScheduleNonFixture, position: Int) {
        holder.categoryName.text = data[position].categoryName
        holder.categoryDescription.text = data[position].categoryDescription
        holder.round.text = data[position].round
        val date = dateFormat.parse("${data[position].date} ${data[position].time}")
        holder.tdv.text = "${formattedDate.format(date)} | ${formattedTime.format(date)} | ${data[position].venue}"

        holder.round.typeface = typeface
        holder.tdv.typeface = typeface
        holder.categoryName.typeface = typeface
        holder.categoryDescription.typeface = typeface
        holder.categoryNameText.typeface = typeface
        holder.categoryDescriptionText.typeface = typeface
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleNonFixture {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.schedule_nonfixture_item_layout_new, parent, false)
        return com.bitspilanidvm.bosm2017.ViewHolder.ScheduleNonFixture(view)
    }

    override fun getItemCount() = data.size
}