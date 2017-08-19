package com.bitspilanidvm.bosm2017

import android.view.animation.Interpolator

class ReverseInterpolator : Interpolator {
    override fun getInterpolation(paramFloat: Float): Float {
        return Math.abs(paramFloat - 1f)
    }
}