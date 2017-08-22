package com.bitspilanidvm.bosm2017

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject


class RegistrationActivity : AppCompatActivity() {

    val url = "https://bits-bosm.org/2017/api/register"
    var username = "TES43T2s2"
    var password = "dfgdf"
    var name = "dfg"
    var city = "dfgdfg"
    var state = "dfg"
    var college = "dfgdf"
    var phone = "354235"
    var gender = "M"
    var email = "sdfdfgsf@gmail.com"

    lateinit var textView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        textView = findViewById(R.id.textView)

        AndroidNetworking.initialize(applicationContext)

        //preparing profile json object
        val profileData = JSONObject()
        profileData.put("name", name)
        profileData.put("city", city)
        profileData.put("state", state)
        profileData.put("college", college)
        profileData.put("phone", phone)
        profileData.put("gender", gender)
        profileData.put("email", email)

        //preparing registration object
        val regData = JSONObject()
        regData.put("username", username)
        regData.put("password", password)
        regData.put("profile", profileData)


        AndroidNetworking.post(url)
                .addJSONObjectBody(regData)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        //textView.text = response?.getString("message")
                        Log.e("Response", response?.toString(4))
                    }

                    override fun onError(anError: ANError?) {
                        Log.e("error", anError?.errorBody)
                    }
                })
    }
}
