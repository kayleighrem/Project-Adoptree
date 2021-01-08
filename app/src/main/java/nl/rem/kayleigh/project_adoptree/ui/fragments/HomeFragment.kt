package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_login.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.UserAdapter
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(view.context)
        this.bottomNavigationView = (activity as MainActivity).bottomNavigationView
        bottomNavigationView = bottomNavigationView.findViewById(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE
        mainActivity = (activity as MainActivity)

        adoptionActivity = AdoptionActivity()

        userViewModel = (activity as MainActivity).userViewModel
        homeViewModel = HomeViewModel(userRepository = UserRepository(), requireContext())
        initializeUI()

        if (!sessionManager.isLogin()) { // if not logged in
            rl_home_not_logged_in.visibility = View.VISIBLE
            rl_home_logged_in.visibility = View.GONE
            ll_home_logged_in_no_trees.visibility = View.GONE

        } else if (sessionManager.isLogin()) { // if logged in
            rl_home_not_logged_in.visibility = View.GONE
            println("are there any trees? " + homeViewModel.trees.value)
            if (homeViewModel.trees.value != null) { // if there are trees
                rl_home_logged_in.visibility = View.VISIBLE
                ll_home_logged_in_no_trees.visibility = View.GONE
                setUpRecyclerView()
            } else if (homeViewModel.trees.value == null) { // if user has no trees
                rl_home_not_logged_in.visibility = View.GONE
                rl_home_logged_in.visibility = View.GONE
                ll_home_logged_in_no_trees.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun initializeUI() {
        if (sessionManager.isLogin()) {
            homeViewModel.trees.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Success -> {
                        println("response ==== " + response)
                        try {
                            println("response trees??? " + response.data)
//                            response.data?.accessToken?.let {
//                                sessionManager.createSession(
//                                        it,
//                                        response.data.refreshToken!!,
//                                        response.data.userId!!
//                                )
//                                println("is logged in? = " + sessionManager.isLogin())
//                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "${getString(R.string.error_log)} ${e.message}")
                        }
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

            btn_logged_in_adopt_more.setOnClickListener {
                mainActivity.navigateToFragment(mainActivity.adoptionFragment)
//                findNavController().navigate(R.id.action_homeFragment_to_adoptionFragment)
            }
        } else {
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
        userAdapter = UserAdapter()
        rv_home_tree_items.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManagerWrapper(activity)
        }
    }
}