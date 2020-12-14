package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.SettingsViewPagerAdapter
import nl.rem.kayleigh.project_adoptree.ui.fragments.screens.SettingsAppFragment
import nl.rem.kayleigh.project_adoptree.ui.fragments.screens.SettingsProfileFragment

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    companion object {
        const val TAG = "SettingsFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


}