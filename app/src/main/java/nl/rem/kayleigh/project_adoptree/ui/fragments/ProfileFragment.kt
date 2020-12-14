package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    lateinit var mainActivity: MainActivity
    companion object {
        const val TAG = "ProfileFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainActivity = MainActivity()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }
}