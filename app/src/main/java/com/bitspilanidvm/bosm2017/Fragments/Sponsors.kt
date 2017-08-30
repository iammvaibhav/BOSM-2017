package com.bitspilanidvm.bosm2017.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bitspilanidvm.bosm2017.Adapters.SponsorViewPager
import com.bitspilanidvm.bosm2017.R
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager

class Sponsors : Fragment(){

    lateinit var horizontalViewPager: HorizontalInfiniteCycleViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sponsors, container, false)
        horizontalViewPager = view.findViewById(R.id.horizontalViewPager)

        horizontalViewPager.adapter = SponsorViewPager(activity)
        horizontalViewPager.scrollDuration = 500
        horizontalViewPager.isMediumScaled = true
        horizontalViewPager.maxPageScale = 0.8f
        horizontalViewPager.minPageScale = 0.5f
        horizontalViewPager.centerPageScaleOffset = 30f
        horizontalViewPager.minPageScaleOffset = 5f

        return view
    }
}