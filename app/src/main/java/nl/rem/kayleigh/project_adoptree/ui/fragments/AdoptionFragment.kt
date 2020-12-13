package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_adoption_overview.*
//import kotlinx.android.synthetic.main.fragment_articles.paginationProgressBar
//import kotlinx.android.synthetic.main.fragment_articles.recycleView
//import kotlinx.android.synthetic.main.fragment_articles.swipeRefreshLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
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
import nl.rem.kayleigh.project_adoptree.util.Resource
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class AdoptionFragment : Fragment(R.layout.fragment_adoption) {
    private lateinit var adoptionAdapter: AdoptionAdapter
    lateinit var adoptionViewModel: AdoptionViewModel
    private lateinit var sessionManager: SessionManager
    var treeList: MutableList<Tree> = ArrayList()
    private var authToken: String? = null
    var isLoading = false
    var isScrolling = false

//    companion object {
//        const val TAG = "AdoptionFragment"
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adoptionViewModel = (activity as HomeActivity).adoptionViewModel
        println("view 1 : " + view)
        view.tv_number_results.text = "test"
        sessionManager = SessionManager(view.context)
        setUpRecyclerView()
        initializeUI()

        adoptionViewModel.articles.observe(viewLifecycleOwner, Observer { response ->
            Log.d("Response ", response.data.toString())

        })

        adoptionViewModel.articles.observe(viewLifecycleOwner, Observer { response ->
            println("test response: " + response)
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    if (treeList.isEmpty()) {
                        response.data?.let { treeResponse ->
                            treeList.add(treeResponse)
                            adoptionAdapter.differ.submitList(treeList.toList())
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Snackbar.make(
                            rv_adoptionRecyclerView,
                            response.message!!,
                            Snackbar.LENGTH_INDEFINITE
                    ).setAction(getString(R.string.retry)) {
                        adoptionViewModel.getAvailableTrees()
                    }.show()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun initializeUI() {
        println("test ui?")
        sr_adoptionLayout.setOnRefreshListener {
            println("test refresh?")
            treeList.clear()
//            adoptionViewModel.articles.value?.data?.treeList?.clear()
            adoptionViewModel.getAvailableTrees()
            sr_adoptionLayout.isRefreshing = false
        }

        if (sessionManager.isLogin()) {
            println("test islogin fr?")
            authToken = sessionManager.getUserDetails().AuthToken.toString()
        }

        if (treeList.isNullOrEmpty()) {
            println("test list is null?")
            adoptionViewModel.getAvailableTrees()
        }
//        if (adoptionViewModel.articles.value?.data?.treeList.isNullOrEmpty()) {
//            println("test list is nul?")
//            adoptionViewModel.getAvailableTrees()
//            println("test gettrees: " + adoptionViewModel.getAvailableTrees())
//        }

        adoptionAdapter.setOnItemClickListener {
            println("test onitemclicklistenere?")
            val bundle = Bundle().apply {
                putSerializable("tree", it)
            }
            println("test click")
            findNavController().navigate(
                    R.id.action_adoptionFragment_to_adoptionTreeInfoActivity,
                    bundle
            )
        }

        adoptionAdapter.setOnLikedButtonClickListener { tree, i ->
            if (tree.assignedTree == null) {
//                savedArticleViewModel.likeAnArticle(tree.id)
                Toast.makeText(
                        requireContext(),
                        R.string.test,
                        Toast.LENGTH_SHORT
                ).show()
//                adoptionAdapter.differ.currentList[i].assignedTree = true
//                adoptionAdapter.notifyItemChanged(i)
//                savedArticleViewModel.getLikedArticles(authToken!!)
            } else {
//                savedArticleViewModel.unlikeAnArticle(article.Id!!, authToken!!)
                Toast.makeText(
                        requireContext(),
                        R.string.test,
                        Toast.LENGTH_SHORT
                ).show()
//                articleAdapter.differ.currentList[i].IsLiked = false
//                articleAdapter.notifyItemChanged(i)
//                savedArticleViewModel.getLikedArticles(authToken!!)
            }
        }
    }

    private fun setUpRecyclerView() {
        println("setup recyclerview")
        adoptionAdapter = AdoptionAdapter()
        adoptionAdapter.test()
        rv_adoptionRecyclerView.apply {
            println("test apply?")
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

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

//    override fun onCreateView(
//            inflater: LayoutInflater, container: ViewGroup?,
//            savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        println("test onviewcreated?")
//        val view = inflater.inflate(R.layout.fragment_adoption, container, false)
//        println("view 2 : " + view.toString())
//        view.tv_number_results.text = "hallo"
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
////            findNavController().navigate(R.id.action_adoptionFragment_to_adoptionTreeInfoActivity)
////        }
//        return view
//    }
}