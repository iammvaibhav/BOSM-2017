package com.bitspilanidvm.bosm2017.Activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.bitspilanidvm.bosm2017.Firebase.FirebaseFetcher
import com.bitspilanidvm.bosm2017.Modals.Sports
import com.bitspilanidvm.bosm2017.R
import com.bitspilanidvm.bosm2017.Universal.GLOBAL_DATA
import com.bitspilanidvm.bosm2017.Universal.convertListToNonFixtureSportsDecoupledList
import com.bitspilanidvm.bosm2017.Universal.getWinnerListFromFixtureSportsDataList
import com.bitspilanidvm.bosm2017.Universal.getWinnerListFromNonFixtureSportsDataDecoupledList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        FirebaseMessaging.getInstance().subscribeToTopic("news")

        val mDatabase = FirebaseDatabase.getInstance().reference.child("Schedule")
        FirebaseFetcher.initialize()

        mDatabase.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                GLOBAL_DATA.sports = Sports()
                GLOBAL_DATA.availableSchedule.clear()
                GLOBAL_DATA.availableResults.clear()

                FirebaseFetcher.fetchAndStore(dataSnapshot)
                getAvailableSchedulesAndResults()
                sortAvailableSchedulesAndResults()

                Log.e("sdf", getLatestUpdateResultDate(1).toString())

                startActivity(Intent(this@SplashScreen, Main::class.java))

            }
        })
    }

    fun getAvailableSchedulesAndResults(){
        for (i in 0..26) {
            if (GLOBAL_DATA.sports.fixtureSportsDataList[i].isNotEmpty()) {
                GLOBAL_DATA.availableSchedule.add(i)
                if (getWinnerListFromFixtureSportsDataList(GLOBAL_DATA.sports.fixtureSportsDataList[i]).isNotEmpty())
                    GLOBAL_DATA.availableResults.add(i)
            }

            if (GLOBAL_DATA.sports.nonFixtureSportsDataList[i].isNotEmpty()) {
                GLOBAL_DATA.availableSchedule.add(i)
                if (getWinnerListFromNonFixtureSportsDataDecoupledList(convertListToNonFixtureSportsDecoupledList(GLOBAL_DATA.sports.nonFixtureSportsDataList[i])).isNotEmpty())
                    GLOBAL_DATA.availableResults.add(i)
            }
        }

    }

    fun sortAvailableSchedulesAndResults(){

        GLOBAL_DATA.availableSchedule.forEach { i -> GLOBAL_DATA.availableScheduleMap.put(i, getLatestUpdateScheduleDate(i))}
        GLOBAL_DATA.availableResults.forEach { i -> GLOBAL_DATA.availableResultsMap.put(i, getLatestUpdateResultDate(i))}

        Collections.sort(GLOBAL_DATA.availableSchedule, kotlin.Comparator { first, second ->
            return@Comparator GLOBAL_DATA.availableScheduleMap[second]?.compareTo(GLOBAL_DATA.availableScheduleMap[first]) ?: 0
        })

        Collections.sort(GLOBAL_DATA.availableResults, kotlin.Comparator { first, second ->
            return@Comparator GLOBAL_DATA.availableResultsMap[second]?.compareTo(GLOBAL_DATA.availableResultsMap[first]) ?: 0
        })
    }

    fun getLatestUpdateScheduleDate(i: Int): Date{
        if (i in GLOBAL_DATA.fixtures){
            var latestDate: Date? = null
            for (j in GLOBAL_DATA.sports.fixtureSportsDataList[i]) {
                if (latestDate == null)
                    latestDate = j.scheduleTime
                else if (j.scheduleTime > latestDate)
                    latestDate = j.scheduleTime
            }
            return latestDate ?: Date()
        }else{
            var latestDate: Date? = null
            for (j in GLOBAL_DATA.sports.nonFixtureSportsDataList[i]) {
                if (latestDate == null)
                    latestDate = j.scheduleTime
                else if (j.scheduleTime > latestDate)
                    latestDate = j.scheduleTime
            }
            return latestDate ?: Date()
        }
    }

    fun getLatestUpdateResultDate(i: Int): Date{
        if (i in GLOBAL_DATA.fixtures){
            var latestDate: Date? = null
            for (j in getWinnerListFromFixtureSportsDataList(GLOBAL_DATA.sports.fixtureSportsDataList[i])) {
                Log.e("$i", j.resultTime.toString())
                if (latestDate == null)
                    latestDate = j.resultTime
                else if (j.resultTime > latestDate)
                    latestDate = j.resultTime
            }
            return latestDate ?: Date()
        }else{
            var latestDate: Date? = null
            for (j in getWinnerListFromNonFixtureSportsDataDecoupledList(convertListToNonFixtureSportsDecoupledList(GLOBAL_DATA.sports.nonFixtureSportsDataList[i]))) {
                if (latestDate == null)
                    latestDate = j.resultTime
                else if (j.resultTime > latestDate)
                    latestDate = j.resultTime
            }
            return latestDate ?: Date()
        }
    }
}
