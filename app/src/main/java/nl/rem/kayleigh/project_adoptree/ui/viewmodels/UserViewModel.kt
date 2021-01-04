package nl.rem.kayleigh.project_adoptree.ui.viewmodels

import android.content.Context
import android.security.KeyChain
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
import retrofit2.Response

class UserViewModel(private val userRepository: UserRepository, val context: Context) :
    ViewModel() {

    var isAuthenticated: Boolean = false
    var accessTokenKey: String = "accessToken"

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> =
            MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    private val _signUpResponse: MutableLiveData<Resource<UserResponseRegister>> =
        MutableLiveData()
    val signUpResponse: LiveData<Resource<UserResponseRegister>>
        get() = _signUpResponse

    companion object {
        const val TAG = "UserViewModel"
    }

    init {
        isAuthenticated = accessTokenKey != null
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
            val response = userRepository.register(user)
            println("response register : " + response)
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
        if (response.isSuccessful && response.body()?.Success == true) {
            response.body()?.let {
                println("response successfull? ")
                return Resource.Success(it)
            }
        }
        println("response error ")
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
}