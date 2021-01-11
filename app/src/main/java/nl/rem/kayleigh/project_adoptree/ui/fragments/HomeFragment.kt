package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_adoption.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.item_tree_card.*
import kotlinx.android.synthetic.main.item_tree_card.view.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.UserAdapter
import nl.rem.kayleigh.project_adoptree.model.TreeObjectCombined
import nl.rem.kayleigh.project_adoptree.ui.activities.AdoptionActivity
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.HomeViewModel
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.UserViewModel
import nl.rem.kayleigh.project_adoptree.util.LinearLayoutManagerWrapper
import nl.rem.kayleigh.project_adoptree.util.Resource
import nl.rem.kayleigh.project_adoptree.util.SessionManager
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var adoptionActivity: AdoptionActivity

    lateinit var mainActivity: MainActivity

    lateinit var userAdapter: UserAdapter
    lateinit var sessionManager: SessionManager
    lateinit var userViewModel: UserViewModel
    lateinit var homeViewModel: HomeViewModel
    var totalCO2reduction: Double = 0.0

    var isLoading = false

    companion object {
        const val TAG = "HomeFragment"
        private const val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(view.context)
        mainActivity = (activity as MainActivity)
//        bottomNavigationView = bottomNavigationView.findViewById(R.id.bottomNavigationView)
//        bottomNavigationView.visibility = View.VISIBLE
        mainActivity.bottomNavigationView.visibility = View.VISIBLE
//        mainActivity = (activity as MainActivity)

        adoptionActivity = AdoptionActivity()

        userViewModel = (activity as MainActivity).userViewModel
        homeViewModel = HomeViewModel(mainActivity, requireContext())

        initializeUI(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("SetTextI18n")
    private fun initializeUI(view: View) {
        mainActivity.userAdapter.mainActivity = mainActivity

        if (sessionManager.isLogin()) {
            rl_home_not_logged_in.visibility = View.GONE
            ll_home_logged_in_no_trees.visibility = View.GONE

            userViewModel.getLoggedInUser(sessionManager.getUserDetails())
            homeViewModel.getTrees(sessionManager.getUserDetails().accessToken)

            homeViewModel.trees.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                    is Resource.Success -> {
                        hideProgressBar()
                        try {
                            rl_home_not_logged_in.visibility = View.GONE
                            mainActivity.userAdapter.differ.submitList(response.data)
                            try {
                                homeViewModel.trees.value?.data?.forEach {
                                    homeViewModel.getCO2ReducePerTree(
                                        sessionManager.getUserDetails().accessToken,
                                        it.id!!
                                    )
                                    homeViewModel.co2reduction
                                    println("values co2reduction? " + homeViewModel.co2reduction)
//                                    totalCO2reduction.plus(homeViewModel.co2reduction.value)
                                    println("totalCO2reduction " + totalCO2reduction)
                                }
                            } catch (e: Exception) { // Catch try when resource = success
                                Log.e(TAG, "${getString(R.string.error_log)} ${e.message}")
                            }

                            if (homeViewModel.trees.value != null) { // if there are trees
                                rl_home_logged_in.visibility = View.VISIBLE
                                ll_home_logged_in_no_trees.visibility = View.GONE
                                setUpRecyclerView()
                            } else if (homeViewModel.trees.value == null) { // if user has no trees
                                rl_home_not_logged_in.visibility = View.GONE
                                rl_home_logged_in.visibility = View.GONE
                                ll_home_logged_in_no_trees.visibility = View.VISIBLE
                            }
                        } catch (e: Exception) { // Catch try when resource = success
                            Log.e(TAG, "${getString(R.string.error_log)} ${e.message}")
                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                        rl_home_not_logged_in.visibility = View.GONE
                        rl_home_logged_in.visibility = View.GONE
                        ll_home_logged_in_no_trees.visibility = View.VISIBLE
                        Toast.makeText(
                            requireContext(),
                            R.string.error_log,
                            Toast.LENGTH_LONG
                        ).show()
                        response.message?.let { message ->
                            Log.e(TAG, "${getString(R.string.error_log)} $message")
                        }
                    }
                }
            })

//            val listItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_tree_card, viewgroup, false)

//            total_co2reduction_value

            mainActivity.userAdapter.setOnPersonalizeButtonClickListener { tree, i ->
                val bundle = Bundle().apply {
                    putSerializable("tree", tree)
                }
                try {
                    view.findNavController().navigate(
                            R.id.action_homeFragment_to_personalizeTreeActivity,
                            bundle
                    )
                } catch (e: Exception) {
                    println("error nav = " + e.message)
                }
            }



            mainActivity.userAdapter.setOnExpandButtonClickListener { tree, i ->
//                println("treeobject combined" + treeObjectCombinedList)
//                userAdapter.treeObjectCombined = treeObjectCombined
//                homeViewModel.getTelemetryByTreeId(sessionManager.getUserDetails().accessToken, tree.id!!.toInt())
//                homeViewModel.getCO2ReducePerTree(sessionManager.getUserDetails().accessToken, tree.id!!)
//                val telemetry = homeViewModel.telemetries.value?.data
//                if (telemetry != null) {
//                    userAdapter.thisTelemetry(telemetry)
//                    userAdapter.telemetry = telemetry
//                    tv_co2reduction_value.text = "${homeViewModel.co2reduction.toString()} g"
//                }
                userAdapter.notifyItemChanged(i)
            }
            view.btn_home_logged_in_adopt_more_trees.setOnClickListener{
                try {
                    view.findNavController().navigate(R.id.action_homeFragment_to_adoptionFragment)
                } catch (e: Exception) {}

            }

            view.btn_logged_in_adopt_more.setOnClickListener {
                view.findNavController().navigate(R.id.action_homeFragment_to_adoptionFragment)
//                mainActivity.navigateToFragment(mainActivity.adoptionFragment)
            }
        } else if (!sessionManager.isLogin()) { // if not logged in
            rl_home_not_logged_in.visibility = View.VISIBLE
            rl_home_logged_in.visibility = View.GONE
            ll_home_logged_in_no_trees.visibility = View.GONE

            btn_guest_adopt_now.setOnClickListener {
                mainActivity.navigateToFragment(mainActivity.adoptionFragment)
            }
            btn_guest_start_adopt_now.setOnClickListener {
                mainActivity.navigateToFragment(mainActivity.adoptionFragment)
            }
        }
    }

    private fun setUpRecyclerView() {
        println("total : " + totalCO2reduction)
        userAdapter = (activity as MainActivity).userAdapter
        rv_home_tree_items.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManagerWrapper(activity)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    getLocation()
            }
        }
    }

    fun hideProgressBar() {
        pb_paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    fun showProgressBar() {
        pb_paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }
}