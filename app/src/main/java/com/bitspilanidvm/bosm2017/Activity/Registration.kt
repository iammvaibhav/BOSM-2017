package com.bitspilanidvm.bosm2017.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bitspilanidvm.bosm2017.R
import com.bitspilanidvm.bosm2017.Universal.URL
import org.json.JSONArray
import org.json.JSONObject

class Registration : AppCompatActivity() {


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


        /*if (URL.cookieJar == null)
            URL.cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(applicationContext))

        if (URL.okHttpClient == null)
            URL.okHttpClient = OkHttpClient.Builder().cookieJar(URL.cookieJar).build()*/

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


        /*AndroidNetworking.post(url)
                .addJSONObjectBody(regData)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        //textView.text = response?.getString("fragment_message")
                        Log.e("Response", response?.toString(4))
                    }

                    override fun onError(anError: ANError?) {
                        Log.e("error", anError?.errorBody)
                    }
                })*/

        val data = JSONObject()
        data.put("username", "test")
        data.put("password", "qwertyuiop")

        var token = ""

        val participants = JSONArray()
        participants.put("dsfsdf")
        participants.put("sdfsdf")
        participants.put("sgfhfdh")
        participants.put("dsfsdf")
        participants.put("sdfsdf")
        participants.put("sgfhfdh")

        val json = JSONObject()
        json.put("event", "4")
        json.put("name", "Vaibhav")
        json.put("gender", "M")
        json.put("phone", "9529179518")
        json.put("g_l", 85)
        json.put("participants", participants.toString())

        Log.e("dsaf", json.toString())

        /*AndroidNetworking.post(URL.API_TOKEN)
                .addJSONObjectBody(data)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        Log.e("response", response.toString())
                        token = response.getString("token")

                        AndroidNetworking.post(URL.REGISTER_CAPTAIN)
                                .addHeaders("Authorization", "JWT $token")
                                .addJSONObjectBody(json)
                                .build()
                                .getAsJSONObject(object : JSONObjectRequestListener{
                                    override fun onResponse(response: JSONObject) {
                                        Log.e("response", response.toString())
                                    }

                                    override fun onError(anError: ANError) {
                                        Log.e("error", anError.errorBody)
                                    }
                                })

                    }

                    override fun onError(anError: ANError?) {
                        Log.e("error", anError?.errorBody)
                    }
                })*/

        val arr = JSONArray()
        arr.put(3)
        arr.put(5)

        val obj = JSONObject()
        obj.put("sportsadded", arr.toString())


        AndroidNetworking.post(URL.API_TOKEN)
                .addJSONObjectBody(data)
                .build().getAsJSONObject(object : JSONObjectRequestListener{
            override fun onResponse(response: JSONObject) {
                token = response.getString("token")

                AndroidNetworking.post(URL.MANAGE_SPORTS)
                        .addHeaders("Authorization", "JWT $token")
                        .addJSONObjectBody(obj)
                        .build()
                        .getAsJSONObject(object : JSONObjectRequestListener{
                            override fun onResponse(response: JSONObject) {
                                Log.e("response", response.toString())
                            }

                            override fun onError(anError: ANError) {
                                Log.e("error", anError.errorBody)
                            }
                        })
            }

            override fun onError(anError: ANError?) {

            }
        })







        /*AndroidNetworking.post(URL.REGISTER)
                .addJSONObjectBody(regData)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        Log.e("Response", response.toString())
                    }

                    override fun onError(anError: ANError) {
                        Log.e("Error", anError.errorBody)
                    }
                })*/


        /*AndroidNetworking.post(URL.LOGIN)
                .addHeaders("Content-Type", "application/x-www-form-urlencoded")
                .addBodyParameter("username", "test")
                .addBodyParameter("password", "qwertyuiop")
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener{
                    override fun onResponse(response: JSONObject?) {
                        Log.e("response", response?.toString())
                        AndroidNetworking.post(URL.REGISTER_CAPTAIN)
                                .addJSONObjectBody(json)
                                .build()
                                .getAsJSONObject(object : JSONObjectRequestListener{
                                    override fun onResponse(response: JSONObject?) {
                                        Log.e("response", response.toString())
                                    }

                                    override fun onError(anError: ANError?) {
                                        Log.e("", anError?.errorBody)
                                    }
                                })
                    }

                    override fun onError(anError: ANError?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })*/




    }
}
