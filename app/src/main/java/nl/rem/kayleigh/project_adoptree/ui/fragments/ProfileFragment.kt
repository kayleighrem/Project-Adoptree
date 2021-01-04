package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_timeline.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    lateinit var mainActivity: MainActivity
    lateinit var sessionManager: SessionManager
    lateinit var loginFragment: LoginFragment
    lateinit var adoptionFragment: AdoptionFragment

    companion object {
        const val TAG = "ProfileFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(view.context)
        mainActivity = (activity as MainActivity)
        (activity as MainActivity).profileFragment
        loginFragment = (activity as MainActivity).loginFragment
        adoptionFragment = AdoptionFragment()

        if (!sessionManager.isLogin()) { // if not logged in, don't show user details
            rl_profile_not_logged_in.visibility = View.VISIBLE
            rl_profile_logged_in.visibility = View.GONE
        } else if (sessionManager.isLogin()) { // if logged in, show user
            rl_profile_logged_in.visibility = View.VISIBLE
            rl_profile_not_logged_in.visibility = View.GONE
        }
        initializeUI()
    }

    @SuppressLint("ResourceType")
    private fun initializeUI() {
        log_in.setOnClickListener {
            mainActivity.navigateToFragment(R.layout.fragment_profile, loginFragment)
//            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
        adopt_now.setOnClickListener {
//            mainActivity.navigateToFragment(adoptionFragment)
            findNavController().navigate(R.id.action_profileFragment_to_adoptionFragment)
        }
    }

    private fun navigateToSelectedFragment(fragmentName: String) {
        val fragment =
                if (fragmentName == "LoginFragment") R.id.loginFragment else R.id.adoptionFragment

        val isFragmentAlreadyThere =
                NavHostFragment.findNavController(navHostFragment).currentDestination?.label?.equals(
                        fragmentName
                )

        isFragmentAlreadyThere?.let {
            if (!it) {
                NavHostFragment.findNavController(navHostFragment)
                        .navigate(fragment, null)
            } else {
                NavHostFragment.findNavController(navHostFragment)
                        .popBackStack(fragment, true)
            }
        }
    }
}