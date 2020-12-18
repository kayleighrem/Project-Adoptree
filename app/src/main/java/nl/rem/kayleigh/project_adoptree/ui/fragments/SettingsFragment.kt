package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import android.preference.PreferenceActivity
import android.preference.PreferenceFragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import nl.rem.kayleigh.project_adoptree.R

//class SettingsFragment : Fragment(R.layout.fragment_settings) {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        class MainPreferenceFragment : PreferenceFragment() {
//            override fun onCreate(savedInstanceState: Bundle?) {
//                super.onCreate(savedInstanceState)
//                addPreferencesFromResource(R.xml.root_preferences)
//            }
//        }
//    }
//}
class SettingsFragment : Fragment(R.layout.fragment_settings) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}


//class SettingsFragment : AppCompatActivity(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_settings)
//
//        if (savedInstanceState == null ){
//            supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.content_preference, MainPreferenceFragment())
//                .commit()
//        } else {
//            title = savedInstanceState.getCharSequence(TAG_TITLE)
//        }
//
//        supportFragmentManager.addFragmentOnAttachListener{
//            if (supportFragmentManager.backStackEntryCount == 0) {
//                setTitle(R.string.title_settings)
//            }
//        }
//
//        setUpToolbar()
//    }
//
//    private fun setUpToolbar() {
//        supportActionBar?.setTitle(R.string.title_settings)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//    }
//
//    class MainPreferenceFragment : PreferenceFragmentCompat() {
//        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
//            setPreferencesFromResource( R.xml.root_preferences, rootKey )
//        }
//    }
//
//    override fun onPreferenceStartFragment(
//        caller: PreferenceFragmentCompat?,
//        pref: Preference?
//    ): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    companion object {
//        private val TAG_TITLE = SettingsFragment::getTitle.toString()
//    }
//}

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
