package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_login.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.UserAdapter
import androidx.navigation.fragment.NavHostFragment
import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.model.User
import nl.rem.kayleigh.project_adoptree.repository.UserRepository
import nl.rem.kayleigh.project_adoptree.ui.activities.AdoptionActivity
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.HomeViewModel
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.UserViewModel
import nl.rem.kayleigh.project_adoptree.util.LinearLayoutManagerWrapper
import nl.rem.kayleigh.project_adoptree.util.Resource
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var adoptionActivity: AdoptionActivity

    lateinit var mainActivity: MainActivity

    lateinit var userAdapter: UserAdapter
    lateinit var sessionManager: SessionManager
    lateinit var userViewModel: UserViewModel
    lateinit var homeViewModel: HomeViewModel
    lateinit var bottomNavigationView: BottomNavigationView

    companion object {
        const val TAG = "HomeFragment"
        private const val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    }

//    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var myLongitude: Double? = null
    private var myLatitude: Double? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//
//        val permissionState = ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
//        if (permissionState != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(mainActivity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_PERMISSIONS_REQUEST_CODE)
//        }


        sessionManager = SessionManager(view.context)
        this.bottomNavigationView = (activity as MainActivity).bottomNavigationView
        bottomNavigationView = bottomNavigationView.findViewById(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE
        mainActivity = (activity as MainActivity)

        adoptionActivity = AdoptionActivity()

        userViewModel = (activity as MainActivity).userViewModel
        homeViewModel = HomeViewModel(userRepository = UserRepository(), requireContext())

        initializeUI()
//            homeViewModel.getTrees(sessionManager.getUserDetails().accessToken)
//            println("are there any trees? " + homeViewModel.trees.value)
//            if (homeViewModel.trees.value != null) { // if there are trees
//                println("trees not null?")
//                rl_home_logged_in.visibility = View.VISIBLE
//                ll_home_logged_in_no_trees.visibility = View.GONE
//                setUpRecyclerView()
//            } else if (homeViewModel.trees.value == null) { // if user has no trees
//                println("trees null?")
//                rl_home_not_logged_in.visibility = View.GONE
//                rl_home_logged_in.visibility = View.GONE
//                ll_home_logged_in_no_trees.visibility = View.VISIBLE
//            }
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun initializeUI() {
        println("test ui?")
        if (sessionManager.isLogin()) {
            println("test login? " + sessionManager.isLogin())
            homeViewModel.getTrees(sessionManager.getUserDetails().accessToken)
            println("test trees " + homeViewModel.trees.value?.data)

            homeViewModel.trees.observe(viewLifecycleOwner, Observer { response ->
                println("test response = " + response.data)
                when (response) {
                    is Resource.Success -> {
                        println("response ==== " + response)
                        try {
                            println("response trees??? " + response.data)
                            rl_home_not_logged_in.visibility = View.GONE

//                            homeViewModel.getTrees(sessionManager.getUserDetails().accessToken)
                            println("are there any trees? " + homeViewModel.trees.value)
                            mainActivity.userAdapter.differ.submitList(response.data)
                            if (homeViewModel.trees.value != null) { // if there are trees
                                println("trees not null?")
                                rl_home_logged_in.visibility = View.VISIBLE
                                ll_home_logged_in_no_trees.visibility = View.GONE
                                    setUpRecyclerView()
                            } else if (homeViewModel.trees.value == null) { // if user has no trees
                                println("trees null?")
                                rl_home_not_logged_in.visibility = View.GONE
                                rl_home_logged_in.visibility = View.GONE
                                ll_home_logged_in_no_trees.visibility = View.VISIBLE
                            }
//                            setUpRecyclerView()
                        } catch (e: Exception) {
                            Log.e(TAG, "${getString(R.string.error_log)} ${e.message}")
                        }
//                        setUpRecyclerView()
                    }
                    is Resource.Error -> {
                        println("response = " + response)
                        Toast.makeText(
                                requireContext(),
                                R.string.wrong_credentials,
                                Toast.LENGTH_LONG
                        ).show()
                        response.message?.let { message ->
                            Log.e(TAG, "${getString(R.string.error_log)} $message")
                        }
                    }
                }
            })

            mainActivity.userAdapter.setOnPersonalizeButtonClickListener { tree, i ->
                val bundle = Bundle().apply {
                    putSerializable("tree", tree)
                }
                this.findNavController().navigate(
                    R.id.action_homeFragment_to_personalizeTreeActivity,
                    bundle
                )
            }

            btn_logged_in_adopt_more.setOnClickListener {
                mainActivity.navigateToFragment(mainActivity.adoptionFragment)
//                findNavController().navigate(R.id.action_homeFragment_to_adoptionFragment)
            }
        } else if (!sessionManager.isLogin()) { // if not logged in
            rl_home_not_logged_in.visibility = View.VISIBLE
            rl_home_logged_in.visibility = View.GONE
            ll_home_logged_in_no_trees.visibility = View.GONE

            btn_guest_adopt_now.setOnClickListener {
                mainActivity.navigateToFragment(mainActivity.adoptionFragment)
//                findNavController().navigate(R.id.action_homeFragment_to_adoptionFragment)
            }
            btn_guest_start_adopt_now.setOnClickListener {
                mainActivity.navigateToFragment(mainActivity.adoptionFragment)
//                findNavController().navigate(R.id.action_homeFragment_to_adoptionFragment)
            }
        }
    }

    private fun setUpRecyclerView() {
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
}