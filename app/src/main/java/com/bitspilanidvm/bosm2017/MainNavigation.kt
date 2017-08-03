package com.bitspilanidvm.bosm2017

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.DisplayMetrics
import android.view.View

class MainNavigation : AppCompatActivity() {

    lateinit var toolBar: Toolbar
    lateinit var mainNavRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation)

        mainNavRecyclerView = findViewById(R.id.mainNavRecyclerView)
        toolBar = findViewById(R.id.toolBar)

        window.setBackgroundDrawableResource(R.drawable.r)
        setSupportActionBar(toolBar)
        supportActionBar?.title = ""

        val layoutManager = LinearLayoutManager(this)
        val recyclerAdapter = MainRecyclerViewAdapter(getMainNavData())
        val recyclerAnimatorAdapter = CustomAnimator(recyclerAdapter)

        with(mainNavRecyclerView){
            setLayoutManager(layoutManager)
            adapter = recyclerAnimatorAdapter
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            isDrawingCacheEnabled = true
            drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        }

    }

    fun Int.toPx() : Int{
        var displayMetrics = DisplayMetrics()
        this@MainNavigation.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return (this * displayMetrics.density).toInt()
    }

    fun Int.toDp() : Int{
        var displayMetrics = DisplayMetrics()
        this@MainNavigation.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return (this / displayMetrics.density).toInt()
    }
}

fun getMainNavData() : ArrayList<Pair<String, Int>>{

    var dataItems = ArrayList<Pair<String, Int>>()

    dataItems.add(Pair("HIGHLIGHTS", R.drawable.w))
    dataItems.add(Pair("SCIENCE", R.drawable.q))
    dataItems.add(Pair("GAMING", R.drawable.u))
    dataItems.add(Pair("MOVIES", R.drawable.w))
    dataItems.add(Pair("HIGHLIGHTS", R.drawable.q))
    dataItems.add(Pair("SCIENCE", R.drawable.u))
    dataItems.add(Pair("GAMING", R.drawable.w))
    dataItems.add(Pair("MOVIES", R.drawable.q))

    return dataItems
}
