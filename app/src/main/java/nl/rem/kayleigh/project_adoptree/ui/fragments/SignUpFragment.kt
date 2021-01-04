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
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.User
import nl.rem.kayleigh.project_adoptree.repository.UserRepository
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.UserViewModel
import nl.rem.kayleigh.project_adoptree.util.Resource
import nl.rem.kayleigh.project_adoptree.util.SessionManager
import java.lang.Exception

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    lateinit var viewModel: UserViewModel
    lateinit var sessionManager: SessionManager
    lateinit var user: User
    lateinit var userRepository: UserRepository

    companion object {
        const val TAG = "SignUpFragment"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(view.context)
        viewModel = (activity as MainActivity).userViewModel
        userRepository = UserRepository()
        initializeUI()

        viewModel.signUpResponse.observe(viewLifecycleOwner, Observer { response ->
            loading.visibility = View.GONE
            when (response) {
                is Resource.Success -> {
                    confirmPasswordText.onEditorAction(EditorInfo.IME_ACTION_DONE)
                    findNavController().navigate(
                            R.id.action_signUpFragment_to_adoptionResponseFragment
                    )
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG).show()
                    response.message?.let { message ->
                        Log.e(TAG, "${getString(R.string.error_log)} $message")
                    }
                }
            }
        })

//        viewModel.signUpResponse.observe(viewLifecycleOwner, Observer { response ->
//            // TODO: response is error
//            loading.visibility = View.GONE
//            when (response) {
//                is Resource.Success -> {
//                    confirmPasswordText.onEditorAction(EditorInfo.IME_ACTION_DONE)
//                    findNavController().navigate(
//                        R.id.action_signUpFragment_to_adoptionResponseFragment
//                    )
//                }
//                is Resource.Error -> {
//                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG).show()
//                    response.message?.let { message ->
//                        Log.e(TAG, "${getString(R.string.error_log)} $message")
//                    }
//                }
//            }
//        })
    }

    private fun initializeUI() {
        signUp.setOnClickListener {
            val firstname = et_firstname.text.toString().trim()
            val lastname = et_lastname.text.toString().trim()
            val username = et_username.text.toString().trim()
            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()
            val confirmPassword = confirmPasswordText.text.toString().trim()
            if (isPasswordValid(password) && isPasswordMatching(password, confirmPassword)) {
                user = User(firstname = firstname, lastname = lastname, username = username, email = email, password = password, role = null, createdAt = null, forgetToken = null, id = null, salt = null)
                loading.visibility = View.VISIBLE
                try {
                    viewModel.register(user)
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