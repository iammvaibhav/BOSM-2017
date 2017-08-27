package com.bitspilanidvm.bosm2017.Fragments

import android.animation.*
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bitspilanidvm.bosm2017.*
import com.bitspilanidvm.bosm2017.Adapters.*
import com.bitspilanidvm.bosm2017.Adapters.EventItem
import com.bitspilanidvm.bosm2017.ClickListeners.DetailsRecyclerViewClickListener
import com.bitspilanidvm.bosm2017.Custom.CustomPager
import com.bitspilanidvm.bosm2017.Custom.Transformer_HeaderPage
import com.bitspilanidvm.bosm2017.Universal.GLOBAL_DATA
import com.bitspilanidvm.bosm2017.Universal.convertListToNonFixtureSportsDecoupledList
import com.bitspilanidvm.bosm2017.Universal.getWinnerListFromFixtureSportsDataList
import com.bitspilanidvm.bosm2017.Universal.getWinnerListFromNonFixtureSportsDataDecoupledList
import com.bitspilanidvm.bosm2017.ViewHolder.DetailedItem

class Details : Fragment(){

    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var headerViewPager: CustomPager
    lateinit var appBarLayout: AppBarLayout
    lateinit var detailsViewPager: CustomPager
    lateinit var bottomSheetParent: CardView
    lateinit var bottomSheetBehaviour: BottomSheetBehavior<CardView>
    lateinit var bottomSheetRecyclerView: RecyclerView

    var viewHolderDetailTemp: DetailedItem? = null
    var cardX: Float = 0f
    var titleX: Float = 0f
    var detailsX: Float = 0f

    val listener1 = object : DetailsRecyclerViewClickListener {
        override fun onItemClick(itemHolder: DetailedItem, position: Int) {
            animateItemExit(itemHolder)
            bottomSheetRecyclerView.adapter = EventItem(com.bitspilanidvm.bosm2017.Universal.EventItem(
                    GLOBAL_DATA.imageRes1[0],
                    GLOBAL_DATA.heading1[position],
                    "10:00 am",
                    GLOBAL_DATA.details1[position]
            ))
        }
    }
    val listener2 = object : DetailsRecyclerViewClickListener {
        override fun onItemClick(itemHolder: DetailedItem, position: Int) {
            animateItemExit(itemHolder)
            bottomSheetRecyclerView.adapter = EventItem(com.bitspilanidvm.bosm2017.Universal.EventItem(
                    GLOBAL_DATA.imageRes2[0],
                    GLOBAL_DATA.heading2[position],
                    "10:00 am",
                    GLOBAL_DATA.details2[position]
            ))
        }
    }
    val listener3 = object : DetailsRecyclerViewClickListener {
        override fun onItemClick(itemHolder: DetailedItem, position: Int) {
            Log.e("clicked", position.toString())
            var sportNo = 0
            for ((k, v) in GLOBAL_DATA.sportsMap)
                if (itemHolder.titleTextView.text == v){
                    sportNo = k
                    break
                }
            if (sportNo in GLOBAL_DATA.fixtures)
                bottomSheetRecyclerView.adapter = ScheduleFixture(GLOBAL_DATA.sports.fixtureSportsDataList[sportNo])
            else
                bottomSheetRecyclerView.adapter = ScheduleNonFixture(convertListToNonFixtureSportsDecoupledList(GLOBAL_DATA.sports.nonFixtureSportsDataList[sportNo]))

            bottomSheetRecyclerView.adapter.notifyDataSetChanged()
            animateItemExit(itemHolder)
        }
    }
    val listener4 = object : DetailsRecyclerViewClickListener {
        override fun onItemClick(itemHolder: DetailedItem, position: Int) {
            var sportNo = 0
            for ((k, v) in GLOBAL_DATA.sportsMap)
                if (itemHolder.titleTextView.text == v){
                    sportNo = k
                    break
                }
            if (sportNo in GLOBAL_DATA.fixtures)
                bottomSheetRecyclerView.adapter = ResultsFixture(getWinnerListFromFixtureSportsDataList(GLOBAL_DATA.sports.fixtureSportsDataList[sportNo]))
            else
                bottomSheetRecyclerView.adapter = ResultsNonFixture(getWinnerListFromNonFixtureSportsDataDecoupledList(convertListToNonFixtureSportsDecoupledList(GLOBAL_DATA.sports.nonFixtureSportsDataList[sportNo])))
            bottomSheetRecyclerView.adapter.notifyDataSetChanged()
            animateItemExit(itemHolder)
        }
    }

