package nl.rem.kayleigh.project_adoptree.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.*
import nl.rem.kayleigh.project_adoptree.repository.UserRepository
import nl.rem.kayleigh.project_adoptree.util.Resource
import nl.rem.kayleigh.project_adoptree.util.SessionManager
import retrofit2.Response

class UserViewModel(private val userRepository: UserRepository, val context: Context) :
    ViewModel() {
    var isAuthenticated: Boolean = false
    var accessTokenKey: String = "accessToken"

    var sessionManager: SessionManager = SessionManager(context)

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> =
            MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    private val _loggedinUserResponse: MutableLiveData<Resource<User>> =
            MutableLiveData()
    val loggedinUserResponse: LiveData<Resource<User>> get() = _loggedinUserResponse

    private val _signUpResponse: MutableLiveData<Resource<UserResponseRegister>> =
        MutableLiveData()
    val signUpResponse: LiveData<Resource<UserResponseRegister>>
        get() = _signUpResponse

    private val _refreshTokenResponse: MutableLiveData<Resource<RefreshTokenResponse>> =
        MutableLiveData()
    val refreshTokenResponse: LiveData<Resource<RefreshTokenResponse>>
        get() = _refreshTokenResponse

    companion object {
        const val TAG = "UserViewModel"
    }

    init {
        isAuthenticated = accessTokenKey != null
    }

    fun getLoggedInUser(accesstoken: String) = viewModelScope.launch {
        try {
            val tokenstring = "Bearer $accesstoken"
            println("tokenstring: " + tokenstring)
            println("logginuserresponse = " + loggedinUserResponse.value?.data)
//            handleLoggedInUserResponse(userRepository.getLoggedInUser(tokenstring))
            try {
                _loggedinUserResponse.value = handleLoggedInUserResponse(userRepository.getLoggedInUser(tokenstring))
            } catch (e: java.lang.Exception) {
                refreshToken(accesstoken)
            }

        } catch (e: java.lang.Exception) {
//            refreshToken(accesstoken)
            Log.e(
                    TAG,
                    "${context.getString(R.string.error_log)} ${R.string.test} ${e.message}"
            )
        }
    }

    fun refreshToken(token: String) = viewModelScope.launch {
        try {
            val tokenstring = "Bearer $token"
            println("test 1")
            val refresh = userRepository.newTokens(tokenstring)
            println("test 2 ")
            _refreshTokenResponse.value = handleRefreshTokenResponse(refresh)
            println("refresh token response value = " + refreshTokenResponse.value!!.data!!.refreshToken)
            println("access token response value = " + refreshTokenResponse.value!!.data!!.accessToken)
            sessionManager.updateSession(refreshTokenResponse.value!!.data!!.accessToken, refreshTokenResponse.value!!.data!!.refreshToken)

//            handleRefreshTokenResponse(userRepository.newTokens(tokenstring))
//            _loggedinUserResponse.value = handleLoginUserResponse(userRepository.newTokens(tokenstring))
        } catch (e: java.lang.Exception) {
            println("refresh token function")
            Log.e(
                    TAG,
                    "${context.getString(R.string.error_log)} ${e.message}"
            )
        }
    }

    fun logout() = viewModelScope.launch {
        try {
            userRepository.logout()
        } catch (e: Exception) {
            Log.e(
                    TAG,
                    "${context.getString(R.string.error_log)} ${e.message}"
            )
        }
    }

    fun login(user: UserLogin) = viewModelScope.launch {
        try {
            _loginResponse.value = handleLoginResponse(userRepository.login(user))
        } catch (e: Exception) {
            _loginResponse.postValue(Resource.Error(context.getString(R.string.connection_error)))
            Log.e(
                TAG,
                "${context.getString(R.string.error_log)} ${e.message}"
            )
        }
    }

    fun register(user: User) = viewModelScope.launch {
        try {
            _signUpResponse.value = handleSignUpResponse(userRepository.register(user))
        } catch (e: Exception) {
            _signUpResponse.postValue(Resource.Error(context.getString(R.string.connection_error)))
            Log.e(
                TAG,
                "${context.getString(R.string.error_log)} ${e.message}"
            )
        }
    }


    private fun handleSignUpResponse(response: Response<UserResponseRegister>) : Resource<UserResponseRegister> {
        if (response.code() in 200..300) {
            response.body()?.let {
                return Resource.Success(it)
            }
        } else if (response.code() in 400..403) {
            return Resource.Error(response.body()?.Message!!)
        } else {
            return Resource.Error(response.body()?.Message!!)
        }
        return Resource.Error(response.body()?.Message!!)
    }

    private fun handleLoginResponse(response: Response<LoginResponse>): Resource<LoginResponse> {
        if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                it.accessToken = response.body()!!.accessToken
                it.refreshToken = response.body()!!.refreshToken
                it.userId = response.body()!!.userId

                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun handleLoggedInUserResponse(response: Response<User>) : Resource<User> {
        println("response logged in user? " + response)
        when (response.code()) {
            in 200..299 -> {
                println("test response when code is good")
                response.body()!!.let {
                    return Resource.Success(it)
                }
            }
            in 400..404 -> {
                println("test response when code is error")
                refreshToken(loginResponse.value?.data?.accessToken!!)
                return Resource.Error(response.message())
            }
            else -> return Resource.Error(response.message())
        }
//        if (response.code() in 200..299) {
//            println("not null body" + response.body())
//            response.body()!!.let {
//                return Resource.Success(it)
//            }
//        }
//        if (response.isSuccessful && response.body() != null) {
//            response.body()!!.let {
//                return Resource.Success(it)
//            }
//        }
//        return Resource.Error(response.message())
    }

    private fun handleRefreshTokenResponse(response: Response<RefreshTokenResponse>) : Resource<RefreshTokenResponse> {
        println("response access token? " + response.body()!!.accessToken)
        println("response refresh token? " + response.body()!!.refreshToken)

        if (response.code() in 200..299) {
            try {
                sessionManager.updateSession(response.body()!!.accessToken, response.body()!!.refreshToken)
//            sessionManager.getUserDetails().accessToken = response.body()!!.accessToken
//            sessionManager.getUserDetails().refreshToken = response.body()!!.refreshToken
                response.body()!!.let {
                    return Resource.Success(it)
                }
            } catch (e: java.lang.Exception) {
                println("dit werkt niet?.... :( ")
            }

        }
//        if (response.isSuccessful && response.body() != null) {
//            response.body()!!.let {
//                return Resource.Success(it)
//            }
//        }
        return Resource.Error(response.message())
    }
}