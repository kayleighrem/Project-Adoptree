package nl.rem.kayleigh.project_adoptree.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.util.CollectionUtils.listOf
import kotlinx.coroutines.launch
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.Telemetry
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.model.TreeTelemetry
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity
import nl.rem.kayleigh.project_adoptree.util.Resource
import retrofit2.Response

class HomeViewModel(private val mainActivity: MainActivity, val context: Context) : ViewModel() {

    private val _trees: MutableLiveData<Resource<List<Tree>>> = MutableLiveData()
    val trees: MutableLiveData<Resource<List<Tree>>>
        get() = _trees

    private val _telemetries: MutableLiveData<Resource<Telemetry>> = MutableLiveData()
    val telemetries: MutableLiveData<Resource<Telemetry>>
        get() = _telemetries

    private val _treeTelemetries: MutableLiveData<Resource<List<TreeTelemetry>>> = MutableLiveData()
    val treeTelemetries: MutableLiveData<Resource<List<TreeTelemetry>>>
        get() = _treeTelemetries

    companion object {
        const val TAG ="HomeViewModel"
    }

    fun getTrees(token: String) = viewModelScope.launch {
        try {
            _trees.postValue(Resource.Loading())
            val tokenstring = "Bearer $token"
            _trees.value = handleTreesResponse(mainActivity.userRepository.getTreesByUser(tokenstring))
        } catch (e: Exception) {
            _trees.postValue(Resource.Error(context.getString(R.string.connection_error)))
            Log.e(TAG, "${context.getString(R.string.error_log)} ${e.message}")
        }
    }

    fun getTelemetryByTreeId(token: String, id: Int) = viewModelScope.launch {
        try {
            val tokenstring = "Bearer $token"
            _telemetries.value = handleTelemetryResponse(mainActivity.treeRepository.getTelemetryByTreeId(id, tokenstring))
        } catch (e: Exception) {
            Log.e(TAG, "${context.getString(R.string.error_log)} ${e.message}")
        }
    }

    private fun handleTreesResponse(response: Response<List<Tree>>): Resource<List<Tree>> {
        if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                this.trees == it
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.body()?.toString()!!)
    }

    private fun handleTelemetryResponse(response: Response<Telemetry>): Resource<Telemetry>? {
        when (response.code()) {
            in 200..299 -> {
                response.body()!!.let {
                    return Resource.Success(it)
                }
            }
            else -> return null
        }
    }
}