    lateinit var header: HeaderViewPager

    val argbEvaluator = ArgbEvaluator()
    val floatEvaluator = FloatEvaluator()

    var bottomSheetPrevState = BottomSheetBehavior.STATE_HIDDEN

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context != null)
            header = HeaderViewPager(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)

        // inflating views
        coordinatorLayout = view.findViewById(R.id.coordinatorLayout)
        headerViewPager = view.findViewById(R.id.headerViewPager)
        appBarLayout = view.findViewById(R.id.appBarLayout)
        detailsViewPager = view.findViewById(R.id.detailsViewPager)
        bottomSheetParent = view.findViewById(R.id.bottomSheetParent)
        bottomSheetRecyclerView = view.findViewById(R.id.bottomSheetRecylerView)


        // getting bottom sheet behaviour
        bottomSheetBehaviour = BottomSheetBehavior.from(bottomSheetParent)
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_HIDDEN

        // synchronizing header and details view pager
        headerViewPager.setViewPager(detailsViewPager)
        detailsViewPager.setViewPager(headerViewPager)

        // setting adapters
        val headingsSchedule = Array(GLOBAL_DATA.availableSchedule.size){ _ -> ""}
        val resultsSchedule = Array(GLOBAL_DATA.availableResults.size){ _ -> ""}

        var index = 0
        for (i in GLOBAL_DATA.availableSchedule) {
            headingsSchedule[index] = GLOBAL_DATA.sportsMap[i] ?: "Dummy"
            index++
        }

        index = 0
        for (i in GLOBAL_DATA.availableResults) {
            resultsSchedule[index] = GLOBAL_DATA.sportsMap[i] ?: "Dummy"
            index++
        }

        headerViewPager.adapter = header
        detailsViewPager.adapter = DetailsViewPager(context,
                arrayOf(DetailsRecyclerView(GLOBAL_DATA.imageRes1, GLOBAL_DATA.heading1, GLOBAL_DATA.details1, listener1),
                        DetailsRecyclerView(GLOBAL_DATA.imageRes2, GLOBAL_DATA.heading2, GLOBAL_DATA.details2, listener2),
                        DetailsRecyclerView(GLOBAL_DATA.imageRes3, headingsSchedule, GLOBAL_DATA.details3, listener3),
                        DetailsRecyclerView(GLOBAL_DATA.imageRes4, resultsSchedule, GLOBAL_DATA.details4, listener4)))

        // setting up page transformer for header view pager
        headerViewPager.setPageTransformer(true, Transformer_HeaderPage())

        // Setting bottom Sheet Recycler View
        bottomSheetRecyclerView.layoutManager = LinearLayoutManager(context)

        // Navigation bar color array
        val navColorArray = arrayOf(getDominantColor(R.drawable.events),
                getDominantColor(R.drawable.ongoing),
                getDominantColor(R.drawable.schedule),
                getDominantColor(R.drawable.results))

        // If sdk is greater than lollipop then synchronize nav bar color with header view pager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            headerViewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                    if (p0 < header.count - 1 && p0 < navColorArray.size - 1) {
                        activity.window.navigationBarColor = argbEvaluator.evaluate(p1, navColorArray[p0], navColorArray[p0 + 1]) as Int
                    } else {
                        activity.window.navigationBarColor = navColorArray[navColorArray.size - 1]
                    }
                }
            })
        }

        // Header offset listener
        appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->

            GLOBAL_DATA.textScale = floatEvaluator.evaluate(verticalOffset/appBarLayout.totalScrollRange.toFloat() * -1, 2f, 1f)
            header.pageMap[headerViewPager.currentItem]?.scaleX = GLOBAL_DATA.textScale
            header.pageMap[headerViewPager.currentItem]?.scaleY = GLOBAL_DATA.textScale
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

        return view
    }

    override fun onResume() {
        super.onResume()
        //headerViewPager.currentItem = arguments["page"] as Int
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
    fun animateItemExit(itemHolder: DetailedItem) {

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

    fun isBottomSheetExpanded() = bottomSheetBehaviour.state == BottomSheetBehavior.STATE_EXPANDED

    fun hideBottomSheet(){
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_HIDDEN
    }

    fun expandAppBarLayout() {
        appBarLayout.setExpanded(true, true)
    }



    //Extension method
    fun Int.toDp(): Int {
        var displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return (this / displayMetrics.density).toInt()
    }

    fun Int.toPx(): Int {
        var displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return (this * displayMetrics.density).toInt()
    }

}