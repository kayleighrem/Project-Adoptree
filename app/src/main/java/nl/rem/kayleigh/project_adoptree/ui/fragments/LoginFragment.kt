package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.User
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.UserViewModel
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class LoginFragment : Fragment(R.layout.fragment_login) {
    lateinit var viewModel: UserViewModel
    lateinit var sessionManager: SessionManager
    lateinit var user: User
    lateinit var mainActivity: MainActivity

    companion object {
        const val TAG = "LoginFragment"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(view.context)
        initializeUI()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initializeUI() {
        btn_forgot_password.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        btn_new_user.setOnClickListener {
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

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
