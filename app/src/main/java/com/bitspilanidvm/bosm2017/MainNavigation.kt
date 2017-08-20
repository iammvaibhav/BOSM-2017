package com.bitspilanidvm.bosm2017

import android.animation.*
import android.graphics.*
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
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import devlight.io.library.ntb.NavigationTabBar

class MainNavigation : AppCompatActivity() {

    lateinit var toolBar: Toolbar
    lateinit var mainNavRecyclerView: RecyclerView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationTabBar: NavigationTabBar
    lateinit var drawerToggle: ActionBarDrawerToggle
    lateinit var rootLayout: FrameLayout
    lateinit var linearLayoutRoot: LinearLayout

    lateinit var imageViewCenter: ImageView
    lateinit var textViewCenter: TextView
    lateinit var imageViewsRight: Array<ImageView>
    lateinit var imageViewsLeft: Array<ImageView>
    lateinit var textViewsRight: Array<TextView>
    lateinit var textViewsLeft: Array<TextView>

    private val floatEvaluator = FloatEvaluator()
    private val displayMetrics = DisplayMetrics()
    val detailsFragment = DetailsFragment()
    val transitionAnimationDuration = 1000L

    var isCurrentlyInTransition = false
    var isDetailsFragmentPresent = false
    var startPos = 0
    var endPos = 0


    private val clickListener = object : MainRecyclerViewClickListener {
        override fun onItemClick(itemHolder: ViewHolder_MainItem, position: Int) {

            detailsFragment.headerViewPager.currentItem = position
            detailsFragment.headerViewPager.visibility = View.INVISIBLE

            val animatorRV2VP = getRecyclerViewToViewPagerAnimator(position)
            makeAllVisibleViewsOfRecylerViewInvisible()

            val oAnimator = ObjectAnimator.ofFloat(detailsFragment.detailsViewPager, "translationY", (rootLayout.height - 300.toPx()), 0f)
            oAnimator.duration = transitionAnimationDuration
            oAnimator.interpolator = DecelerateInterpolator()


            animatorRV2VP?.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {}
                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationStart(p0: Animator?) {
                    isCurrentlyInTransition = true
                    supportFragmentManager.beginTransaction().show(detailsFragment).commit()
                    isDetailsFragmentPresent = true
                    oAnimator.start()
                }

                override fun onAnimationEnd(p0: Animator?) {

                    isCurrentlyInTransition = false
                    detailsFragment.headerViewPager.visibility = View.VISIBLE
                    detailsFragment.detailsViewPager.translationY = rootLayout.height - 320.toPx()
                    clearPropertiesOnImageAndTextViews()
                }
            })

