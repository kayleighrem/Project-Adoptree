package nl.rem.kayleigh.project_adoptree.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.repository.AdoptionRepository
import nl.rem.kayleigh.project_adoptree.util.Resource
import retrofit2.Response

class AdoptionViewModel(private val adoptionRepository: AdoptionRepository, val context: Context) : ViewModel() {
    private val _trees: MutableLiveData<Resource<List<Tree>>> = MutableLiveData()
    val trees: MutableLiveData<Resource<List<Tree>>>
        get() = _trees
    private var treeResponse: List<Tree>? = ArrayList()

    companion object {
        const val TAG = "AdoptionViewModel"
    }

    fun getAvailableTrees() = viewModelScope.launch {
        try {
            _trees.postValue(Resource.Loading())
            val response: Response<List<Tree>> = adoptionRepository.getAvailableTrees()
            _trees.postValue(handleTreeResponse(response))
        } catch (e: Exception) {
            _trees.postValue(Resource.Error(context.getString(R.string.connection_error)))
            Log.e(TAG, "${context.getString(R.string.error_log)} ${e.message}")
        }
    }

    private fun handleTreeResponse(response: Response<List<Tree>>): Resource<List<Tree>> {
        if (response.body()?.isEmpty() == false) {
            response.body()?.let { resultResponse ->
                if (treeResponse == null) {
                    treeResponse = resultResponse
                } else {
                    treeResponse = resultResponse
                    val oldTrees = treeResponse
                    val newTrees = resultResponse
                }
                return Resource.Success(treeResponse ?: resultResponse)
            }
        }
        return Resource.Error("response.message()")
    }
}