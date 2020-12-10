package nl.rem.kayleigh.project_adoptree.ui.activities

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_adoption.*
import kotlinx.android.synthetic.main.item_adoption_tree_card.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.AdoptionAdapter
import nl.rem.kayleigh.project_adoptree.adapters.TreeAdapter
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.repository.TreeRepository
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.AdoptionViewModel
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.UserViewModel
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.factory.AdoptionViewModelFactory
import nl.rem.kayleigh.project_adoptree.util.LinearLayoutManagerWrapper
import nl.rem.kayleigh.project_adoptree.util.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdoptionActivity : AppCompatActivity() {
    lateinit var adoptionViewModel: AdoptionViewModel
    lateinit var adoptionViewModelFactory: AdoptionViewModelFactory
    private var trees = mutableListOf<Tree>()
    private lateinit var treeRepository: TreeRepository
    lateinit var userViewModel: UserViewModel
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adoption)

        initializeViewModels()
//        treeRepository = TreeRepository()
//        trees = treeRepository.getAllTrees()
//        if (trees == null) {
//            println("empty")
//        }
//        else {
//            println(trees)
//        }
//        rv_adoptionRecyclerView.layoutManager = LinearLayoutManager(this)
//        rv_adoptionRecyclerView.adapter = AdoptionAdapter(trees)
    }

    fun initializeViewModels() {
        treeRepository = TreeRepository()
        adoptionViewModel = ViewModelProvider(this, adoptionViewModelFactory).get(AdoptionViewModel::class.java)
    }
}