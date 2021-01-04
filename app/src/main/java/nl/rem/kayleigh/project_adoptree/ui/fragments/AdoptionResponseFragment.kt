package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_adoption_response.*
import nl.rem.kayleigh.project_adoptree.R

class AdoptionResponseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InitializeUI()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adoption_response, container, false)
    }

    private fun InitializeUI() {
        btn_monitor.setOnClickListener {
            findNavController().navigate(R.id.action_adoptionResponseFragment_to_homeFragment)
        }
    }
}