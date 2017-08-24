package com.bitspilanidvm.bosm2017

object GLOBAL_DATA{
    var textScale = 2f
    val imageDrawableRes = arrayOf(R.drawable.events, R.drawable.ongoing, R.drawable.schedule, R.drawable.results)
    val headerTitles = arrayOf("EVENTS", "ONGOING", "SCHEDULE", "RESULTS")
    val shadowColors = arrayOf(R.color.Events, R.color.Ongoing, R.color.Schedule, R.color.Results)

    val scheduleFixtureList = ArrayList<FixtureSportsData>()
    val scheduleNonFixturesList = ArrayList<NonFixtureSportsDataDecoupled>()
    val resultsFixtureList = ArrayList<FixtureSportsData>()
    val resultsNonFixtureList = ArrayList<NonFixtureSportsDataDecoupled>()

}