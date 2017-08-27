package com.bitspilanidvm.bosm2017.Universal

object URL{
    val LOGIN = "https://bits-bosm.org/2017/api/login"
    val REGISTER = "https://bits-bosm.org/2017/api/register"
    val SHOW_SPORTS = "https://bits-bosm.org/2017/api/show_sports"
    val REGISTER_CAPTAIN = "https://bits-bosm.org/2017/api/register_captain"
    val MANAGE_SPORTS = "https://bits-bosm.org/2017/api/manage_sports"
    val LOGOUT = "https://bits-bosm.org/2017/api/logout"
    val API_TOKEN = "https://bits-bosm.org/2017/api/api_token"
    val API_TOKEN_REFRESH = "https://bits-bosm.org/2017/api/api_token_refresh"
    fun ADD_EVENTS(tc_id: Int) = "https://bits-bosm.org/2017/api/add_events/$tc_id"
    fun ADD_EXTRA_EVENT(tc_id: Int) = "https://bits-bosm.org/2017/api/add_extra_event/$tc_id"
}