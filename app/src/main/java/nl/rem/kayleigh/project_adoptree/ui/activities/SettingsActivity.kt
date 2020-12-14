package nl.rem.kayleigh.project_adoptree.ui.activities

import android.content.res.Configuration
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceActivity
import android.preference.PreferenceFragment
import android.preference.RingtonePreference
import android.text.TextUtils
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.preference.*
import nl.rem.kayleigh.project_adoptree.R

class SettingsActivity : AppCompatPreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // load settings fragment
        fragmentManager.beginTransaction().replace(android.R.id.content, MainPreferenceFragment()).commit()
    }

    class MainPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.root_preferences)

            // gallery EditText change listener
//            bindPreferenceSummaryToValue(findPreference(getString(R.string.key_gallery_name)))

            // notification preference change listener
//            bindPreferenceSummaryToValue(findPreference(getString(R.string.key_notifications_new_message_ringtone)))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAG = SettingsActivity::class.java!!.getSimpleName()

        private fun bindPreferenceSummaryToValue(preference: Preference) {
            preference.onPreferenceChangeListener = sBindPreferenceSummaryToValueListener

            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                    .getDefaultSharedPreferences(preference.context)
                    .getString(preference.key, ""))
        }

        private val sBindPreferenceSummaryToValueListener = Preference.OnPreferenceChangeListener { preference, newValue ->
            val stringValue = newValue.toString()

            if (preference is ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                val index = preference.findIndexOfValue(stringValue)

                // Set the summary to reflect the new value.
                preference.setSummary(
                    if (index >= 0)
                        preference.entries[index]
                    else
                        null)

            } else if (preference is RingtonePreference) {
                // For ringtone preferences, look up the correct display value
                // using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
//                    preference.setSummary(R.string.pref_ringtone_silent)

                } else {
                    val ringtone = RingtoneManager.getRingtone(
                        preference.getContext(), Uri.parse(stringValue))

                    if (ringtone == null) {
                        // Clear the summary if there was a lookup error.
//                        preference.setSummary(R.string.summary_choose_ringtone)
                    } else {
                        // Set the summary to reflect the new ringtone display
                        // name.
                        val name = ringtone.getTitle(preference.getContext())
                        preference.setSummary(name)
                    }
                }

            } else if (preference is EditTextPreference) {
                if (preference.getKey() == "key_gallery_name") {
                    // update the changed gallery name to summary filed
                    preference.setSummary(stringValue)
                }
            } else {
                preference.summary = stringValue
            }
            true
        }
    }
}


abstract class AppCompatPreferenceActivity : PreferenceActivity() {

    private var mDelegate: AppCompatDelegate? = null

    val supportActionBar: ActionBar?
        get() = delegate?.supportActionBar

    private val delegate: AppCompatDelegate?
        get() {
            if (mDelegate == null) {
                mDelegate = AppCompatDelegate.create(this, null)
            }
            return mDelegate
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        delegate?.installViewFactory()
        delegate?.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        delegate?.onPostCreate(savedInstanceState)
    }

    fun setSupportActionBar(toolbar: Toolbar?) {
        delegate?.setSupportActionBar(toolbar)
    }

    override fun getMenuInflater(): MenuInflater {
        return delegate!!.menuInflater
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        delegate?.setContentView(layoutResID)
    }

    override fun setContentView(view: View) {
        delegate?.setContentView(view)
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        delegate?.setContentView(view, params)
    }

    override fun addContentView(view: View, params: ViewGroup.LayoutParams) {
        delegate?.addContentView(view, params)
    }

    override fun onPostResume() {
        super.onPostResume()
        delegate?.onPostResume()
    }

    override fun onTitleChanged(title: CharSequence, color: Int) {
        super.onTitleChanged(title, color)
        delegate?.setTitle(title)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        delegate?.onConfigurationChanged(newConfig)
    }

    override fun onStop() {
        super.onStop()
        delegate?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        delegate?.onDestroy()
    }

    override fun invalidateOptionsMenu() {
        delegate?.invalidateOptionsMenu()
    }
}