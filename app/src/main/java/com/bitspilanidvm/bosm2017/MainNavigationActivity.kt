package com.bitspilanidvm.bosm2017

import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.DisplayMetrics
import android.view.View
import devlight.io.library.ntb.NavigationTabBar



class MainNavigation : AppCompatActivity() {

    lateinit var toolBar: Toolbar
    lateinit var mainNavRecyclerView: RecyclerView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationTabBar: NavigationTabBar
    lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation)

        mainNavRecyclerView = findViewById(R.id.mainNavRecyclerView)
        toolBar = findViewById(R.id.toolBar)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationTabBar = findViewById(R.id.ntb_vertical)


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

        drawerToggle = object : ActionBarDrawerToggle(this,
                drawerLayout,
                toolBar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close){
            override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                mainNavRecyclerView.translationX = (slideOffset * 100)

                for (i in layoutManager.findFirstVisibleItemPosition()..layoutManager.findLastVisibleItemPosition()){
                    (mainNavRecyclerView.findViewHolderForLayoutPosition(i) as MainItemViewHolder).itemText.translationX = slideOffset * 120
                }
            }
        }

        drawerLayout.addDrawerListener(drawerToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        drawerToggle.syncState()


        initNTB()
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

    fun initNTB(){
        val colors = resources.getStringArray(R.array.vertical_ntb)

        val models: ArrayList<NavigationTabBar.Model> = ArrayList()
        models.add(
                NavigationTabBar.Model.Builder(
                        resources.getDrawable(R.drawable.ic_first),
                        Color.parseColor(colors[0]))
                        .title("ic_first")
                        .selectedIcon(resources.getDrawable(R.drawable.ic_eighth))
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        resources.getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[1]))
                        .selectedIcon(resources.getDrawable(R.drawable.ic_eighth))
                        .title("ic_second")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        resources.getDrawable(R.drawable.ic_third),
                        Color.parseColor(colors[2]))
                        .selectedIcon(resources.getDrawable(R.drawable.ic_eighth))
                        .title("ic_third")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        resources.getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[3]))
                        .selectedIcon(resources.getDrawable(R.drawable.ic_eighth))
                        .title("ic_fourth")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        resources.getDrawable(R.drawable.ic_fifth),
                        Color.parseColor(colors[4]))
                        .selectedIcon(resources.getDrawable(R.drawable.ic_eighth))
                        .title("ic_fifth")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        resources.getDrawable(R.drawable.ic_sixth),
                        Color.parseColor(colors[5]))
                        .selectedIcon(resources.getDrawable(R.drawable.ic_eighth))
                        .title("ic_sixth")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        resources.getDrawable(R.drawable.ic_seventh),
                        Color.parseColor(colors[6]))
                        .selectedIcon(resources.getDrawable(R.drawable.ic_eighth))
                        .title("ic_seventh")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        resources.getDrawable(R.drawable.ic_eighth),
                        Color.parseColor(colors[7]))
                        .selectedIcon(resources.getDrawable(R.drawable.ic_eighth))
                        .title("ic_eighth")
                        .build()
        )

        navigationTabBar.models = models
        navigationTabBar.titleMode = NavigationTabBar.TitleMode.ACTIVE
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
