package nl.rem.kayleigh.project_adoptree.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.model.User
import nl.rem.kayleigh.project_adoptree.repository.TreeRepository
import nl.rem.kayleigh.project_adoptree.repository.UserRepository
import nl.rem.kayleigh.project_adoptree.util.Resource
import retrofit2.Response
import java.lang.StringBuilder

class HomeViewModel(private val userRepository: UserRepository, val context: Context) : ViewModel() {

    private val _trees: MutableLiveData<Resource<List<Tree>>> = MutableLiveData()
    val trees: MutableLiveData<Resource<List<Tree>>>
        get() = _trees
    val notassigned: String = "null"

    companion object {
        const val TAG ="HomeViewModel"
    }

//    fun checkIfUserHasTrees(id: Int) {
//        this.trees = getTrees(id)
//    }

    fun getTrees(token: String) = viewModelScope.launch {
        try {
            val tokenstring = "Bearer $token"
//            _trees.postValue(Resource.Loading())
//            val response = userRepository.getTreesByUser()
//            _trees.postValue(handleTreesResponse(response))
            _trees.value = handleTreesResponse(userRepository.getTreesByUser(tokenstring))
        } catch (e: Exception) {
            _trees.postValue(Resource.Error(context.getString(R.string.connection_error)))
            Log.e(TAG, "${context.getString(R.string.error_log)} ${e.message}")
        }
    }

//    fun getTrees(authToken: String) = viewModelScope.launch {
//        try {
//            _trees.postValue(Resource.Loading())
////            val response = userRepository.getTreesByUser(authToken)
////            _trees.postValue(handleTreesResponse(response))
//        } catch (e: Exception) {
//            _trees.postValue(Resource.Error(context.getString(R.string.connection_error)))
//            Log.e(TAG, "${context.getString(R.string.error_log)} ${e.message}")
//        }
//    }

    private fun handleTreesResponse(response: Response<List<Tree>>): Resource<List<Tree>> {
        if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                this.trees == it
                println("response successfull? ")
                return Resource.Success(it)
            }
        }
        println("response error ")
        return Resource.Error(response.body()?.toString()!!)
    }
}