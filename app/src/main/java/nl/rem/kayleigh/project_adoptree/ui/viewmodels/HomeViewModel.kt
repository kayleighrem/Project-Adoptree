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

    private val _trees: MutableLiveData<Resource<Tree>> = MutableLiveData()
    val trees: MutableLiveData<Resource<Tree>>
        get() = _trees
    val notassigned: String = "null"

    companion object {
        const val TAG ="HomeViewModel"
    }

//    fun checkIfUserHasTrees(id: Int) {
//        this.trees = getTrees(id)
//    }

    fun getTrees(id: Int) = viewModelScope.launch {
        try {
            _trees.postValue(Resource.Loading())
            val response = userRepository.getTreesByUser(id!!)
//            _trees.postValue(handleTreesResponse(response))
        } catch (e: Exception) {
            _trees.postValue(Resource.Error(context.getString(R.string.connection_error)))
            Log.e(TAG, "${context.getString(R.string.error_log)} ${e.message}")
        }
    }

    fun getTrees(authToken: String) = viewModelScope.launch {
        try {
            _trees.postValue(Resource.Loading())
//            val response = userRepository.getTreesByUser(authToken)
//            _trees.postValue(handleTreesResponse(response))
        } catch (e: Exception) {
            _trees.postValue(Resource.Error(context.getString(R.string.connection_error)))
            Log.e(TAG, "${context.getString(R.string.error_log)} ${e.message}")
        }
    }

    private fun handleTreesResponse(response: Response<List<Tree>>): Resource<Tree> {
        if (response.isSuccessful) {
//            response.body()?.let { resultResponse ->
//                if (trees == null) {
//                    _trees = resultResponse
//                } else {
//                    val oldArticles = treeResponse?.Results
//                    val newArticles = resultResponse.Results
//                    oldArticles?.addAll(newArticles)
//                }
//                return Resource.Success(treeResponse ?: resultResponse)
//            }
        }
        return Resource.Error(response.message())
    }
}