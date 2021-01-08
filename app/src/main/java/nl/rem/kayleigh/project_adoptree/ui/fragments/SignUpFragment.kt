package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.et_password
import kotlinx.android.synthetic.main.fragment_sign_up.et_username
import kotlinx.android.synthetic.main.fragment_sign_up.loading
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.Order
import nl.rem.kayleigh.project_adoptree.model.User
import nl.rem.kayleigh.project_adoptree.model.UserLogin
import nl.rem.kayleigh.project_adoptree.repository.UserRepository
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.UserViewModel
import nl.rem.kayleigh.project_adoptree.util.Resource
import nl.rem.kayleigh.project_adoptree.util.SessionManager
import java.lang.Exception

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    lateinit var userViewModel: UserViewModel
    lateinit var sessionManager: SessionManager
    lateinit var user: User
    lateinit var loginuser: UserLogin
    lateinit var userRepository: UserRepository
    lateinit var mainActivity: MainActivity

    companion object {
        const val TAG = "SignUpFragment"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(view.context)
        mainActivity = (activity as MainActivity)
        userViewModel = (activity as MainActivity).userViewModel
        userRepository = UserRepository()
        initializeUI()

        println("receive value args " + arguments?.classLoader)

        println("receive value of args? " + arguments?.get("order"))

        // Observe sign up
        mainActivity.userViewModel.signUpResponse.observe(viewLifecycleOwner, Observer { response ->
            loading.visibility = View.GONE
            when (response) {
                is Resource.Success -> { // if register response is successfull
                    confirmPasswordText.onEditorAction(EditorInfo.IME_ACTION_DONE)
                    try { // if register is successfull, login the user
                        userViewModel.login(loginuser)
                        userViewModel.loginResponse.observe(viewLifecycleOwner, Observer { loginresponse -> // if register was successfull, login user and create session
                            when (loginresponse) {
                                is Resource.Success -> { // if login response is successfull
                                    try {
                                        loginresponse.data?.accessToken?.let { // create session
                                            sessionManager.createSession(
                                                    it,
                                                    loginresponse.data.refreshToken!!
                                            )
                                        }
                                        // TODO: go to the payment
                                        println("is de order er nog ? " + arguments?.get("order"))
                                        // When the user is successfully logged in, get the order that was made from the args
                                        val order: Order = arguments?.get("order") as Order
                                        println("en nu ook nog? " + order)
                                        mainActivity.activateHandler() // Get the latest user tokens
                                        userViewModel.getLoggedInUser(sessionManager.getUserDetails().accessToken)
                                        try { // Try to create an order with the logged in user id
                                            order.userId = userViewModel.loggedinUserResponse.value?.data?.id
                                            mainActivity.orderViewModel.createOrder(order, mainActivity.userViewModel.loggedinUserResponse.value!!.data!!.id!!, sessionManager.getUserDetails().accessToken)
                                        } catch (e: Exception) { // Catch if the order was not being made

                                        }

                                        // TODO volgens mij kan dit weg :
                                        findNavController().navigate(
                                                R.id.action_signUpFragment_to_adoptionResponseFragment
                                        )
                                    } catch (e: Exception) { // Catch if there was a problem with logging in the user
                                        Log.e(LoginFragment.TAG, "${getString(R.string.error_log)} ${e.message}")
                                    }
                                }
                                is Resource.Error -> { // if login response gives error
                                    Toast.makeText(requireContext(), R.string.wrong_credentials, Toast.LENGTH_LONG).show()
                                    response.message?.let { message ->
                                        Log.e(LoginFragment.TAG, "${getString(R.string.error_log)} $message")
                                    }
                                }
                            }
                        })
                    } catch (e: Exception) {
                        // TODO
                    }
                }
                is Resource.Error -> { // if register response gives an error
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG).show()
                    response.message?.let { message ->
                        Log.e(TAG, "${getString(R.string.error_log)} $message")
                    }
                }
            }
        })
    }

    private fun initializeUI() {
        signUp.setOnClickListener {
            val password = et_password.text.toString().trim()
            val confirmPassword = confirmPasswordText.text.toString().trim()
            if (isPasswordValid(password) && isPasswordMatching(password, confirmPassword)) {
                user = User(
                        firstname = et_firstname.text.toString().trim(),
                        lastname = et_lastname.text.toString().trim(),
                        username = et_username.text.toString().trim(),
                        email = et_email.text.toString().trim(),
                        password = et_password.text.toString().trim(),
                        role = null,
                        createdAt = null,
                        forgetToken = null,
                        id = null,
                        salt = null)
                loginuser = UserLogin(username = et_username.text.toString().trim(), password = et_password.text.toString().trim())
                println("register user : " + user)
                loading.visibility = View.VISIBLE
                try {
                    userViewModel.register(user)
                } catch (e: Exception) {
                    Log.e(e.toString(), "Something went wrong")
                }
            } else if (!isPasswordValid(password)) {
                et_password.error = getString(R.string.error_password_length)
            } else {
                confirmPasswordText.error = getString(R.string.error_password_no_match)
            }
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private fun isPasswordMatching(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }
}