package com.bitspilanidvm.bosm2017.Activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.androidnetworking.AndroidNetworking
import com.bitspilanidvm.bosm2017.Custom.ReverseInterpolator
import com.bitspilanidvm.bosm2017.Fragments.Details
import com.bitspilanidvm.bosm2017.Fragments.Login
import com.bitspilanidvm.bosm2017.Fragments.Registration
import com.bitspilanidvm.bosm2017.Fragments.Subscribe
import com.bitspilanidvm.bosm2017.R
import com.bitspilanidvm.bosm2017.Universal.GLOBAL_DATA
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import devlight.io.library.ntb.NavigationTabBar
import org.json.JSONObject

class Main : AppCompatActivity(), View.OnClickListener, Animator.AnimatorListener {

    lateinit var tiles: Array<CardView>
    lateinit var tilesText: Array<TextView>
    lateinit var titlesImages: Array<ImageView>
    lateinit var bosmTextView: TextView
    lateinit var hamburgerIcon: ImageView
    lateinit var drawerLayout: DrawerLayout
    lateinit var ntb: NavigationTabBar
    lateinit var headerCard: CardView

    private val displayMetrics = DisplayMetrics()
    val detailsFragment = Details()

    var isCurrentlyInTransition = false
    var isDetailsFragmentPresent = false
    var isEntering = true

    val expandedAppBarHeight = 200
    val transitionAnimationDuration = 1000L

    val rectCenter = Rect()
    val rectLeft = Array(3) { _ -> Rect() }
    val rectRight = Array(3) { _ -> Rect() }

    val rectInit = Array(4) { _ -> Rect() }
    val rectTextInit = Array(4) { _ -> Rect() }

    var username = ""
    var password = ""

    val addedSports = ArrayList<JSONObject>()
    val availableSports = ArrayList<JSONObject>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        AndroidNetworking.initialize(this)

        bosmTextView = findViewById(R.id.bosmTextView)
        hamburgerIcon = findViewById(R.id.hamburgerIcon)
        tiles = arrayOf(findViewById(R.id.schedule), findViewById(R.id.results), findViewById(R.id.events), findViewById(R.id.ongoing))
        tilesText = arrayOf(findViewById(R.id.scheduleText), findViewById(R.id.resultsText), findViewById(R.id.eventsText), findViewById(R.id.ongoingText))
        titlesImages = arrayOf(findViewById(R.id.scheduleImage), findViewById(R.id.resultsImage), findViewById(R.id.eventsImage), findViewById(R.id.ongoingImage))
        drawerLayout = findViewById(R.id.drawerLayout)
        ntb = findViewById(R.id.ntb_vertical)
        headerCard = findViewById(R.id.headerCard)

        supportFragmentManager.beginTransaction().add(R.id.rootConstraintLayout, detailsFragment).hide(detailsFragment).commit()

        windowManager.defaultDisplay.getMetrics(displayMetrics)
        window.setBackgroundDrawableResource(R.drawable.background)

        for (i in 0..3)
            picasso(this, GLOBAL_DATA.imageDrawableRes[i]).into(titlesImages[i])

        val ikarosTypeface = Typeface.createFromAsset(assets, "fonts/Ikaros-Regular.otf")
        bosmTextView.typeface = ikarosTypeface

        for (i in tilesText)
            i.typeface = ikarosTypeface

        for (i in tiles)
            i.setOnClickListener(this)

