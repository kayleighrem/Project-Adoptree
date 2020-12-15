package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_timeline.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    lateinit var mainActivity: MainActivity
    lateinit var sessionManager: SessionManager
    companion object {
        const val TAG = "ProfileFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(view.context)

        //         If not logged in, don't show the section
        if (!sessionManager.isLogin()) {
            rl_profile_not_logged_in.visibility = View.VISIBLE
            rl_profile_logged_in.visibility = View.GONE
        } else if (sessionManager.isLogin()) {
            rl_profile_logged_in.visibility = View.VISIBLE
            rl_profile_not_logged_in.visibility = View.GONE
        }
    }
}