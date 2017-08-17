package com.bitspilanidvm.bosm2017

import android.animation.*
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.Toolbar
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text
import android.graphics.Bitmap
import android.graphics.BitmapFactory


class DetailsActivity : AppCompatActivity() {

    companion object PREVIOUS_STATE {
        var textSize = 40f
    }

    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var headerViewPager: CustomPager
    lateinit var appBarLayout: AppBarLayout
    lateinit var detailsViewPager: CustomPager
    lateinit var bottomSheetParent: CardView
    lateinit var bottomSheetBehaviour: BottomSheetBehavior<CardView>
    lateinit var bottomSheetImage: ImageView
    lateinit var bottomSheetCardView: CardView
    lateinit var bottomSheetHeading: TextView
    lateinit var bottomSheetTime: TextView
    lateinit var bottomSheetDetails: TextView

    val headerMaxTextSizeSP = 40
    val headerMinTextSizeSP = 28

    val headerDrawableRes = arrayOf(R.drawable.w, R.drawable.q,
            R.drawable.u, R.drawable.w)
    val headerTitles = arrayOf("EVENTS", "HIGHLIGHTS", "EVENTS NOW", "SPORTS SCHEDULE")

    val imageRes1 = arrayOf(android.R.color.holo_blue_light, android.R.color.holo_green_light,
            android.R.color.holo_purple, android.R.color.holo_orange_dark, android.R.color.holo_blue_light, android.R.color.holo_green_light,
            android.R.color.holo_purple, android.R.color.holo_orange_dark)
    val imageRes2 = arrayOf(android.R.color.holo_blue_light)
    val imageRes3 = arrayOf(android.R.color.holo_blue_light, android.R.color.holo_green_light,
            android.R.color.holo_purple)
    val imageRes4 = arrayOf(android.R.color.holo_blue_light, android.R.color.holo_green_light)

    val heading1 = arrayOf("dsfds", "fsdfsd", "fdsfd", "sfdsfsdf", "dsfds", "fsdfsd", "fdsfd", "sfdsfsdf")
    val heading2 = arrayOf("sfdsdf")
    val heading3 = arrayOf("dsfsd", "sdfsdfds", "sdfsdf")
    val heading4 = arrayOf("djfjdl", "sdfsdfsdf")

    val details1 = arrayOf("dgdfgdfgdfsfds", "fsdfdfgdfdsd", "fdfgdfgsfd", "sfdsfsdf", "dsfds", "fsdfsd", "fdsfd", "sfdsfsdf")
    val details2 = arrayOf("sfgdfgdfgdfgdsdf")
    val details3 = arrayOf("dsdfgdfgdffsd", "sdfsdfds", "sdffgdfgdsdf")
    val details4 = arrayOf("djfjdl", "sdfsdfdfgdfgdsdf")

    var viewHolderDetailTemp: ViewHolder_DetailItem? = null
    var cardX: Float = 0f
    var titleX: Float = 0f
    var detailsX: Float = 0f

    val listener1 = object : DetailsRecyclerViewClickListener {
        override fun onItemClick(itemHolder: ViewHolder_DetailItem, position: Int) {
            animateItemExit(itemHolder)
            bottomSheetImage.setImageResource(imageRes1[position])
            bottomSheetHeading.text = heading1[position]
            bottomSheetTime.text = details1[position]
        }
    }
    val listener2 = object : DetailsRecyclerViewClickListener {
        override fun onItemClick(itemHolder: ViewHolder_DetailItem, position: Int) {
            animateItemExit(itemHolder)
            bottomSheetImage.setImageResource(imageRes2[position])
            bottomSheetHeading.text = heading2[position]
            bottomSheetTime.text = details2[position]
        }
    }
    val listener3 = object : DetailsRecyclerViewClickListener {
        override fun onItemClick(itemHolder: ViewHolder_DetailItem, position: Int) {
            animateItemExit(itemHolder)
            bottomSheetImage.setImageResource(imageRes1[position])
            bottomSheetHeading.text = heading3[position]
            bottomSheetTime.text = details1[position]
        }
    }
    val listener4 = object : DetailsRecyclerViewClickListener {
        override fun onItemClick(itemHolder: ViewHolder_DetailItem, position: Int) {
            animateItemExit(itemHolder)
            bottomSheetImage.setImageResource(imageRes1[position])
            bottomSheetHeading.text = heading1[position]
            bottomSheetTime.text = details1[position]
        }
    }

    val adapterArray = arrayOf(Adapter_DetailsRecyclerView(imageRes1, heading1, details1, listener1),
            Adapter_DetailsRecyclerView(imageRes2, heading2, details2, listener2),
            Adapter_DetailsRecyclerView(imageRes3, heading3, details3, listener3),
            Adapter_DetailsRecyclerView(imageRes4, heading4, details4, listener4))

    val headerAdapter = Adapter_HeaderViewPager(this, headerDrawableRes, headerTitles, this)

    val argbEvaluator = ArgbEvaluator()
    val floatEvaluator = FloatEvaluator()

    var bottomSheetPrevState = BottomSheetBehavior.STATE_HIDDEN

