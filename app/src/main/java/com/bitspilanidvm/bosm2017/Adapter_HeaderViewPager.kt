package com.bitspilanidvm.bosm2017

import android.content.Context
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

class Adapter_HeaderViewPager(val context: Context) : PagerAdapter() {

    var pageMap = HashMap<Int, TextView>()
    var pages = HashMap<Int, View>()

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val itemView = LayoutInflater.from(context).inflate(R.layout.header_view_pager_page, container, false)
        val imageView = itemView.findViewById<ImageView>(R.id.headerPagerImageView)
        val textView = itemView.findViewById<TextView>(R.id.itemText)
        pageMap.put(position, textView)
        pages.put(position, itemView)
        //imageView.setImageResource(Data.imageDrawableRes[position])
        picasso(context, Data.imageDrawableRes[position]).into(imageView)
        textView.text = Data.headerTitles[position]
        textView.scaleX = Data.textScale
        textView.scaleY = Data.textScale
        textView.typeface = Typeface.createFromAsset(context.assets, "fonts/Ikaros-Regular.otf")
        textView.setShadowLayer(2f, 2f, 2f, ContextCompat.getColor(context, Data.shadowColors[position]))
        container?.addView(itemView)
        return itemView
    }
    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container?.removeView(`object` as FrameLayout)
        pageMap.remove(position)
    }

    override fun isViewFromObject(view: View?, `object`: Any?) = view == `object` as FrameLayout

    override fun getCount() = Data.headerTitles.size

    fun picasso(context: Context, resourceId: Int): RequestCreator {
        return Picasso.with(context)
                .load(resourceId)
                .fit()
    }
}