            animatorRV2VP?.start()
        }
    }

    private val navigationTabListener = object : NavigationTabBar.OnTabBarSelectedIndexListener {
        override fun onEndTabSelected(model: NavigationTabBar.Model?, index: Int) {
            if (index in 0..3) {
                mainNavRecyclerView.smoothScrollToPosition(index + 4)
            }
        }

        override fun onStartTabSelected(model: NavigationTabBar.Model?, index: Int) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation)

        // If greater than lollipop, draw behind status bar for transitions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        // Inflating views
        mainNavRecyclerView = findViewById(R.id.mainNavRecyclerView)
        toolBar = findViewById(R.id.toolBar)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationTabBar = findViewById(R.id.ntb_vertical)
        rootLayout = findViewById(R.id.rootLayout)
        linearLayoutRoot = findViewById(R.id.linearLayoutRoot)

        imageViewsLeft = Array(5) { _ -> ImageView(this) }
        textViewsLeft = Array(5) { _ -> TextView(this) }
        imageViewsRight = Array(5) { _ -> ImageView(this) }
        textViewsRight = Array(5) { _ -> TextView(this) }
        imageViewCenter = ImageView(this)
        textViewCenter = TextView(this)

        // Setting window background, retrieving metrics
        window.setBackgroundDrawableResource(R.drawable.r)
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        // Setting up toolbar as action bar
        setSupportActionBar(toolBar)
        supportActionBar?.title = ""


        // Recycler View setup
        val layoutManager = LinearLayoutManager(this)
        val recyclerAdapter = Adapter_MainRecyclerView(getMainNavData(), clickListener)
        val recyclerAnimatorAdapter = CustomAnimator(recyclerAdapter)

        with(mainNavRecyclerView) {
            setLayoutManager(layoutManager)
            adapter = recyclerAnimatorAdapter
            setHasFixedSize(true)
            setItemViewCacheSize(20)
            isDrawingCacheEnabled = true
            drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        }

        // After root FrameLayout has been measured place these views on top of that
        rootLayout.post {
            supportFragmentManager.beginTransaction().add(R.id.rootLayout, detailsFragment).hide(detailsFragment).commit()
            prepareAndPlaceImageAndTextViews() }

        drawerToggle =
                object : ActionBarDrawerToggle(this,
                        drawerLayout,
                        toolBar,
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close) {

                    override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {
                        super.onDrawerSlide(drawerView, slideOffset)
                        mainNavRecyclerView.translationX = floatEvaluator.evaluate(slideOffset, 0f, 20.toPx())

                        for (i in layoutManager.findFirstVisibleItemPosition()..layoutManager.findLastVisibleItemPosition()) {
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

    fun prepareImageViewAndTextView(imageView: ImageView, textView: TextView) {
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP

        textView.elevation = 10.toDp().toFloat()
        textView.setShadowLayer(4f, 1f, 1f, Color.BLACK)
        textView.setTextColor(Color.WHITE)
        textView.letterSpacing = 0.3f
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40f)
        textView.gravity = Gravity.CENTER
        textView.setTypeface(null, Typeface.BOLD)
        textView.setSingleLine(true)
    }

    fun addToLayout(imageView: ImageView, textView: TextView, gravity: Int, position: Int) {
        val layoutParams = FrameLayout.LayoutParams(rootLayout.width, 300.toPx().toInt())

        if (gravity != 0)
            layoutParams.gravity = gravity

        Log.e("gravity start", (position * rootLayout.width).toString())
        when (gravity) {
            Gravity.START -> layoutParams.leftMargin = position * rootLayout.width
            Gravity.END -> layoutParams.rightMargin = position * rootLayout.width
        }

        rootLayout.addView(imageView, layoutParams)
        rootLayout.addView(textView, layoutParams)
    }

    // May be of use?
    fun getViewToViewScalingAnimator(parentView: ViewGroup, viewToAnimate: View, fromViewRect: Rect, toViewRect: Rect, duration: Long, startDelay: Long): AnimatorSet {
        // get all coordinates at once
        val parentViewRect = Rect()
        val viewToAnimateRect = Rect()
        parentView.getGlobalVisibleRect(parentViewRect)
        viewToAnimate.getGlobalVisibleRect(viewToAnimateRect)

        viewToAnimate.scaleX = 1f
        viewToAnimate.scaleY = 1f

        // rescaling of the object on X-axis
        val valueAnimatorWidth = ValueAnimator.ofInt(fromViewRect.width(), toViewRect.width())
        valueAnimatorWidth.addUpdateListener {
            // Get animated width value update
            val newWidth = valueAnimatorWidth.animatedValue as Int

            // Get and update LayoutParams of the animated view
            val lp = viewToAnimate.layoutParams as ViewGroup.LayoutParams

            lp.width = newWidth
            viewToAnimate.layoutParams = lp
        }

        // rescaling of the object on Y-axis
        val valueAnimatorHeight = ValueAnimator.ofInt(fromViewRect.height(), toViewRect.height())
        valueAnimatorHeight.addUpdateListener {
            // Get animated width value update
            val newHeight = valueAnimatorHeight.animatedValue as Int

            // Get and update LayoutParams of the animated view
            val lp = viewToAnimate.layoutParams as ViewGroup.LayoutParams
            lp.height = newHeight
            viewToAnimate.layoutParams = lp
        }

        // moving of the object on X-axis
        val translateAnimatorX = ObjectAnimator.ofFloat(viewToAnimate, "X", (fromViewRect.left - parentViewRect.left).toFloat(), (toViewRect.left - parentViewRect.left).toFloat())

        // moving of the object on Y-axis
        val translateAnimatorY = ObjectAnimator.ofFloat(viewToAnimate, "Y", (fromViewRect.top - parentViewRect.top).toFloat(), (toViewRect.top - parentViewRect.top).toFloat())

        val animatorSet = AnimatorSet()
        animatorSet.interpolator = DecelerateInterpolator(1f)
        animatorSet.duration = duration // can be decoupled for each animator separately
        animatorSet.startDelay = startDelay // can be decoupled for each animator separately
        animatorSet.playTogether(valueAnimatorWidth, valueAnimatorHeight, translateAnimatorX, translateAnimatorY)

        return animatorSet
    }

    fun getVtoVScaleAndTranslateAnimator(viewToAnimate: View, fromViewRect: Rect, toViewRect: Rect, duration: Long): AnimatorSet {

        val translateXAnimator = ValueAnimator.ofFloat((fromViewRect.left + fromViewRect.right) / 2f - (toViewRect.left + toViewRect.right) / 2f, 0f)
        translateXAnimator.addUpdateListener { valueAnimator -> viewToAnimate.translationX = valueAnimator.animatedValue as Float }

        val translateYAnimator = ValueAnimator.ofFloat((fromViewRect.top + fromViewRect.bottom) / 2f - (toViewRect.top + toViewRect.bottom) / 2f, 0f)
        translateYAnimator.addUpdateListener { valueAnimator -> viewToAnimate.translationY = valueAnimator.animatedValue as Float }

        val animatorSet = AnimatorSet()

        if (viewToAnimate is TextView) {
            val textSizeAnimator = ValueAnimator.ofFloat(28f, 40f)
            textSizeAnimator.addUpdateListener { animator -> viewToAnimate.setTextSize(TypedValue.COMPLEX_UNIT_SP, animator.animatedValue as Float) }
            animatorSet.playTogether(textSizeAnimator, translateXAnimator, translateYAnimator)
        } else {
            val scaleXAnimator = ValueAnimator.ofFloat(fromViewRect.width().div(toViewRect.width().toFloat()), 1f)
            scaleXAnimator.addUpdateListener { valueAnimator -> viewToAnimate.scaleX = valueAnimator.animatedValue as Float }

            val scaleYAnimator = ValueAnimator.ofFloat(fromViewRect.height().div(toViewRect.height().toFloat()), 1f)
            scaleYAnimator.addUpdateListener { valueAnimator -> viewToAnimate.scaleY = valueAnimator.animatedValue as Float }

            animatorSet.playTogether(scaleXAnimator, scaleYAnimator, translateXAnimator, translateYAnimator)
        }

        animatorSet.duration = duration

        return animatorSet
    }

    fun getNavBarColorAnimator(resImage: Int): Animator {
        val valueAnimator = ValueAnimator.ofArgb(window.navigationBarColor, getDominantColor(resImage))
        valueAnimator.addUpdateListener { valueAnimator -> window.navigationBarColor = valueAnimator.animatedValue as Int }
        valueAnimator.duration = transitionAnimationDuration
        return valueAnimator
    }

    fun prepareAndPlaceImageAndTextViews() {

        for (i in 5 downTo 1) {
            prepareImageViewAndTextView(imageViewsLeft[i - 1], textViewsLeft[i - 1])
            addToLayout(imageViewsLeft[i - 1], textViewsLeft[i - 1], Gravity.END, i)
        }

        prepareImageViewAndTextView(imageViewCenter, textViewCenter)
        addToLayout(imageViewCenter, textViewCenter, 0, 0)

        for (i in 1..5) {
            prepareImageViewAndTextView(imageViewsRight[i - 1], textViewsRight[i - 1])
            addToLayout(imageViewsRight[i - 1], textViewsRight[i - 1], Gravity.START, i)
        }
    }

    fun clearPropertiesOnImageAndTextViews() {
        for (i in imageViewsRight) {
            clearPropertiesOnImageView(i)
        }

        for (i in imageViewsLeft) {
            clearPropertiesOnImageView(i)
        }

        for (i in textViewsRight) {
            clearPropertiesOnTextView(i)
        }
        for (i in textViewsLeft) {
            clearPropertiesOnTextView(i)
        }
        clearPropertiesOnImageView(imageViewCenter)
        clearPropertiesOnTextView(textViewCenter)
    }

    fun clearPropertiesOnImageView(imageView: ImageView) {
        imageView.scaleX = 1f
        imageView.scaleY = 1f
        imageView.translationX = 0f
        imageView.translationY = 0f
        imageView.setImageResource(android.R.color.transparent)
    }

    fun clearPropertiesOnTextView(textView: TextView) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28f)
        textView.translationX = 0f
        textView.translationY = 0f
        textView.text = ""
    }

    fun getRecyclerViewToViewPagerAnimator(position: Int): Animator?{
        if (!isCurrentlyInTransition) {

            val animatorSet = AnimatorSet()
            val animatorList = ArrayList<Animator>()

            val llManager = mainNavRecyclerView.layoutManager as LinearLayoutManager
            startPos = llManager.findFirstVisibleItemPosition()
            endPos = llManager.findLastVisibleItemPosition()

            // declaring rectangles
            val rectFrom = Rect()
            val rectTo = Rect()
            val textFrom = Rect()
            val textTo = Rect()

            (mainNavRecyclerView.findViewHolderForLayoutPosition(position) as ViewHolder_MainItem).itemImage.getGlobalVisibleRect(rectFrom)
            (mainNavRecyclerView.findViewHolderForLayoutPosition(position) as ViewHolder_MainItem).itemText.getGlobalVisibleRect(textFrom)
            imageViewCenter.getGlobalVisibleRect(rectTo)
            textViewCenter.getGlobalVisibleRect(textTo)

            imageViewCenter.setImageResource(getMainNavData()[position].second)
            textViewCenter.text = getMainNavData()[position].first
            animatorList.add(getVtoVScaleAndTranslateAnimator(imageViewCenter, rectFrom, rectTo, transitionAnimationDuration))
            animatorList.add(getVtoVScaleAndTranslateAnimator(textViewCenter, textFrom, textTo, transitionAnimationDuration))
            animatorList.add(getNavBarColorAnimator(getMainNavData()[position].second))

            for (i in (position + 1)..endPos) {
                (mainNavRecyclerView.findViewHolderForLayoutPosition(i) as ViewHolder_MainItem).itemImage.getGlobalVisibleRect(rectFrom)
                (mainNavRecyclerView.findViewHolderForLayoutPosition(i) as ViewHolder_MainItem).itemText.getGlobalVisibleRect(textFrom)
                imageViewsRight[i - position - 1].getGlobalVisibleRect(rectTo)
                textViewsRight[i - position - 1].getGlobalVisibleRect(textTo)

                imageViewsRight[i - position - 1].setImageResource(getMainNavData()[i].second)
                textViewsRight[i - position - 1].text = getMainNavData()[i].first
                animatorList.add(getVtoVScaleAndTranslateAnimator(imageViewsRight[i - position - 1], rectFrom, rectTo, transitionAnimationDuration))
                animatorList.add(getVtoVScaleAndTranslateAnimator(textViewsRight[i - position - 1], textFrom, textTo, transitionAnimationDuration))
            }

            for (i in (position - 1) downTo startPos) {
                (mainNavRecyclerView.findViewHolderForLayoutPosition(i) as ViewHolder_MainItem).itemImage.getGlobalVisibleRect(rectFrom)
                (mainNavRecyclerView.findViewHolderForLayoutPosition(i) as ViewHolder_MainItem).itemText.getGlobalVisibleRect(textFrom)

                imageViewsLeft[position - 1 - i].getGlobalVisibleRect(rectTo)
                textViewsLeft[position - 1 - i].getGlobalVisibleRect(textTo)

                imageViewsLeft[position - 1 - i].setImageResource(getMainNavData()[i].second)
                textViewsLeft[position - 1 - i].text = getMainNavData()[i].first
                animatorList.add(getVtoVScaleAndTranslateAnimator(imageViewsLeft[position - 1 - i], rectFrom, rectTo, transitionAnimationDuration))
                animatorList.add(getVtoVScaleAndTranslateAnimator(textViewsLeft[position - 1 - i], textFrom, textTo, transitionAnimationDuration))
            }

            animatorSet.playTogether(animatorList)
            animatorSet.interpolator = DecelerateInterpolator()
            return animatorSet
        }else
            return null
    }

    fun makeAllVisibleViewsOfRecylerViewInvisible(){
        for (i in startPos..endPos)
            (mainNavRecyclerView.findViewHolderForLayoutPosition(i) as ViewHolder_MainItem).itemView.visibility = View.INVISIBLE
    }

    fun Int.toPx() = this * displayMetrics.density

    fun Int.toDp() = (this / displayMetrics.density).toInt()

    fun initNTB() {
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



    // get dominant color from header view pager image for the navigation bar color
    fun getDominantColor(res: Int): Int {
        val bitmap = BitmapFactory.decodeResource(resources, res)
        val newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()
        return color
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else if (isDetailsFragmentPresent){
            if (detailsFragment.isBottomSheetExpanded()){
                detailsFragment.hideBottomSheet()
            }else if (Data.GLOBAL_DATA.textSize < 30){
                detailsFragment.expandAppBarLayout()
            }else{

                window.navigationBarColor = Color.BLACK
                val revAnimator = getRecyclerViewToViewPagerAnimator(detailsFragment.headerViewPager.currentItem)
                revAnimator?.interpolator = ReverseInterpolator()

                val oAnimator = ObjectAnimator.ofFloat(detailsFragment.detailsViewPager, "translationY", 0f, (rootLayout.height - 300.toPx()))
                oAnimator.duration = transitionAnimationDuration
                oAnimator.interpolator = DecelerateInterpolator()

                revAnimator?.addListener(object : Animator.AnimatorListener{
                    override fun onAnimationRepeat(p0: Animator?) { }

                    override fun onAnimationEnd(p0: Animator?) {

                        supportFragmentManager.beginTransaction().hide(detailsFragment).commit()
                        isDetailsFragmentPresent = false

                        clearPropertiesOnImageAndTextViews()
                        for (i in startPos..endPos){
                            if (mainNavRecyclerView.findViewHolderForLayoutPosition(i) != null)
                                (mainNavRecyclerView.findViewHolderForLayoutPosition(i) as ViewHolder_MainItem).itemView.visibility = View.VISIBLE
                        }
                    }

                    override fun onAnimationCancel(p0: Animator?) { }

                    override fun onAnimationStart(p0: Animator?) {
                        detailsFragment.headerViewPager.visibility = View.INVISIBLE
                        oAnimator.start()
                    }
                })
                revAnimator?.start()
            }
        }else{
            super.onBackPressed()
        }
    }
}

fun getMainNavData(): ArrayList<Pair<String, Int>> {

    var dataItems = ArrayList<Pair<String, Int>>()

    dataItems.add(Pair("EVENTS", R.drawable.w))
    dataItems.add(Pair("HIGH", R.drawable.q))
    dataItems.add(Pair("YYT", R.drawable.u))
    dataItems.add(Pair("SPORTS", R.drawable.w))
    dataItems.add(Pair("SAAK", R.drawable.w))
    dataItems.add(Pair("YAYY", R.drawable.q))
    dataItems.add(Pair("BOSM", R.drawable.u))
    dataItems.add(Pair("OKIE", R.drawable.w))

    return dataItems
}
