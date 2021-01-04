package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import android.preference.PreferenceActivity
import android.preference.PreferenceFragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.rl_profile_logged_in
import kotlinx.android.synthetic.main.fragment_settings.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.repository.UserRepository
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    lateinit var sessionManager: SessionManager
    lateinit var userRepository: UserRepository

    companion object {
        const val TAG = "SettingsFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(view.context)
        userRepository = UserRepository()

        println("printing some values: ")
        println("activity = " + this.activity)
        println("context = "+this.context)

        if (!sessionManager.isLogin()) { // if not logged in, don't show user details
            ll_settings_not_logged_in.visibility = View.VISIBLE
            ll_settings_logged_in.visibility = View.GONE
        } else if (sessionManager.isLogin()) { // if logged in, show user
            ll_settings_logged_in.visibility = View.VISIBLE
            ll_settings_not_logged_in.visibility = View.GONE

            ll_logout.setOnClickListener {
                // TODO: log out user thru call
//                userRepository.logout(this.sessionManager.getUserDetails())
                sessionManager.clearUserDetails()
                findNavController().navigate(R.id.action_settingsFragment_to_splashFragment)
//                findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
            }
        }
    }
}