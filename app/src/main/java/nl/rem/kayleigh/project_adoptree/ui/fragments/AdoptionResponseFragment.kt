package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_adoption_response.*
import kotlinx.android.synthetic.main.fragment_adoption_response.view.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity

class AdoptionResponseFragment : Fragment(R.layout.fragment_adoption_response) {

    lateinit var mainActivity: MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adoption_response, container, false)
    }

    private fun initializeUI(view: View) {
       view.btn_monitor.setOnClickListener {
           mainActivity.navigateToFragment(mainActivity.homeFragment)
//            view.findNavController().navigate(R.id.action_adoptionResponseFragment_to_homeFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = (activity as MainActivity)
        initializeUI(view)
    }
}