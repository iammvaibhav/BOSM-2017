package com.bitspilanidvm.bosm2017

import android.view.View

interface DetailsRecyclerViewClickListener {
    fun onItemClick(itemHolder : ViewHolder_DetailItem, position: Int)
}