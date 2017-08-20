package com.bitspilanidvm.bosm2017

import android.animation.*
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.CardView
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.details_fragment.*
import kotlinx.android.synthetic.main.recycler_view_view_pager_page.*

class DetailsFragment : Fragment(){

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

    var isCollapsed = false

    val headerDrawableRes = arrayOf(R.drawable.w, R.drawable.q,
            R.drawable.u, R.drawable.w)
    val headerTitles = arrayOf("EVENTS", "HIGH", "YYT", "SPORTS")

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

    lateinit var headerAdapter: Adapter_HeaderViewPager

    val argbEvaluator = ArgbEvaluator()
    val floatEvaluator = FloatEvaluator()

    var bottomSheetPrevState = BottomSheetBehavior.STATE_HIDDEN

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context != null)
            headerAdapter = Adapter_HeaderViewPager(context, headerDrawableRes, headerTitles)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.details_fragment, container, false)

        // inflating views
        coordinatorLayout = view!!.findViewById(R.id.coordinatorLayout)
        headerViewPager = view!!.findViewById(R.id.headerViewPager)
        appBarLayout = view!!.findViewById(R.id.appBarLayout)
        detailsViewPager = view!!.findViewById(R.id.detailsViewPager)
        bottomSheetParent = view!!.findViewById(R.id.bottomSheetParent)
        bottomSheetImage = view!!.findViewById(R.id.imageView)
        bottomSheetCardView = view!!.findViewById(R.id.cardView)
        bottomSheetHeading = view!!.findViewById(R.id.heading)
        bottomSheetTime = view!!.findViewById(R.id.time)
        bottomSheetDetails = view!!.findViewById(R.id.details)


        // getting bottom sheet behaviour
        bottomSheetBehaviour = BottomSheetBehavior.from(bottomSheetParent)
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_HIDDEN

        // synchronizing header and details view pager
        headerViewPager.setViewPager(detailsViewPager)
        detailsViewPager.setViewPager(headerViewPager)

        // setting adapters
        headerViewPager.adapter = headerAdapter
        detailsViewPager.adapter = Adapter_DetailsViewPager(activity, adapterArray)

        // setting up page transformer for header view pager
        headerViewPager.setPageTransformer(true, Transformer_HeaderPage())

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
                        activity.window.navigationBarColor = argbEvaluator.evaluate(p1, navColorArray[p0], navColorArray[p0 + 1]) as Int
                    } else {
                        activity.window.navigationBarColor = navColorArray[navColorArray.size - 1]
                    }
                }
            })
        }

        // Header offset listener
        appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
            Data.GLOBAL_DATA.textSize = (headerMaxTextSizeSP - ((headerMaxTextSizeSP - headerMinTextSizeSP).toFloat() / 220) *
                    (verticalOffset * -1).toDp().toFloat())
            headerAdapter.pageMap[headerViewPager.currentItem]?.textSize = Data.GLOBAL_DATA.textSize
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
