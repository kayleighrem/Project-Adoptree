package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import android.preference.PreferenceActivity
import android.preference.PreferenceFragment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.rl_profile_logged_in
import kotlinx.android.synthetic.main.fragment_settings.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.repository.UserRepository
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.AdoptionViewModel
import nl.rem.kayleigh.project_adoptree.util.SessionManager
import java.lang.Exception

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
        val navController: NavController? = this.activity?.let { Navigation.findNavController(it, R.id.navHostFragment) }

        initializeUI()

        println("printing some values: ")
        println("activity = " + this.activity)
        println("context = "+this.context)
        println("navigation = " + navController)

        if (!sessionManager.isLogin()) { // if not logged in, don't show user details
            ll_settings_not_logged_in.visibility = View.VISIBLE
            ll_settings_logged_in.visibility = View.GONE
        } else if (sessionManager.isLogin()) { // if logged in, show user
            ll_settings_logged_in.visibility = View.VISIBLE
            ll_settings_not_logged_in.visibility = View.GONE
        }
    }

    private fun initializeUI() {
        ll_logout.setOnClickListener {
            // TODO: log out user thru call
//                userRepository.logout(this.sessionManager.getUserDetails())
            try {
                sessionManager.clearUserDetails()
                findNavController().navigate(R.id.action_settingsFragment_to_splashFragment)
            } catch (e: Exception) {
                Log.e(TAG, "${context?.getString(R.string.error_log)} ${e.message}")
            }
        }


        action_settings_logout.setOnClickListener {
            try {
                sessionManager.clearUserDetails()
                findNavController().navigate(R.id.action_settingsFragment_to_splashFragment)
            } catch (e: Exception) {
                Log.e(TAG, "${context?.getString(R.string.error_log)} ${e.message}")
            }
        }
        action_settings_language.setOnClickListener {  }
        action_settings_contract_information.setOnClickListener {  }
        action_settings_privacy_policy.setOnClickListener {  }
        action_settings_about_app.setOnClickListener {  }
        action_settings_share_app.setOnClickListener {  }
        action_settings_rate_app.setOnClickListener {  }
        action_settings_contact.setOnClickListener {  }
    }
}