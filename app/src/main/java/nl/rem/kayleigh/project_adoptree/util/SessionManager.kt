package nl.rem.kayleigh.project_adoptree.util

import android.content.Context
import android.content.SharedPreferences
import nl.rem.kayleigh.project_adoptree.model.LoginResponse
import nl.rem.kayleigh.project_adoptree.util.Constants.Companion.ACCESSTOKEN
import nl.rem.kayleigh.project_adoptree.util.Constants.Companion.LOGIN
import nl.rem.kayleigh.project_adoptree.util.Constants.Companion.PREF_NAME
import nl.rem.kayleigh.project_adoptree.util.Constants.Companion.PRIVATE_MODE
import nl.rem.kayleigh.project_adoptree.util.Constants.Companion.REFRESHTOKEN
import nl.rem.kayleigh.project_adoptree.util.Constants.Companion.USERID

class SessionManager(private val context: Context) {
    private val shared: SharedPreferences
    private val editor: SharedPreferences.Editor
    private val _context: Context = context

    init {
        shared = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = shared.edit()
    }

//    fun createSession(username: String, auth: String) {
//        editor.putBoolean(LOGIN, true)
//        editor.putString(USERNAME, username)
//        editor.putString(AUTH, auth)
//        editor.apply()
//    }

    fun createSession(accesstoken: String, refreshtoken: String, userid: String) {
        editor.putBoolean(LOGIN, true)
        editor.putString(ACCESSTOKEN, accesstoken)
        editor.putString(REFRESHTOKEN, refreshtoken)
        editor.putString(USERID, userid)
        editor.apply()
    }


    fun isLogin(): Boolean {
        return shared.getBoolean(LOGIN, false)
    }

//    fun getUserDetails(): UserResponse {
//        val username = shared.getString(USERNAME, null)
//        val auth = shared.getString(AUTH, null)
//        return UserResponse(username, auth)
//    }
    fun getUserDetails(): LoginResponse {
        val accesstoken = shared.getString(ACCESSTOKEN, null)
        val refreshtoken = shared.getString(REFRESHTOKEN, null)
        val userid = shared.getString(USERID, null)
        return LoginResponse(accesstoken!!, refreshtoken!!, userid!!)
    }


    fun clearUserDetails() {
        editor.clear()
        editor.apply()
    }
}