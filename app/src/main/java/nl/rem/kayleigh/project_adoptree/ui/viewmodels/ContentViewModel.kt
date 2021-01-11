package nl.rem.kayleigh.project_adoptree.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.Content
import nl.rem.kayleigh.project_adoptree.repository.ContentRepository
import nl.rem.kayleigh.project_adoptree.util.Resource
import retrofit2.Response

class ContentViewModel(private val contentRepository: ContentRepository, context: Context) : ViewModel() {

    private var _contentResponse: MutableLiveData<Resource<List<Content>>> =
            MutableLiveData()
    val contentResponse: LiveData<Resource<List<Content>>>
        get() = _contentResponse

    fun getGreenIdeas() = viewModelScope.launch {

    }
    companion object {
        const val TAG = "TreeViewModel"
    }

    fun getContent() = viewModelScope.launch {
        try {
            _contentResponse.value = handleContentResponse(contentRepository.getAllContent())
        } catch (e: Exception) {
            Log.e(
                    TAG,
                    "${(R.string.error_log)} ${e.message}"
            )
        }
    }

    private fun handleContentResponse(response: Response<List<Content>>) : Resource<List<Content>> {
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