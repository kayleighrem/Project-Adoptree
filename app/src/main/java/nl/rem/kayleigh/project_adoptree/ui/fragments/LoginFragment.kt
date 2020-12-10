package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_login.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.User
import nl.rem.kayleigh.project_adoptree.ui.activities.HomeActivity
import nl.rem.kayleigh.project_adoptree.ui.activities.LoginActivity
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.UserViewModel
import nl.rem.kayleigh.project_adoptree.util.Resource
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class LoginFragment : Fragment(R.layout.fragment_login) {
    lateinit var viewModel: UserViewModel
    lateinit var sessionManager: SessionManager
    lateinit var user: User

    companion object {
        const val TAG = "LoginFragment"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel = (activity as HomeActivity).userViewModel

        sessionManager = SessionManager(view.context)
        initializeUI()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initializeUI() {
        btn_forgot_password.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        btn_new_user.setOnClickListener {
            navigateToFragment("AdoptionFragment")
            findNavController().navigate(R.id.action_loginFragment_to_adoptionFragment)
        }

        btn_login.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment) // DUMMY ACTION
            // TODO: login button
//            val username = username.text.toString().trim()
//            val uPassword = password.text.toString().trim()
//            if (isPasswordValid(uPassword)) {
//                user = User(username, uPassword)
//                loading.visibility = View.VISIBLE
//                viewModel.login(user)
//            } else {
//                password.error = getString(R.string.error_password_length)
//            }
        }

        btn_not_now.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

        }
    }

    private fun navigateToFragment(fragmentname: String) {
        val fragment =
                when (fragmentname) {
                    "LoginFragment" -> R.id.loginFragment
                    "HomeFragment" -> R.id.homeFragment
                    "AdoptionFragment" -> R.id.adoptionFragment
                    else -> {
                        R.id.loginFragment
                    }
                }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