        ntb.onTabBarSelectedIndexListener = object : NavigationTabBar.OnTabBarSelectedIndexListener{
            override fun onEndTabSelected(model: NavigationTabBar.Model?, index: Int) {
                when(index){
                    0 -> {
                        showAllViewsAgain()
                        supportFragmentManager.beginTransaction().replace(R.id.rootConstraintLayout, detailsFragment).hide(detailsFragment).commit()
                        drawerLayout.closeDrawer(Gravity.START)
                    }
                    1 -> {
                        clearAllProperties()
                        supportFragmentManager.beginTransaction().replace(R.id.rootConstraintLayout, Subscribe()).commit()
                        drawerLayout.closeDrawer(Gravity.START)
                    }
                    2 -> {
                        clearAllProperties()
                        supportFragmentManager.beginTransaction().replace(R.id.rootConstraintLayout, Login()).commit()
                        drawerLayout.closeDrawer(Gravity.START)
                    }
                    3 -> {
                        clearAllProperties()
                        supportFragmentManager.beginTransaction().replace(R.id.rootConstraintLayout, Registration()).commit()
                        drawerLayout.closeDrawer(Gravity.START)
                    }
                    4 -> {
                        clearAllProperties()
                        //supportFragmentManager.beginTransaction().replace(R.id.rootConstraintLayout, Registration()).commit()
                        drawerLayout.closeDrawer(Gravity.START)
                    }
                    5 -> {
                        clearAllProperties()
                        //supportFragmentManager.beginTransaction().replace(R.id.rootConstraintLayout, Registration()).commit()
                        drawerLayout.closeDrawer(Gravity.START)
                    }
                    6 -> {
                        clearAllProperties()
                        //supportFragmentManager.beginTransaction().replace(R.id.rootConstraintLayout, Registration()).commit()
                        drawerLayout.closeDrawer(Gravity.START)
                    }
                    7 -> {
                        clearAllProperties()
                        //supportFragmentManager.beginTransaction().replace(R.id.rootConstraintLayout, Registration()).commit()
                        drawerLayout.closeDrawer(Gravity.START)
                    }
                }
            }

            override fun onStartTabSelected(model: NavigationTabBar.Model?, index: Int) {

            }
        }

