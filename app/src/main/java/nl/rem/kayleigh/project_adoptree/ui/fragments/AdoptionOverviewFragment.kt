package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_adoption.view.*
import kotlinx.android.synthetic.main.fragment_adoption_overview.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.ui.activities.HomeActivity
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class AdoptionOverviewFragment : Fragment(R.layout.fragment_adoption_overview) {
    private lateinit var sessionManager: SessionManager

    companion object {
        const val TAG = "AdoptionOverviewFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(view.context)

        btn_pay.setOnClickListener {
            findNavController().navigate(R.id.action_adoptionOverviewFragment_to_signUpFragment)
        }
    }
}