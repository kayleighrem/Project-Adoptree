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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_adoption.*
import kotlinx.android.synthetic.main.fragment_adoption.view.*
import kotlinx.android.synthetic.main.fragment_intro_page3.view.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.item_adoption_tree_card.*
import kotlinx.android.synthetic.main.item_adoption_tree_card.view.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.AdoptionAdapter
import nl.rem.kayleigh.project_adoptree.model.Product
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.AdoptionViewModel
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.OrderViewModel
import nl.rem.kayleigh.project_adoptree.util.LinearLayoutManagerWrapper
import nl.rem.kayleigh.project_adoptree.util.Resource
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class AdoptionFragment : Fragment(R.layout.fragment_adoption) {
    private lateinit var adoptionAdapter: AdoptionAdapter
    lateinit var mainActivity: MainActivity
    lateinit var orderViewModel: OrderViewModel
    lateinit var adoptionViewModel: AdoptionViewModel
    private lateinit var sessionManager: SessionManager
    var productList: List<Product> = ArrayList()
    private var authToken: String? = null
    lateinit var bottomNavigationView: BottomNavigationView
    var isLoading = false
    var isScrolling = false

    companion object {
        const val TAG = "AdoptionFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderViewModel = (activity as MainActivity).orderViewModel
        adoptionViewModel = (activity as MainActivity).adoptionViewModel
        sessionManager = SessionManager(view.context)

        mainActivity = (activity as MainActivity)

        this.bottomNavigationView = (activity as MainActivity).bottomNavigationView
        bottomNavigationView = bottomNavigationView.findViewById(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.GONE


        setUpRecyclerView()
        initializeUI()

        adoptionViewModel.trees.observe(viewLifecycleOwner, Observer { response ->
            Log.d("Response trees: ", response.data.toString())
        })

        adoptionViewModel.products.observe(viewLifecycleOwner, Observer { response ->
            Log.d("Response products: ", response.data.toString())
        })

        adoptionViewModel.products.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    if (productList.isEmpty()) {
                        response.data?.let { productResponse ->
                            adoptionAdapter.differ.submitList(productResponse.toList())
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
                        adoptionViewModel.getProducts()
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
        productList == adoptionViewModel.getProducts()
        sr_adoptionLayout.isRefreshing = false

        if (sessionManager.isLogin()) {
//            authToken = sessionManager.getUserDetails().AuthToken.toString()
            authToken = sessionManager.getUserDetails().accessToken.toString()
        }

        if (adoptionViewModel.products.value?.data?.isNullOrEmpty() == true) {
            adoptionViewModel.getProducts()
            if (adoptionViewModel.products.value?.data?.isEmpty() == true) {
                fl_no_trees_available.visibility = View.VISIBLE
            }
        }

        btn_next_step.setOnClickListener {
            mainActivity.navigateToFragment(mainActivity.adoptionOverviewFragment)
//            findNavController().navigate(R.id.action_adoptionFragment_to_adoptionOverviewFragment)
        }

        adoptionAdapter.setOnInfoButtonClickListener { product, i ->
            val bundle = Bundle().apply {
                putSerializable("product", product)
            }
            findNavController().navigate(
                    R.id.action_adoptionFragment_to_adoptionTreeInfoActivity,
                    bundle
            )
        }

        adoptionAdapter.setOnAdoptButtonClickListener { product, i ->
            Toast.makeText(
                        requireContext(),
                        R.string.successfully_added,
                        Toast.LENGTH_SHORT
                ).show()
            orderViewModel.add(product)
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

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val totalItemCount = layoutManager.itemCount;
            val lastVisibleItem = layoutManager.findLastVisibleItemPosition();
            if (!isLoading && isScrolling && totalItemCount <= (lastVisibleItem + 1)) {
//                adoptionViewModel.getProducts(authToken)
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }
}