package com.bitspilanidvm.bosm2017.Universal

import com.bitspilanidvm.bosm2017.Modals.Sports
import com.bitspilanidvm.bosm2017.R
import java.util.*
import kotlin.collections.ArrayList

object GLOBAL_DATA{

    var textScale = 2f
    val imageDrawableRes = arrayOf(R.drawable.schedule, R.drawable.results, R.drawable.events, R.drawable.ongoing)
    //val imageDrawableRes = arrayOf(R.color.Events, R.color.Ongoing, R.color.Schedule, R.color.Results)
    val headerTitles = arrayOf("SCHEDULE", "RESULTS", "EVENTS", "ONGOING")
    val shadowColors = arrayOf(R.color.Schedule, R.color.Results, R.color.Events, R.color.Ongoing)

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

    val sportsMapReverse = mapOf(Pair("Hockey", 1),
            Pair("Athletics (Boys)", 2),
            Pair("Athletics (Girls)", 3),
            Pair("Basketball (Boys)", 4),
            Pair("Lawn Tennis (Girls)", 5),
            Pair("Lawn Tennis (Boys)", 6),
            Pair("Squash", 7),
            Pair("Swimming (Boys)", 8),
            Pair("Football (Boys)", 9),
            Pair("Badminton (Boys)", 10),
            Pair("", 11),
            Pair("Powerlifting", 12),
            Pair("Chess", 13),
            Pair("Table Tennis (Boys)", 14),
            Pair("Table Tennis (Girls)", 15),
            Pair("Taekwondo (Boys)", 16),
            Pair("Taekwondo (Girls)", 17),
            Pair("Volleyball (Boys)", 18),
            Pair("Volleyball (Girls)", 19),
            Pair("Badminton (Girls)", 20),
            Pair("Carrom", 21),
            Pair("Swimming (Girls)", 22),
            Pair("Cricket", 23),
            Pair("Football (Girls)", 24),
            Pair("Snooker", 25),
            Pair("Basketball (Girls)", 26))

    val sportsImageRes = mapOf<String, Int>(Pair("Hockey", R.drawable.hockey),
            Pair("Athletics (Boys)", R.drawable.atheletics),
            Pair("Athletics (Girls)", R.drawable.atheletics),
            Pair("Basketball (Boys)", R.drawable.basketball),
            Pair("Lawn Tennis (Girls)", R.drawable.tennis),
            Pair("Lawn Tennis (Boys)", R.drawable.tennis),
            Pair("Squash", R.drawable.squash),
            Pair("Swimming (Boys)", R.drawable.swimming),
            Pair("Football (Boys)", R.drawable.football),
            Pair("Badminton (Boys)", R.drawable.badminton),
            Pair("", 11),
            Pair("Powerlifting", R.drawable.powerlifting),
            Pair("Chess", R.drawable.chess),
            Pair("Table Tennis (Boys)", R.drawable.tabletennis),
            Pair("Table Tennis (Girls)", R.drawable.tabletennis),
            Pair("Taekwondo (Boys)", R.drawable.taekwondo),
            Pair("Taekwondo (Girls)", R.drawable.taekwondo),
            Pair("Volleyball (Boys)", R.drawable.volleyball),
            Pair("Volleyball (Girls)", R.drawable.volleyball),
            Pair("Badminton (Girls)", R.drawable.badminton),
            Pair("Carrom", R.drawable.carrom),
            Pair("Swimming (Girls)", R.drawable.swimming),
            Pair("Cricket", R.drawable.cricket),
            Pair("Football (Girls)", R.drawable.football),
            Pair("Snooker", R.drawable.snooker),
            Pair("Basketball (Girls)", R.drawable.basketball))

