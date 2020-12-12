package nl.rem.kayleigh.project_adoptree.ui.viewmodels

import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.AdoptionAdapter
import nl.rem.kayleigh.project_adoptree.api.AdoptreeService
import nl.rem.kayleigh.project_adoptree.api.RetrofitInstance
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.model.TreeResult
import nl.rem.kayleigh.project_adoptree.repository.AdoptionRepository
import nl.rem.kayleigh.project_adoptree.util.Resource
import nl.rem.kayleigh.project_adoptree.R.layout.fragment_adoption
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdoptionViewModel(private val adoptionRepository: AdoptionRepository, val context: Context) : ViewModel() {
    val trees: MutableLiveData<List<TreeResult>> = MutableLiveData()

    private val _treeResponse: MutableLiveData<Resource<Tree>> =
            MutableLiveData()
    val treeResponse: MutableLiveData<Resource<Tree>>
        get() = _treeResponse

    companion object {
        const val TAG = "AdoptionViewModel"
    }

    fun gettrees() {
//        viewModelScope.launch {
//            val response = adoptionRepository.getAllTrees()
//            trees.value = response
//        }
        val alltrees: Call<List<Tree>> = adoptionRepository.getAllTrees()
        alltrees.enqueue(object : Callback<List<Tree>> {
            override fun onFailure(call: Call<List<Tree>>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }

            override fun onResponse(
                    call: Call<List<Tree>>,
                    response: Response<List<Tree>>
            ) {
                var treeslist :List<Tree> = response.body() as List<Tree>
                var tree = arrayOfNulls<String>(treeslist!!.size)
                for(i in treeslist!!.indices) {
                    tree[i] = treeslist!![i]!!.dateSeeded
                }

                var adapter = ArrayAdapter<String>(context, R.layout.item_adoption_tree_card, tree)
//                sr_adoptionLayout.adapter = adapter
            }
        })
    }





//    private fun handleTreeResponse(response: Response<Tree>): Resource<Tree> {
//        if (response.isSuccessful && response.body() != null) {
//            response.body()?.let {
//                return Resource.Success(it)
//            }
//        }
//        return Resource.Error(response.message())
//    }

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