        hamburgerIcon.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START)
            else
                drawerLayout.openDrawer(GravityCompat.START)
        }

        drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener(){
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                for (i in tilesText)
                    i.translationX = slideOffset * ntb.width
                for (i in tiles)
                    i.translationX = slideOffset * (ntb.width / 2f)
            }
        })

        drawerLayout.post { createRectArray() }

        initNTB()
        
    }

    override fun onClick(view: View) {

        if (!isCurrentlyInTransition) {
            val position = when(view.id){
                R.id.schedule -> 0
                R.id.results -> 1
                R.id.events -> 2
                R.id.ongoing -> 3
                else -> 4
            }

            detailsFragment.headerViewPager.currentItem = position
            detailsFragment.headerViewPager.visibility = View.INVISIBLE
            supportFragmentManager.beginTransaction().show(detailsFragment).commit()

            val oAnimator = ObjectAnimator.ofFloat(detailsFragment.detailsViewPager, "translationY", (drawerLayout.height - 200.toPx()), 0f)
            oAnimator.duration = transitionAnimationDuration
            oAnimator.interpolator = DecelerateInterpolator()
            oAnimator.start()

            isEntering = true
            getCompleteEnterAnimator(position).start()
        }
    }

    override fun onAnimationRepeat(p0: Animator?) {}

    override fun onAnimationCancel(p0: Animator?) {}

    override fun onAnimationEnd(p0: Animator?) {
        isCurrentlyInTransition = false

        if (isEntering) {
            detailsFragment.headerViewPager.visibility = View.VISIBLE
            clearAllProperties()
        }else{
            supportFragmentManager.beginTransaction().hide(detailsFragment).commit()
            isDetailsFragmentPresent = false
        }
    }

    override fun onAnimationStart(p0: Animator?) {
        isCurrentlyInTransition = true

        if (isEntering){
            isDetailsFragmentPresent = true
        }else{
            detailsFragment.headerViewPager.visibility = View.INVISIBLE
            showAllViewsAgain()
        }
    }

    fun getCompleteEnterAnimator(position: Int): Animator {
        val animatorSet = AnimatorSet()
        val animatorList = ArrayList<Animator>(4)
        for (i in 0..3) {
            when {
                i < position -> {
                    animatorList.add(getVtoVScaleAndTranslateAnimator(tiles[i], rectInit[i], rectLeft[position - i - 1]))
                    animatorList.add(getVtoVScaleAndTranslateAnimator(tilesText[i], rectTextInit[i], rectLeft[position - i - 1]))
                }
                i == position -> {
                    animatorList.add(getVtoVScaleAndTranslateAnimator(tiles[i], rectInit[i], rectCenter))
                    animatorList.add(getVtoVScaleAndTranslateAnimator(tilesText[i], rectTextInit[i], rectCenter))
                }
                i > position -> {
                    animatorList.add(getVtoVScaleAndTranslateAnimator(tiles[i], rectInit[i], rectRight[i - position - 1]))
                    animatorList.add(getVtoVScaleAndTranslateAnimator(tilesText[i], rectTextInit[i], rectRight[i - position - 1]))
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            animatorList.add(getNavBarColorAnimator(GLOBAL_DATA.imageDrawableRes[position]))

        animatorSet.playTogether(animatorList)
        animatorSet.interpolator = DecelerateInterpolator()
        animatorSet.addListener(this)
        return animatorSet
    }

    fun getCompleteExitAnimator(): Animator {
        val animator = getCompleteEnterAnimator(detailsFragment.headerViewPager.currentItem)
        animator.interpolator = ReverseInterpolator()

        val oAnimator = ObjectAnimator.ofFloat(detailsFragment.detailsViewPager, "translationY", 0f, (drawerLayout.height - 200.toPx()))
        oAnimator.duration = transitionAnimationDuration
        oAnimator.interpolator = DecelerateInterpolator()
        oAnimator.start()

        return animator
    }

    fun createRectArray() {
        val bottom = expandedAppBarHeight.toPx().toInt()
        var left = drawerLayout.width * -3

        for (i in 0..6) {
            when (i) {
                in 0..2 -> rectLeft[2 - i].set(left, 0, left + drawerLayout.width, bottom)
                3 -> rectCenter.set(left, 0, left + drawerLayout.width, bottom)
                in 4..6 -> rectRight[i - 4].set(left, 0, left + drawerLayout.width, bottom)
            }
            left += drawerLayout.width
        }

        for (i in 0..3) {
            tiles[i].getGlobalVisibleRect(rectInit[i])
            tilesText[i].getGlobalVisibleRect(rectTextInit[i])
        }
    }

    fun getVtoVScaleAndTranslateAnimator(viewToAnimate: View, fromViewRect: Rect, toViewRect: Rect): AnimatorSet {

        val animatorSet = AnimatorSet()

        if (viewToAnimate is TextView) {
            val translateXAnimator = ValueAnimator.ofFloat(0f, (toViewRect.left + toViewRect.right) / 2f - (fromViewRect.left + fromViewRect.right) / 2f)
            translateXAnimator.addUpdateListener { valueAnimator -> viewToAnimate.translationX = valueAnimator.animatedValue as Float }

            val translateYAnimator = ValueAnimator.ofFloat(0f, (toViewRect.top + toViewRect.bottom) / 2f - (fromViewRect.top + fromViewRect.bottom) / 2f)
            translateYAnimator.addUpdateListener { valueAnimator -> viewToAnimate.translationY = valueAnimator.animatedValue as Float }

            val textScaleAnimator = ValueAnimator.ofFloat(1f, 2f)
            textScaleAnimator.addUpdateListener { animator ->
                viewToAnimate.scaleX = animator.animatedValue as Float
                viewToAnimate.scaleY = animator.animatedValue as Float
            }
            animatorSet.playTogether(textScaleAnimator, translateXAnimator, translateYAnimator)
        } else {
            val translateXAnimator = ValueAnimator.ofFloat(0f, (toViewRect.left + toViewRect.right) / 2f - (fromViewRect.left + fromViewRect.right) / 2f)
            translateXAnimator.addUpdateListener { valueAnimator -> viewToAnimate.translationX = valueAnimator.animatedValue as Float }

            val translateYAnimator = ValueAnimator.ofFloat(0f, (toViewRect.top + toViewRect.bottom) / 2f - (fromViewRect.top + fromViewRect.bottom) / 2f)
            translateYAnimator.addUpdateListener { valueAnimator -> viewToAnimate.translationY = valueAnimator.animatedValue as Float }

            val scaleXAnimator = ValueAnimator.ofFloat(1f, toViewRect.width().div(fromViewRect.width().toFloat()))
            scaleXAnimator.addUpdateListener { valueAnimator -> viewToAnimate.scaleX = valueAnimator.animatedValue as Float }

            val scaleYAnimator = ValueAnimator.ofFloat(1f, toViewRect.height().div(fromViewRect.height().toFloat()))
            scaleYAnimator.addUpdateListener { valueAnimator -> viewToAnimate.scaleY = valueAnimator.animatedValue as Float }

            animatorSet.playTogether(scaleXAnimator, scaleYAnimator, translateXAnimator, translateYAnimator)
        }

        animatorSet.duration = transitionAnimationDuration
        return animatorSet
    }

    fun getNavBarColorAnimator(resImage: Int): Animator {
        val valueAnimator = ValueAnimator.ofArgb(window.navigationBarColor, getDominantColor(resImage))
        valueAnimator.addUpdateListener { vAnimator -> window.navigationBarColor = vAnimator.animatedValue as Int }
        valueAnimator.duration = transitionAnimationDuration
        return valueAnimator
    }

    fun clearAllProperties() {
        for (i in tiles) {
            i.translationX = 0f
            i.translationY = 0f
            i.scaleX = 1f
            i.scaleY = 1f
            i.visibility = View.INVISIBLE
        }

        for (i in tilesText) {
            i.scaleX = 1f
            i.scaleY = 1f
            i.translationX = 0f
            i.translationY = 0f
            i.visibility = View.INVISIBLE
        }

        headerCard.visibility = View.INVISIBLE
    }

    fun showAllViewsAgain() {
        for (i in tiles)
            i.visibility = View.VISIBLE

        for (i in tilesText)
            i.visibility = View.VISIBLE

        headerCard.visibility = View.VISIBLE
    }

    fun picasso(context: Context, resourceId: Int): RequestCreator {
        return Picasso.with(context)
                .load(resourceId)
                .fit()
    }

    fun Int.toPx() = this * displayMetrics.density

    fun Int.toDp() = (this / displayMetrics.density).toInt()

    fun getDominantColor(res: Int): Int {
        val bitmap = BitmapFactory.decodeResource(resources, res)
        val newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()
        return color
    }

    fun initNTB() {
        val colors = resources.getStringArray(R.array.vertical_ntb)

        val models: ArrayList<NavigationTabBar.Model> = ArrayList()
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.home),
                        Color.parseColor(colors[0]))
                        .title("ic_first")
                        .selectedIcon(ContextCompat.getDrawable(this, R.drawable.home))
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.subscribe),
                        Color.parseColor(colors[1]))
                        .selectedIcon(ContextCompat.getDrawable(this, R.drawable.subscribe))
                        .title("ic_second")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.login),
                        Color.parseColor(colors[2]))
                        .selectedIcon(ContextCompat.getDrawable(this, R.drawable.login))
                        .title("ic_third")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.register),
                        Color.parseColor(colors[3]))
                        .selectedIcon(ContextCompat.getDrawable(this, R.drawable.register))
                        .title("ic_fourth")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.maps),
                        Color.parseColor(colors[4]))
                        .selectedIcon(ContextCompat.getDrawable(this, R.drawable.maps))
                        .title("ic_fifth")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.contact),
                        Color.parseColor(colors[5]))
                        .selectedIcon(ContextCompat.getDrawable(this, R.drawable.contact))
                        .title("ic_sixth")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.sponsors),
                        Color.parseColor(colors[5]))
                        .selectedIcon(ContextCompat.getDrawable(this, R.drawable.sponsors))
                        .title("ic_seventh")
                        .build()
        )
        models.add(
                NavigationTabBar.Model.Builder(
                        ContextCompat.getDrawable(this, R.drawable.developers),
                        Color.parseColor(colors[6]))
                        .selectedIcon(ContextCompat.getDrawable(this, R.drawable.developers))
                        .title("ic_eighth")
                        .build()
        )

        ntb.models = models
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)

        } else if (isDetailsFragmentPresent) {

            if (detailsFragment.isBottomSheetExpanded()) {
                detailsFragment.hideBottomSheet()
            } else if (GLOBAL_DATA.textScale < 1.05f) {
                detailsFragment.expandAppBarLayout()
            } else {

                if (!isCurrentlyInTransition) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        window.navigationBarColor = Color.BLACK

                    isEntering = false
                    getCompleteExitAnimator().start()
                }
            }
        } else {
            super.onBackPressed()
        }
    }

}