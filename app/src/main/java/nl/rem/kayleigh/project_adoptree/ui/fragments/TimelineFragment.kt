package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_timeline.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class TimelineFragment : Fragment(R.layout.fragment_timeline) {
    lateinit var sessionManager: SessionManager

    companion object {
        const val TAG = "TimelineFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(view.context)

        //         If not logged in, don't show the timeline
        if (!sessionManager.isLogin()) {
            fl_timeline_not_logged_in.visibility = View.VISIBLE
            fl_timeline_logged_in.visibility = View.GONE
        } else if (sessionManager.isLogin()) {
            fl_timeline_logged_in.visibility = View.VISIBLE
            fl_timeline_not_logged_in.visibility = View.GONE
        }
    }
}