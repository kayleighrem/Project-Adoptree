package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.fragment_timeline.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class FeedFragment : Fragment(R.layout.fragment_feed) {
    lateinit var mainActivity: MainActivity
    lateinit var sessionManager: SessionManager
    companion object {
        const val TAG = "FeedFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(view.context)
        mainActivity = MainActivity()

        // If not logged in, don't show the timeline
        if (!sessionManager.isLogin()) {
            btn_add_tour.visibility = View.GONE
        } else if (sessionManager.isLogin()) {
            btn_add_tour.visibility = View.VISIBLE
        }
    }

//    override fun onCreateView(
//            inflater: LayoutInflater, container: ViewGroup?,
//            savedInstanceState: Bundle?
//    ): View? {
//        // If not logged in, don't show the timeline
//        if (!sessionManager.isLogin()) {
//            btn_add_tour.visibility = View.GONE
//        } else if (sessionManager.isLogin()) {
//            btn_add_tour.visibility = View.VISIBLE
//        }
//
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_feed, container, false)
//    }
}