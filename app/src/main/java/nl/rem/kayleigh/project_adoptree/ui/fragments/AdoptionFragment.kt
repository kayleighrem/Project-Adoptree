package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_adoption.*
import kotlinx.android.synthetic.main.fragment_adoption.view.*
import kotlinx.android.synthetic.main.fragment_intro_page3.view.*
import kotlinx.android.synthetic.main.item_adoption_tree_card.view.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.AdoptionAdapter
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.ui.activities.HomeActivity
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.AdoptionViewModel
import nl.rem.kayleigh.project_adoptree.util.LinearLayoutManagerWrapper

class AdoptionFragment : Fragment(R.layout.fragment_adoption) {
    private lateinit var adoptionAdapter: AdoptionAdapter
    lateinit var adoptionViewModel: AdoptionViewModel
    var treeList: MutableList<Tree> = ArrayList()
    var isLoading = false
    var isScrolling = false

    companion object {
        const val TAG = "AdoptionFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adoptionViewModel = (activity as HomeActivity).adoptionViewModel
        println("view 1 : " + view)
        view.tv_number_results.text = "test"

        setUpRecyclerView(view)
        initializeUI()

        adoptionViewModel.treeResponse.observe(viewLifecycleOwner, Observer { response ->
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
        sr_adoptionLayout.setOnRefreshListener {
            adoptionViewModel.gettrees()
            sr_adoptionLayout.isRefreshing = false
        }
        if (adoptionViewModel.trees.value == null) {
            adoptionViewModel.gettrees()
        }
        else {
            //TODO else
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

    private fun setUpRecyclerView(view: View) {
        println("setup recyclerview")
        adoptionAdapter = AdoptionAdapter()
        adoptionAdapter.test()
        rv_adoptionRecyclerView.apply {
            adapter = adoptionAdapter
            layoutManager = LinearLayoutManagerWrapper(activity)
            addOnScrollListener(this@AdoptionFragment.scrollListener)
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val totalItemCount = layoutManager.itemCount;
            val lastVisibleItem = layoutManager.findLastVisibleItemPosition();
//            if (!isLoading && isScrolling && totalItemCount <= (lastVisibleItem + 1)) {
//                adoptionViewModel.getNextArticles(authToken)
//                isScrolling = false
//            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        println("onviewcreated?")
        val view = inflater.inflate(R.layout.fragment_adoption, container, false)
        println("view 2 : " + view.toString())
        view.tv_number_results.text = "hallo"

//        view.btn_next_step.setOnClickListener {
//            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
//        }
//
//        view.btn_adopt.setOnClickListener{
//            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
//        }
//
//        view.btn_info.setOnClickListener{
//            findNavController().navigate(R.id.action_adoptionFragment_to_adoptionTreeInfoActivity)
//        }
        return view
    }
}