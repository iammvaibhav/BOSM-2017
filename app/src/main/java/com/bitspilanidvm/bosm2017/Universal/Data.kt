package com.bitspilanidvm.bosm2017.Universal

import com.bitspilanidvm.bosm2017.Modals.Sports
import com.bitspilanidvm.bosm2017.R

object GLOBAL_DATA{

    var textScale = 2f
    val imageDrawableRes = arrayOf(R.drawable.events, R.drawable.ongoing, R.drawable.schedule, R.drawable.results)
    //val imageDrawableRes = arrayOf(R.color.Events, R.color.Ongoing, R.color.Schedule, R.color.Results)
    val headerTitles = arrayOf("EVENTS", "ONGOING", "SCHEDULE", "RESULTS")
    val shadowColors = arrayOf(R.color.Events, R.color.Ongoing, R.color.Schedule, R.color.Results)

    val sportsMap = mapOf(Pair(1, "Hockey"),
            Pair(2, "Athletics (Boys)"),
            Pair(3, "Athletics (Girls)"),
            Pair(4, "Basketball (Boys)"),
            Pair(5, "Lawn Tennis (Girls)"),
            Pair(6, "Lawn Tennis (Boys)"),
            Pair(7, "Squash"),
            Pair(8, "Swimming (Boys)"),
            Pair(9, "Football (Boys)"),
            Pair(10, "Badminton (Boys)"),
            Pair(11, ""),
            Pair(12, "Powerlifting"),
            Pair(13, "Chess"),
            Pair(14, "Table Tennis (Boys)"),
            Pair(15, "Table Tennis (Girls)"),
            Pair(16, "Taekwondo (Boys)"),
            Pair(17, "Taekwondo (Girls)"),
            Pair(18, "Volleyball (Boys)"),
            Pair(19, "Volleyball (Girls)"),
            Pair(20, "Badminton (Girls)"),
            Pair(21, "Carrom"),
            Pair(22, "Swimming (Girls)"),
            Pair(23, "Cricket"),
            Pair(24, "Football (Girls)"),
            Pair(25, "Snooker"),
            Pair(26, "Basketball (Girls)"))

    val imageRes1 = arrayOf(android.R.color.holo_blue_light)
    val imageRes2 = arrayOf(android.R.color.holo_blue_light)
    val imageRes3 = arrayOf(android.R.color.holo_blue_light)
    val imageRes4 = arrayOf(android.R.color.holo_blue_light)

    val heading1 = Array(5){i -> "Heading ${i + 1}"}
    val heading2 = Array(3){i -> "Heading ${i + 1}"}
    val heading3 = arrayOf<String>()
    val heading4 = arrayOf<String>()

    val details1 = Array(5){i -> "Details ${i + 1}"}
    val details2 = Array(3){i -> "Details ${i + 1}"}
    val details3 = Array(26){i -> "Sports Details ${i + 1}"}
    val details4 = Array(26){i -> "Sports Details ${i + 1}"}

    val fixtures = arrayOf(1,4,5,6,9,10,13,14,15,16,17,18,19,20,22,23,24,26)

    val availableSchedule = ArrayList<Int>()
    val availableResults = ArrayList<Int>()

    val headingsSchedule = ArrayList<String>()
    val detailsSchedule = ArrayList<String>()

    var sports = Sports()

}

data class NonFixtureSportsDataDecoupled(val categoryName: String,
                                         val categoryDescription: String,
                                         val categoryResult: ArrayList<String>,
                                         val date: String,
                                         val time: String,
                                         val venue: String,
                                         val round: String)

data class EventItem(val imageRes: Int,
                     val heading: String,
                     val time: String,
                     val details: String)