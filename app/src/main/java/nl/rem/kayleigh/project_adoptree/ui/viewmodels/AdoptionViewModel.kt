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

class AdoptionViewModel(private val adoptionRepository: AdoptionRepository, val context: Context) : ViewModel() {
    private val _trees: MutableLiveData<Resource<Tree>> = MutableLiveData()
    val trees: MutableLiveData<Resource<Tree>>
        get() = _trees
    val notassigned: String = "null"

    companion object {
        const val TAG = "AdoptionViewModel"
    }

    fun getTrees() = viewModelScope.launch {
        try {
            _trees.postValue(Resource.Loading())
            val response = adoptionRepository.getAllTrees(notassigned)
//            _articles.postValue(handleArticlesResponse(response))
        } catch (e: Exception) {
//            _articles.postValue(Resource.Error(context.getString(R.string.connection_error)))
            Log.e(TAG, "${context.getString(R.string.error_log)} ${e.message}")
        }
    }
}