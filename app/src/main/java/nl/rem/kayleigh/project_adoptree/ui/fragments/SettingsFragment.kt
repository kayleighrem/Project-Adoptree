package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import android.preference.PreferenceFragment
import androidx.fragment.app.Fragment
import nl.rem.kayleigh.project_adoptree.R

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        class MainPreferenceFragment : PreferenceFragment() {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                addPreferencesFromResource(R.xml.root_preferences)
            }
        }
    }
}

//    class SettingsActivity : AppCompatActivity() {
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//
//            if (fragmentManager.findFragmentById(android.R.id.content) == null) {
//                fragmentManager.beginTransaction()
//                    .add(android.R.id.content, SettingsFragment()).commit()
//            }
//        }
//    }
//
//
//        class SettingsFragment : PreferenceFragment() {
//
//            companion object {
//                const val TAG = "SettingsFragment"
//            }
//            override fun onCreate(savedInstanceState: Bundle?) {
//                super.onCreate(savedInstanceState)
//                addPreferencesFromResource(R.xml.root_preferences)
//            }
//        }
//
