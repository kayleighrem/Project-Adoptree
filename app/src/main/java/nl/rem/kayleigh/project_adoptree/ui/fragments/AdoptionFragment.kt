package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import android.util.Log
import android.util.Log.INFO
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_adoption.*
import kotlinx.android.synthetic.main.fragment_adoption.view.*
import kotlinx.android.synthetic.main.fragment_intro_page3.view.*
import kotlinx.android.synthetic.main.item_adoption_tree_card.view.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.AdoptionAdapter
import nl.rem.kayleigh.project_adoptree.adapters.TreeAdapter
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.ui.activities.AdoptionActivity
import nl.rem.kayleigh.project_adoptree.ui.activities.HomeActivity
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.AdoptionViewModel
import nl.rem.kayleigh.project_adoptree.util.LinearLayoutManagerWrapper
import nl.rem.kayleigh.project_adoptree.util.Resource
import okhttp3.internal.platform.Platform.Companion.INFO
import java.util.logging.Level.INFO

class AdoptionFragment : Fragment(R.layout.fragment_adoption) {
    lateinit var adoptionAdapter: AdoptionAdapter
    lateinit var adoptionViewModel: AdoptionViewModel
    var treeList: MutableList<Tree> = ArrayList()

    companion object {
        const val TAG = "AdoptionFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adoptionViewModel = (activity as HomeActivity).adoptionViewModel

        view.number_results.text = "test"

        setUpRecyclerView()
        initializeUI()

        println("test? ")
        adoptionViewModel.treeResponse.observe(viewLifecycleOwner, Observer { response ->
            println("response? ")
            Log.d("Response ", response.data.toString())
        })

//        adoptionViewModel.treeResponse.observe(viewLifecycleOwner, Observer { response ->
//            println("test when ")
//            when (response) {
//                is Resource.Success -> {
//                    println("resourche success")
//                    if (treeList.isEmpty()) {
//                        println("empty treelist ")
//                        response.data?.let { treeResponse ->
//                            adoptionAdapter.differ.submitList(treeResponse.dateSeeded.toString())
//                        }
//                    } else {
//                        println("resouce else")
////                        adoptionViewModel.handleTreeResponse(null)
////                        adoptionAdapter.differ.submitList(adoptionViewModel.treeResponse.toList())
//                    }
//                }
//                is Resource.Error<*> -> {
//                    println("resource error")
//                    Snackbar.make(
//                        rv_adoptionRecyclerView,
//                        response.message!!,
//                        Snackbar.LENGTH_INDEFINITE
//                    ).setAction(getString(R.string.retry)) {
//                        adoptionViewModel.gettrees()
//                    }.show()
//                }
//            }
//        })
    }

    private fun initializeUI() {
        println("test initualise ui")
        sr_adoptionLayout.setOnRefreshListener {
            println("test swlayout")
//            adoptionViewModel.trees.value?.data?.Results?.clear()
            adoptionViewModel.gettrees()
            sr_adoptionLayout.isRefreshing = false
        }
        println("test again srlayout")

        if (adoptionViewModel.trees.value == null) {
            println("empty? 43")
            adoptionViewModel.gettrees()
            println("test get " + adoptionViewModel.gettrees())
        }
        else {
            println("empty? 47")
        }

        adoptionAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("tree", it)
            }
            println("test click")
            findNavController().navigate(
                    R.id.action_adoptionFragment_to_adoptionTreeInfoActivity,
                    bundle
            )
        }
    }

    private fun setUpRecyclerView() {
        adoptionAdapter = AdoptionAdapter()
        rv_adoptionRecyclerView.apply {
            adapter = adoptionAdapter
            layoutManager = LinearLayoutManagerWrapper(activity)
        }
    }

//    override fun onCreateView(
//            inflater: LayoutInflater, container: ViewGroup?,
//            savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_adoption, container, false)
//
//        view.number_results.text = "test"
//
////        view.btn_next_step.setOnClickListener {
////            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
////        }
////
////        view.btn_adopt.setOnClickListener{
////            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
////        }
////
////        view.btn_info.setOnClickListener{
////            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
////        }
//        return view
//    }
}