package com.bitspilanidvm.bosm2017

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar

class MainNavigation : AppCompatActivity() {

    lateinit var toolBar: Toolbar
    lateinit var mainNavRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation)

        toolBar = findViewById(R.id.toolBar)
        mainNavRecyclerView = findViewById(R.id.mainNavRecyclerView)
    }
}