    val sportsImageIconRes = mapOf<String, Int>(Pair("Hockey", R.drawable.ic_hockey),
            Pair("Athletics (Boys)", R.drawable.ic_atheletics),
            Pair("Athletics (Girls)", R.drawable.ic_atheletics),
            Pair("Basketball (Boys)", R.drawable.ic_basketball),
            Pair("Lawn Tennis (Girls)", R.drawable.ic_tennis),
            Pair("Lawn Tennis (Boys)", R.drawable.ic_tennis),
            Pair("Squash", R.drawable.ic_squash),
            Pair("Swimming (Boys)", R.drawable.ic_swimming),
            Pair("Football (Boys)", R.drawable.ic_football),
            Pair("Badminton (Boys)", R.drawable.ic_badminton),
            Pair("", 11),
            Pair("Powerlifting", R.drawable.ic_powerlifting),
            Pair("Chess", R.drawable.ic_chess),
            Pair("Table Tennis (Boys)", R.drawable.ic_tabletennis),
            Pair("Table Tennis (Girls)", R.drawable.ic_tabletennis),
            Pair("Taekwondo (Boys)", R.drawable.ic_taekwondo),
            Pair("Taekwondo (Girls)", R.drawable.ic_taekwondo),
            Pair("Volleyball (Boys)", R.drawable.ic_volleyball),
            Pair("Volleyball (Girls)", R.drawable.ic_volleyball),
            Pair("Badminton (Girls)", R.drawable.ic_badminton),
            Pair("Carrom", R.drawable.carrom),
            Pair("Swimming (Girls)", R.drawable.ic_swimming),
            Pair("Cricket", R.drawable.ic_cricket),
            Pair("Football (Girls)", R.drawable.ic_football),
            Pair("Snooker", R.drawable.snooker),
            Pair("Basketball (Girls)", R.drawable.ic_basketball))

    val fixtures = arrayOf(1,4,5,6,9,10,13,14,15,16,17,18,19,20,22,23,24,26)

    val imageRes1 = arrayOf(android.R.color.holo_blue_light)
    val imageRes2 = arrayOf(android.R.color.holo_blue_light)
    val imageRes3 = arrayOf(android.R.color.holo_blue_light)
    val imageRes4 = arrayOf(android.R.color.holo_blue_light)

    val heading1 = Array(5){i -> "Heading ${i + 1}"}
    val heading2 = Array(3){i -> "Heading ${i + 1}"}

    val details1 = Array(5){i -> "Details ${i + 1}"}
    val details2 = Array(3){i -> "Details ${i + 1}"}

    val availableSchedule = ArrayList<Int>()
    val availableResults = ArrayList<Int>()

    val availableScheduleMap = HashMap<Int, Date>()
    val availableResultsMap = HashMap<Int, Date>()

    val headingsSchedule = ArrayList<String>()
    val headingsResults = ArrayList<String>()
    val detailsSchedule = ArrayList<String>()
    val detailsResults = ArrayList<String>()

    val sponsorImageRes = arrayOf(R.drawable.events, R.drawable.ongoing, R.drawable.schedule, R.drawable.results)
    val sponsorText = arrayOf("EVENTS", "ONGOING", "SCHEDULE", "RESULTS")

    val developerImageRes = arrayOf(R.drawable.events, R.drawable.ongoing, R.drawable.schedule, R.drawable.results)
    val developerName = arrayOf("VAIBHAV", "SOMBUDDHA", "NO ONE", "---")
    val developerDescription = arrayOf("UI/UX Designer | Backend Developer", "Backend Developer", "Chatbot Developer (?)", "---")

    var sports = Sports()
}

data class NonFixtureSportsDataDecoupled(val categoryName: String,
                                         val categoryDescription: String,
                                         val categoryResult: ArrayList<String>,
                                         val date: String,
                                         val time: String,
                                         val venue: String,
                                         val round: String,
                                         val scheduleTime: Date,
                                         val resultTime: Date,
                                         val categoryScore: ArrayList<String>)

data class EventItem(val imageRes: Int,
                     val heading: String,
                     val time: String,
                     val details: String)