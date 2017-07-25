package com.bitspilanidvm.bosm2017

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar

class MainNavigation : AppCompatActivity() {

    lateinit var toolBar: Toolbar
    lateinit var mainNavRecyclerView: RecyclerView

    var dataItems = ArrayList<Pair<String, Int>>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation)

        window.setBackgroundDrawableResource(R.drawable.temp)

        dataItems.add(Pair("This is A", android.R.color.holo_blue_dark))
        dataItems.add(Pair("This is B", android.R.color.holo_red_dark))
        dataItems.add(Pair("This is C", android.R.color.holo_orange_dark))
        dataItems.add(Pair("This is D", android.R.color.holo_green_dark))
        dataItems.add(Pair("This is E", android.R.color.holo_purple))
        dataItems.add(Pair("This is F", android.R.color.black))
        dataItems.add(Pair("This is G", android.R.color.holo_green_light))
        dataItems.add(Pair("This is H", android.R.color.holo_blue_bright))
        dataItems.add(Pair("This is I", android.R.color.holo_red_light))

        toolBar = findViewById(R.id.toolBar)
        mainNavRecyclerView = findViewById(R.id.mainNavRecyclerView)

        mainNavRecyclerView.layoutManager = LinearLayoutManager(this)
        mainNavRecyclerView.adapter = MainRecyclerViewAdapter(dataItems)

    }
}
