package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import nl.rem.kayleigh.project_adoptree.R

class AdoptionOverviewFragment : Fragment(R.layout.fragment_adoption_overview) {

    companion object {
        const val TAG = "AdoptionOverviewFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val items = listOf("Material", "Design", "Components", "Android")
//        val adapter = ArrayAdapter(requireContext(), R.layout.item_location_list, items)
//        (textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
}