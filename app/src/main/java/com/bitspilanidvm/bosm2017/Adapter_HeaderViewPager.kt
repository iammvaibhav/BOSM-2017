package com.bitspilanidvm.bosm2017

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

class Adapter_HeaderViewPager(val context: Context, val imageDrawableRes: Array<Int>, val titles: Array<String>) : PagerAdapter() {

    var pageMap = HashMap<Int, TextView>()
    var pages = HashMap<Int, View>()

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val itemView = LayoutInflater.from(context).inflate(R.layout.header_view_pager_page, container, false)
        val imageView = itemView.findViewById<ImageView>(R.id.headerPagerImageView)
        val textView = itemView.findViewById<TextView>(R.id.itemText)
        pageMap.put(position, textView)
        pages.put(position, itemView)
        imageView.setImageResource(imageDrawableRes[position])
        textView.text = titles[position]
        textView.textSize = Data.GLOBAL_DATA.textSize
        container?.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container?.removeView(`object` as FrameLayout)
        pageMap.remove(position)
    }

    override fun isViewFromObject(view: View?, `object`: Any?) = view == `object` as FrameLayout

    override fun getCount() = titles.size
}
