package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_adoption_overview.*
//import kotlinx.android.synthetic.main.fragment_articles.paginationProgressBar
//import kotlinx.android.synthetic.main.fragment_articles.recycleView
//import kotlinx.android.synthetic.main.fragment_articles.swipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_adoption.*
import kotlinx.android.synthetic.main.fragment_adoption.view.*
import kotlinx.android.synthetic.main.fragment_intro_page3.view.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.item_adoption_tree_card.*
import kotlinx.android.synthetic.main.item_adoption_tree_card.view.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.AdoptionAdapter
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.AdoptionViewModel
import nl.rem.kayleigh.project_adoptree.util.LinearLayoutManagerWrapper
import nl.rem.kayleigh.project_adoptree.util.Resource
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class AdoptionFragment : Fragment(R.layout.fragment_adoption) {
    private lateinit var adoptionAdapter: AdoptionAdapter
    lateinit var adoptionViewModel: AdoptionViewModel
    private lateinit var sessionManager: SessionManager
    var treeList: List<Tree> = ArrayList()
    private var authToken: String? = null
    var isLoading = false
    var isScrolling = false

    companion object {
        const val TAG = "AdoptionFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adoptionViewModel = (activity as MainActivity).adoptionViewModel
        sessionManager = SessionManager(view.context)
        setUpRecyclerView()
        initializeUI()

        adoptionViewModel.trees.observe(viewLifecycleOwner, Observer { response ->
            Log.d("Response ", response.data.toString())
        })

        adoptionViewModel.trees.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    if (treeList.isEmpty()) {
                        response.data?.let { treeResponse ->
//                            treeList.add(treeResponse)
                            adoptionAdapter.differ.submitList(treeResponse.toList())
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

    @SuppressLint("SetTextI18n")
    private fun initializeUI() {
//        sr_adoptionLayout.setOnRefreshListener {
//            this.treeList = adoptionViewModel.trees.value?.data?
           treeList == adoptionViewModel.getAvailableTrees()
            sr_adoptionLayout.isRefreshing = false
//        }

        println("test response trees: " + adoptionViewModel.trees)

        if (adoptionViewModel.trees.value?.data?.isEmpty() == true) {
            println("response is null/empty" + adoptionViewModel.trees)
        } else {
            println("response is not empty? : " + adoptionViewModel.trees)
            println("test data : " + adoptionViewModel.trees.value?.data)
            adoptionViewModel.trees.value?.data?.forEach {
                println("tree item: " + it)
            }
        }

//        if (treeList.count() == 0) {
//            fl_no_trees_available.visibility = View.VISIBLE
//            sr_adoptionLayout.visibility = View.GONE
//        } else { fl_no_trees_available.visibility = View.GONE }

        if (sessionManager.isLogin()) {
            authToken = sessionManager.getUserDetails().AuthToken.toString()
        }


        btn_next_step.setOnClickListener {
            findNavController().navigate(R.id.action_adoptionFragment_to_adoptionOverviewFragment)
        }

//        btn_adopt.setOnClickListener {
//           findNavController().navigate(R.id.action_adoptionOverviewFragment_to_signUpFragment)
//        // TODO add to cart
//        }
//
//        btn_info.setOnClickListener {
//            findNavController().navigate(R.id.action_adoptionFragment_to_adoptionTreeInfoActivity)
//        }


        adoptionAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("tree", it)
            }
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
        adoptionAdapter = AdoptionAdapter()
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