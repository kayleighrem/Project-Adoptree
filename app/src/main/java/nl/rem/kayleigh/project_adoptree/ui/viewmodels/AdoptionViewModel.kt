package nl.rem.kayleigh.project_adoptree.ui.viewmodels

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.compose.ui.viewinterop.viewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.Category
import nl.rem.kayleigh.project_adoptree.model.Order
import nl.rem.kayleigh.project_adoptree.model.Product
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.repository.AdoptionRepository
import nl.rem.kayleigh.project_adoptree.util.Resource
import retrofit2.Response
import java.util.concurrent.Flow

class AdoptionViewModel(private val adoptionRepository: AdoptionRepository, val context: Context) : ViewModel() {
    private val _trees: MutableLiveData<Resource<List<Tree>>> = MutableLiveData()
    val trees: MutableLiveData<Resource<List<Tree>>>
        get() = _trees

    private val _products: MutableLiveData<Resource<List<Product>>> = MutableLiveData()
    val products: MutableLiveData<Resource<List<Product>>>
        get() = _products
    private var productResponse: List<Product>? = ArrayList()

    private val _categories: MutableLiveData<Resource<List<Category>>> = MutableLiveData()
    val categories: MutableLiveData<Resource<List<Category>>> get() = _categories

    companion object {
        const val TAG = "AdoptionViewModel"
    }

    fun getProducts() = viewModelScope.launch {
        try {
            _products.postValue(Resource.Loading())
            val response: Response<List<Product>> = adoptionRepository.getProducts()
            _products.postValue(handleProductResponse(response))
        } catch (e: Exception) {
            _products.postValue(Resource.Error(context.getString(R.string.connection_error)))
            Log.e(TAG, "${context.getString(R.string.error_log)} ${e.message}")
        }
    }

    private fun handleProductResponse(response: Response<List<Product>>): Resource<List<Product>> {
        if (response.body()?.isEmpty() == false) {
            response.body()?.let { resultResponse ->
                if (productResponse == null) {
                    productResponse = resultResponse
                } else {
                    productResponse = resultResponse
                }
                return Resource.Success(productResponse ?: resultResponse)
            }
        }
        return Resource.Error("response.message()")
    }
}