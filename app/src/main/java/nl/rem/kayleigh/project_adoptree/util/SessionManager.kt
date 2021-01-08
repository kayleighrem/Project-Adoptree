package nl.rem.kayleigh.project_adoptree.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import nl.rem.kayleigh.project_adoptree.model.LoginResponse
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.UserViewModel
import nl.rem.kayleigh.project_adoptree.util.Constants.Companion.ACCESSTOKEN
import nl.rem.kayleigh.project_adoptree.util.Constants.Companion.LOGIN
import nl.rem.kayleigh.project_adoptree.util.Constants.Companion.PREF_NAME
import nl.rem.kayleigh.project_adoptree.util.Constants.Companion.PRIVATE_MODE
import nl.rem.kayleigh.project_adoptree.util.Constants.Companion.REFRESHTOKEN
import nl.rem.kayleigh.project_adoptree.util.Constants.Companion.USERID
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class SessionManager(context: Context) {
    private val shared: SharedPreferences
    private val editor: SharedPreferences.Editor
    private val _context: Context = context

    init {
        shared = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = shared.edit()
    }

    fun createSession(accesstoken: String, refreshtoken: String) {
        editor.putBoolean(LOGIN, true)
        editor.putString(ACCESSTOKEN, accesstoken)
        editor.putString(REFRESHTOKEN, refreshtoken)
        editor.apply()
    }

    fun isLogin(): Boolean {
        return shared.getBoolean(LOGIN, false)
    }

    fun getUserDetails(): LoginResponse {
        val accesstoken = shared.getString(ACCESSTOKEN, null)
        val refreshtoken = shared.getString(REFRESHTOKEN, null)
        return LoginResponse(accesstoken!!, refreshtoken!!)
    }

    fun clearUserDetails() {
        editor.clear()
        editor.apply()
    }
}