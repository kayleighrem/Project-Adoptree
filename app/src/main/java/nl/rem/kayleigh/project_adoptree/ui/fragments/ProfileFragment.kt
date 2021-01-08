package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_profile.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.User
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.UserViewModel
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    lateinit var mainActivity: MainActivity
    lateinit var sessionManager: SessionManager
    lateinit var loginFragment: LoginFragment
    lateinit var userViewModel: UserViewModel
    lateinit var adoptionFragment: AdoptionFragment

    companion object {
        const val TAG = "ProfileFragment"
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(view.context)
        mainActivity = (activity as MainActivity)
        (activity as MainActivity).profileFragment
        userViewModel = (activity as MainActivity).userViewModel
        loginFragment = (activity as MainActivity).loginFragment
        adoptionFragment = AdoptionFragment()

        println("session? " + sessionManager.isLogin())
        initializeUI()

        if (!sessionManager.isLogin()) { // if not logged in, don't show user details
            ll_profile_not_logged_in.visibility = View.VISIBLE
            rl_profile_logged_in.visibility = View.GONE
        } else if (sessionManager.isLogin()) { // if logged in, show user
            rl_profile_logged_in.visibility = View.VISIBLE
            ll_profile_not_logged_in.visibility = View.GONE

        }
    }

    @SuppressLint("SetTextI18n")
    private fun initializeUI() {
        if (sessionManager.isLogin()) {
            // TODO
                val user: User = userViewModel.loggedinUserResponse.value!!.data!!
            tv_username.text = "Hi ${user.firstname} !"
            your_email.text = user.email

        } else {
            ll_login.setOnClickListener {
                mainActivity.navigateToFragment(loginFragment)
            }

            ll_adopt.setOnClickListener {
                println("action clicked ")
//                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                mainActivity.navigateToFragment(adoptionFragment)
            }
        }
    }
}