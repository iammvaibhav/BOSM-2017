package com.bitspilanidvm.bosm2017

import android.animation.FloatEvaluator
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
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

    val floatEvaluator = FloatEvaluator()
    val displayMetrics = DisplayMetrics()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        mainNavRecyclerView = findViewById(R.id.mainNavRecyclerView)
        toolBar = findViewById(R.id.toolBar)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationTabBar = findViewById(R.id.ntb_vertical)

        window.setBackgroundDrawableResource(R.drawable.r)
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        setSupportActionBar(toolBar)
        supportActionBar?.title = ""


        val clickListener = object : MainRecyclerViewClickListener {
            override fun onItemClick(itemHolder: ViewHolder_MainItem, position: Int) {
                if (position in 0..3) {
                    val intent = Intent(this@MainNavigation, DetailsActivity::class.java)
                    intent.putExtra("page", position)
                    startActivity(intent)
                }
            }
        }

        val navigationTabListener = object : NavigationTabBar.OnTabBarSelectedIndexListener{
            override fun onEndTabSelected(model: NavigationTabBar.Model?, index: Int) {
                if (index in 0..3){
                    mainNavRecyclerView.smoothScrollToPosition(index + 4)
                }
            }

            override fun onStartTabSelected(model: NavigationTabBar.Model?, index: Int) {

            }
        }

        val layoutManager = LinearLayoutManager(this)
        val recyclerAdapter = Adapter_MainRecyclerView(getMainNavData(), clickListener)
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
                mainNavRecyclerView.translationX = floatEvaluator.evaluate(slideOffset, 0f, 20.toPx())

                for (i in layoutManager.findFirstVisibleItemPosition()..layoutManager.findLastVisibleItemPosition()){
                    (mainNavRecyclerView.findViewHolderForLayoutPosition(i) as ViewHolder_MainItem).itemText.translationX = floatEvaluator.evaluate(slideOffset, 0f, 70.toPx())
                }
            }
        }

        drawerLayout.addDrawerListener(drawerToggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        drawerToggle.syncState()
        initNTB()
        navigationTabBar.onTabBarSelectedIndexListener = navigationTabListener
    }

    fun Int.toPx() = this * displayMetrics.density

    fun Int.toDp() = (this / displayMetrics.density).toInt()

    fun initNTB(){
        val colors = resources.getStringArray(R.array.vertical_ntb)

        val models: ArrayList<NavigationTabBar.Model> = ArrayList()
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_first),
                        Color.parseColor(colors[0]))
                        .title("ic_first")
                        .selectedIcon(ContextCompat.getDrawable(this, R.drawable.ic_first))
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_second),
                        Color.parseColor(colors[1]))
                        .selectedIcon(ContextCompat.getDrawable(this, R.drawable.ic_eighth))
                        .title("ic_second")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_third),
                        Color.parseColor(colors[2]))
                        .selectedIcon(ContextCompat.getDrawable(this, R.drawable.ic_eighth))
                        .title("ic_third")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_fourth),
                        Color.parseColor(colors[3]))
                        .selectedIcon(ContextCompat.getDrawable(this, R.drawable.ic_eighth))
                        .title("ic_fourth")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_fifth),
                        Color.parseColor(colors[4]))
                        .selectedIcon(ContextCompat.getDrawable(this, R.drawable.ic_eighth))
                        .title("ic_fifth")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_sixth),
                        Color.parseColor(colors[5]))
                        .selectedIcon(ContextCompat.getDrawable(this, R.drawable.ic_eighth))
                        .title("ic_sixth")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_seventh),
                        Color.parseColor(colors[6]))
                        .selectedIcon(ContextCompat.getDrawable(this, R.drawable.ic_eighth))
                        .title("ic_seventh")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.ic_eighth),
                        Color.parseColor(colors[7]))
                        .selectedIcon(ContextCompat.getDrawable(this, R.drawable.ic_eighth))
                        .title("ic_eighth")
                        .build()
        )

        navigationTabBar.models = models
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }
}

fun getMainNavData() : ArrayList<Pair<String, Int>>{

    var dataItems = ArrayList<Pair<String, Int>>()

    dataItems.add(Pair("EVENTS", R.drawable.w))
    dataItems.add(Pair("HIGHLIGHTS", R.drawable.q))
    dataItems.add(Pair("EVENTS NOW", R.drawable.u))
    dataItems.add(Pair("SPORTS SCHEDULE", R.drawable.w))
    dataItems.add(Pair("EVENTS", R.drawable.w))
    dataItems.add(Pair("HIGHLIGHTS", R.drawable.q))
    dataItems.add(Pair("EVENTS NOW", R.drawable.u))
    dataItems.add(Pair("SPORTS SCHEDULE", R.drawable.w))

    return dataItems
}
