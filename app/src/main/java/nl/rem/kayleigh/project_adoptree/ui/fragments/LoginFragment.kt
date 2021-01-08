package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_login.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.User
import nl.rem.kayleigh.project_adoptree.model.UserLogin
import nl.rem.kayleigh.project_adoptree.repository.UserRepository
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.HomeViewModel
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.UserViewModel
import nl.rem.kayleigh.project_adoptree.util.Resource
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class LoginFragment : Fragment(R.layout.fragment_login) {
    lateinit var homeViewModel: HomeViewModel
    lateinit var viewModel: UserViewModel
    lateinit var userRepository: UserRepository
    lateinit var sessionManager: SessionManager
    lateinit var user: User
    lateinit var userLogin: UserLogin
    lateinit var mainActivity: MainActivity
    lateinit var bottomNavigationView: BottomNavigationView

    companion object {
        const val TAG = "LoginFragment"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userRepository = UserRepository()
        viewModel = UserViewModel(userRepository, requireContext())
        homeViewModel = HomeViewModel(userRepository, requireContext())

        mainActivity = (activity as MainActivity)

        this.bottomNavigationView = (activity as MainActivity).bottomNavigationView
        bottomNavigationView = bottomNavigationView.findViewById(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.GONE

        sessionManager = SessionManager(view.context)
        initializeUI()

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer { response ->
            loading.visibility = View.GONE
            when (response) {
                is Resource.Success -> {
                    et_password.onEditorAction(EditorInfo.IME_ACTION_DONE)
                    try {
                        response.data?.accessToken?.let {
                            sessionManager.createSession(
                                    it,
                                    response.data.refreshToken!!
                            )
                            mainActivity.activateHandler()
                        }

                        mainActivity.navigateToFragment(mainActivity.homeFragment)
//                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        Toast.makeText(
                                requireContext(),
                                "${getString(R.string.test)} ${userLogin.username}",
                                Toast.LENGTH_LONG
                        ).show()
                    } catch (e: Exception) {
                        Log.e(TAG, "${getString(R.string.error_log)} ${e.message}")
                    }
                }
                is Resource.Error -> {
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
            // TODO: login button
            val username = et_username.text.toString().trim()
            val password = et_password.text.toString().trim()
            if (isPasswordValid(password)) {
                userLogin = UserLogin(username, password)
                loading.visibility = View.VISIBLE
                try {
                    viewModel.login(userLogin)
                } catch (e: java.lang.Exception) {
                    Log.e(TAG, "${getString(R.string.error_log)} ${e.message}")
                }
            } else {
                et_password.error = getString(R.string.error_password_length)
            }
        }

        btn_not_now.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
