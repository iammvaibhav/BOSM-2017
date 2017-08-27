package com.bitspilanidvm.bosm2017.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.bitspilanidvm.bosm2017.Adapters.ViewSports
import com.bitspilanidvm.bosm2017.ClickListeners.ViewSportsClickListener
import com.bitspilanidvm.bosm2017.R
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

class ViewSports : Fragment(){

    lateinit var hamburgerIcon: ImageView
    lateinit var recyclerView: RecyclerView
    lateinit var manageSports: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_view_sports, container, false)

        hamburgerIcon = view.findViewById(R.id.hamburgerIcon)
        recyclerView = view.findViewById(R.id.recycler_view)
        manageSports = view.findViewById(R.id.manageSport)

        hamburgerIcon.setOnClickListener {
            if (activity.drawerLayout.isDrawerOpen(GravityCompat.START))
                activity.drawerLayout.closeDrawer(GravityCompat.START)
            else
                activity.drawerLayout.openDrawer(GravityCompat.START)
        }

        val currentSports = JSONArray(arguments.getString("currentSports"))
        val sportsName = ArrayList<String>()

        for (i in 0..(currentSports.length() - 1)){
            sportsName.add(currentSports.getJSONObject(i).getString("name"))
        }

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = ViewSports(sportsName, object : ViewSportsClickListener {
                override fun onItemClick(itemHolder: com.bitspilanidvm.bosm2017.ViewHolder.SportsAdded, position: Int) {

            }
        })

        manageSports.setOnClickListener {
            activity.supportFragmentManager.beginTransaction().replace(R.id.rootConstraintLayout, ManageSports()).commit()
        }

        return view
    }
}