package nl.rem.kayleigh.project_adoptree.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.model.TreeResult
import nl.rem.kayleigh.project_adoptree.repository.AdoptionRepository
import nl.rem.kayleigh.project_adoptree.util.Resource
import retrofit2.Response

class AdoptionViewModel(private val adoptionRepository: AdoptionRepository, val context: Context) : ViewModel() {
    val trees: MutableLiveData<List<TreeResult>> = MutableLiveData()
//    private val _treeResponse: MutableLiveData<Resource<Tree>> =
//            MutableLiveData()
//    private var treeResponse: MutableLiveData<Resource<Tree>>
//        get() = _treeResponse

    private val _articles: MutableLiveData<Resource<List<Tree>>> = MutableLiveData()
    val articles: MutableLiveData<Resource<List<Tree>>>
        get() = _articles
    private var articleResponse: List<Tree>? = ArrayList()

    companion object {
        const val TAG = "AdoptionViewModel"
    }

//    fun gettrees() {
//        println("test fun gettrees")
////        viewModelScope.launch {
////            val response = adoptionRepository.getAllTrees()
////            trees.value = response
////        }
//        val alltrees: Call<List<Tree>> = adoptionRepository.getAvailableTrees()
//        println("test all trees: " + alltrees.toString())
//        alltrees.enqueue(object : Callback<List<Tree>> {
//            override fun onFailure(call: Call<List<Tree>>, t: Throwable) {
//                Log.e("ERROR", t.message.toString())
//            }
//
//            override fun onResponse(
//                    call: Call<List<Tree>>,
//                    response: Response<List<Tree>>
//            ) {
//                println("test onresponse? ")
//                var treeslist: List<Tree> = response.body() as List<Tree>
//                var tree = arrayOfNulls<String>(treeslist!!.size)
//                for (i in treeslist!!.indices) {
//                    tree[i] = treeslist!![i]!!.dateSeeded
//                }
//
//                var adapter = ArrayAdapter<String>(context, R.layout.item_adoption_tree_card, tree)
////                .adapter = adapter
//            }
//        })
//    }

    fun getAvailableTrees() = viewModelScope.launch {
        try {
            _articles.postValue(Resource.Loading())
            println("test error? : ")
            val response: Response<List<Tree>> = adoptionRepository.getAvailableTrees()
            _articles.postValue(handleTreeResponse(adoptionRepository.getAvailableTrees()))
        } catch (e: Exception) {
            println("test error 2 : ")
            _articles.postValue(Resource.Error(context.getString(R.string.connection_error)))
            Log.e(TAG, "${context.getString(R.string.error_log)} ${e.message}")
        }
    }

    private fun handleTreeResponse(response: Response<List<Tree>>): Resource<List<Tree>> {
        println("test handle response?")
        if (response.body()?.isEmpty() == false) {
            println("test respons is not empty")
            response.body()?.let { resultResponse ->
                if (articleResponse == null) {
                    articleResponse = resultResponse
                } else {
                    val oldArticles = articleResponse
                    val newArticles = resultResponse
//                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(articleResponse ?: resultResponse)
            }
        }
        println("test else error? ")
        return Resource.Error("response.message()")
    }

//    fun getAvailableTrees() {
//        val alltrees: Call<List<Tree>> = adoptionRepository.getAllTrees()
//        alltrees.enqueue(object : Callback<List<Tree>> {
//            override fun onFailure(call: Call<List<Tree>>, t: Throwable) {
//                Log.e("ERROR", t.message.toString())
//            }
//
//            override fun onResponse(call: Call<List<Tree>>, response: Response<List<Tree>>) {
//                val Trees:List<Tree> = response.body()!!
//                val stringBuilder = StringBuilder()
//                for(tree in Trees) {
//                    stringBuilder.append(tree.dateSeeded)
//                    stringBuilder.append("\n")
//                    stringBuilder.append(tree.forestId)
//                }
//            }
//        })
//    }
}