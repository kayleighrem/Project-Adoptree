package nl.rem.kayleigh.project_adoptree.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.User
import nl.rem.kayleigh.project_adoptree.model.UserResponse
import nl.rem.kayleigh.project_adoptree.model.UserResponseRegister
import nl.rem.kayleigh.project_adoptree.repository.UserRepository
import nl.rem.kayleigh.project_adoptree.util.Resource
import retrofit2.Response

class UserViewModel(private val userRepository: UserRepository, val context: Context) :
    ViewModel() {

    private val _loginResponse: MutableLiveData<Resource<UserResponse>> =
        MutableLiveData()
    val loginResponse: LiveData<Resource<UserResponse>>
        get() = _loginResponse

    private val _signUpResponse: MutableLiveData<Resource<UserResponseRegister>> =
        MutableLiveData()
    val signUpResponse: LiveData<Resource<UserResponseRegister>>
        get() = _signUpResponse

    companion object {
        const val TAG = "UserViewModel"
    }

    fun login(user: User) = viewModelScope.launch {
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

    fun createUser(user: User) = viewModelScope.launch {
        try {
            _signUpResponse.value = handleSignUpResponse(userRepository.createUser(user))
        } catch (e: Exception) {
            _signUpResponse.postValue(Resource.Error(context.getString(R.string.connection_error)))
            Log.e(
                TAG,
                "${context.getString(R.string.error_log)} ${e.message}"
            )
        }
    }

    suspend fun createUser(firstname: String, lastname: String, username: String, email: String, password: String) {
        try {
            userRepository.createUser(firstname, lastname, username, email, password)
//            _signUpResponse.value = handleSignUpResponse(userRepository.createUser(firstname, lastname, username, email, password))
        } catch (e: Exception) {
            _signUpResponse.postValue(Resource.Error(context.getString(R.string.connection_error)))
            Log.e(
                TAG,
                "${context.getString(R.string.error_log)} ${e.message}"
            )
        }
    }

    private fun handleSignUpResponse(response: Response<UserResponseRegister>): Resource<UserResponseRegister> {
        if (response.isSuccessful && response.body()?.Success == true) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.body()?.Message!!)
    }

    private fun handleLoginResponse(response: Response<UserResponse>): Resource<UserResponse> {
        if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

}