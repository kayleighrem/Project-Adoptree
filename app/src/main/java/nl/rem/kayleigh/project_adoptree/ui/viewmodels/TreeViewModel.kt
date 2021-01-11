package nl.rem.kayleigh.project_adoptree.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.AssignedTree
import nl.rem.kayleigh.project_adoptree.model.RefreshTokenResponse
import nl.rem.kayleigh.project_adoptree.repository.TreeRepository
import nl.rem.kayleigh.project_adoptree.util.Resource
import nl.rem.kayleigh.project_adoptree.util.SessionManager
import retrofit2.Response

class TreeViewModel(private val treeRepository: TreeRepository, context: Context) : ViewModel() {
    var sessionManager: SessionManager = SessionManager(context)

    companion object {
        const val TAG = "TreeViewModel"
    }

    fun personalizeTree(token: String, assignedTree: AssignedTree) = viewModelScope.launch {
        try {
            val tokenstring = "Bearer $token"
            handlePersonalizationResponse(treeRepository.personalizeTree(tokenstring, assignedTree))
        } catch (e: Exception) {
            Log.e(
                    TAG,
                    "${(R.string.error_log)} ${e.message}"
            )
        }
    }

    private fun handlePersonalizationResponse(response: Response<AssignedTree>) : Resource<AssignedTree> {
        if (response.code() in 200..299) {
            try {
                response.body()!!.let {
                    return Resource.Success(it)
                }
            } catch (e: java.lang.Exception) {
                println("dit werkt niet?.... :( ")
            }
        }
        return Resource.Error(response.message())
    }
}