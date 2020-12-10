package nl.rem.kayleigh.project_adoptree.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.android.synthetic.main.item_adoption_tree_card.*
import kotlinx.coroutines.launch
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.repository.TreeRepository
import nl.rem.kayleigh.project_adoptree.util.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TreeViewModel(private val treeRepository: TreeRepository, val context: Context) : ViewModel() {
//    private var treeResponse: Tree? = null
//    private val _trees: MutableLiveData<Resource<Result>> = MutableLiveData()
//    val trees: MutableLiveData<Resource<Result>>
//        get() = _trees
//
//    companion object {
//        const val TAG = "TreeViewModel"
//    }
//
//    fun getAllTrees() = viewModelScope.launch{
//        try {
//            _trees.postValue(Resource.Loading())
////            val response = nextId?.let { treeRepository.getAllTrees() }
////            _trees.postValue(response?.let { handleTreessResponse(it) })
//        } catch (e: Exception) {
//            _trees.postValue(Resource.Error(context.getString(R.string.connection_error)))
//            Log.e(TAG, "${context.getString(R.string.error_log)} ${e.message}")
//        }
//
//
//        val alltrees: Call<List<Tree>> = treeRepository.getAllTrees()
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
//
//    private fun handleTreesResponse(response: Response<Tree>): Resource<Tree> {
//        if (response.isSuccessful) {
//            response.body()?.let { resultResponse ->
//                if (treeResponse == null) {
//                    treeResponse = resultResponse
//                } else {
////                    val oldArticles = treeResponse?.Results
////                    val newArticles = resultResponse.Results
////                    oldArticles?.addAll(newArticles)
//                }
//                return Resource.Success(treeResponse ?: resultResponse)
//            }
//        }
//        return Resource.Error(response.message())
//    }

}