    // onCreate Callback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // Making views draw behind status bar
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        // inflating views
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        headerViewPager = findViewById(R.id.headerViewPager)
        appBarLayout = findViewById(R.id.appBarLayout)
        detailsViewPager = findViewById(R.id.detailsViewPager)
        bottomSheetParent = findViewById(R.id.bottomSheetParent)
        bottomSheetImage = findViewById(R.id.imageView)
        bottomSheetCardView = findViewById(R.id.cardView)
        bottomSheetHeading = findViewById(R.id.heading)
        bottomSheetTime = findViewById(R.id.time)
        bottomSheetDetails = findViewById(R.id.details)

        // getting bottom sheet behaviour
        bottomSheetBehaviour = BottomSheetBehavior.from(bottomSheetParent)
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_HIDDEN

        // synchronizing header and details view pager
        headerViewPager.setViewPager(detailsViewPager)
        detailsViewPager.setViewPager(headerViewPager)

        // setting adapters
        headerViewPager.adapter = headerAdapter
        detailsViewPager.adapter = Adapter_DetailsViewPager(this, adapterArray)

        // setting up page transformer for header view pager
        headerViewPager.setPageTransformer(true, Transformer_HeaderPage())

        headerViewPager.currentItem = intent.extras["page"] as Int

        // Navigation bar color array
        val navColorArray = arrayOf(getDominantColor(R.drawable.w),
                getDominantColor(R.drawable.q),
                getDominantColor(R.drawable.u),
                getDominantColor(R.drawable.w))

        // If sdk is greater than lollipop then synchronize nav bar color with header view pager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            headerViewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                    if (p0 < headerAdapter.count - 1 && p0 < navColorArray.size - 1) {
                        window.navigationBarColor = argbEvaluator.evaluate(p1, navColorArray[p0], navColorArray[p0 + 1]) as Int
                    } else {
                        window.navigationBarColor = navColorArray[navColorArray.size - 1]
                    }
                }
            })
        }

        // Header offset listener
        appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
            PREVIOUS_STATE.textSize = (headerMaxTextSizeSP - ((headerMaxTextSizeSP - headerMinTextSizeSP).toFloat() / 220) *
                    (verticalOffset * -1).toDp().toFloat())
            headerAdapter.pageMap[headerViewPager.currentItem]?.textSize = PREVIOUS_STATE.textSize
        }

        // Bottom Sheet offset listener
        bottomSheetBehaviour.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

                bottomSheetParent.alpha = 1 + slideOffset

                if (bottomSheetPrevState == BottomSheetBehavior.STATE_EXPANDED) {
                    viewHolderDetailTemp?.cardView?.translationX = floatEvaluator.evaluate(slideOffset * -1, cardX, 0f)
                    viewHolderDetailTemp?.titleTextView?.translationX = floatEvaluator.evaluate(slideOffset * -1, titleX, 0f)
                    viewHolderDetailTemp?.detailsTextView?.translationX = floatEvaluator.evaluate(slideOffset * -1, detailsX, 0f)
                }
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {

                    bottomSheetPrevState = BottomSheetBehavior.STATE_HIDDEN

                } else if (newState == BottomSheetBehavior.STATE_EXPANDED) {

                    bottomSheetPrevState = BottomSheetBehavior.STATE_EXPANDED

                    if (cardX == 0f) {
                        cardX = viewHolderDetailTemp?.cardView?.translationX ?: 0f
                        titleX = viewHolderDetailTemp?.titleTextView?.translationX ?: 0f
                        detailsX = viewHolderDetailTemp?.detailsTextView?.translationX ?: 0f
                    }
                }
            }
        })
    }

    // If sheet is open, close that sheet
    override fun onBackPressed() {
        if (bottomSheetBehaviour.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehaviour.state = BottomSheetBehavior.STATE_HIDDEN
        } else {
            super.onBackPressed()
        }
    }

    // get dominant color from header view pager image for the navigation bar color
    fun getDominantColor(res: Int): Int {
        val bitmap = BitmapFactory.decodeResource(resources, res)
        val newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()
        return color
    }

    // Animate item while opening a sheet
    fun animateItemExit(itemHolder: ViewHolder_DetailItem) {

        viewHolderDetailTemp = itemHolder

        val cardViewAnimator = ObjectAnimator.ofFloat(itemHolder.cardView, "translationX", 0f, -(itemHolder.cardView.left + itemHolder.cardView.width).toFloat())
        val headingAnimator = ObjectAnimator.ofFloat(itemHolder.titleTextView, "translationX", 0f, (itemHolder.itemView.width - itemHolder.titleTextView.left).toFloat())
        val detailsAnimator = ObjectAnimator.ofFloat(itemHolder.detailsTextView, "translationX", 0f, (itemHolder.itemView.width - itemHolder.detailsTextView.left).toFloat())

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(cardViewAnimator, headingAnimator, detailsAnimator)
        animatorSet.duration = 200
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                bottomSheetBehaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationStart(p0: Animator?) {}
        })
        animatorSet.start()
    }

    //Extension method
    fun Int.toDp(): Int {
        var displayMetrics = DisplayMetrics()
        this@DetailsActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return (this / displayMetrics.density).toInt()
    }
}
