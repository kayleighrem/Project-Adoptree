package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.loading
import kotlinx.android.synthetic.main.fragment_sign_up.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.User
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.UserViewModel
import nl.rem.kayleigh.project_adoptree.util.Resource

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    lateinit var viewModel: UserViewModel
    lateinit var user: User

    companion object {
        const val TAG = "SignUpFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        viewModel = (activity as HomeActivity).userViewModel
//        initializeUI()
//
//        viewModel.signUpResponse.observe(viewLifecycleOwner, Observer { response ->
//            loading.visibility = View.GONE
//            when (response) {
//                is Resource.Success -> {
//                    confirmPasswordText.onEditorAction(EditorInfo.IME_ACTION_DONE)
//                    findNavController().navigate(
//                        R.id.action_signupFragment_to_loginFragment
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
//        signUp.setOnClickListener {
//            val username = usernameText.text.toString().trim()
//            val password = passwordText.text.toString().trim()
//            val confirmPassword = confirmPasswordText.text.toString().trim()
//            if (isPasswordValid(password) && isPasswordMatching(password, confirmPassword)) {
//                user = User(username, password)
//                loading.visibility = View.VISIBLE
//                viewModel.createUser(user)
//            } else if (!isPasswordValid(password)) {
//                passwordText.error = getString(R.string.error_password_length)
//            } else {
//                confirmPasswordText.error = getString(R.string.error_password_no_match)
//            }
//        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    private fun isPasswordMatching(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }
}