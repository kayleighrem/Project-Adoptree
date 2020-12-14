package nl.rem.kayleigh.project_adoptree.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_settings.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.SettingsViewPagerAdapter
import nl.rem.kayleigh.project_adoptree.repository.AdoptionRepository
import nl.rem.kayleigh.project_adoptree.repository.UserRepository
import nl.rem.kayleigh.project_adoptree.ui.fragments.*
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.AdoptionViewModel
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.UserViewModel
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.factory.AdoptionViewModelFactory
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.factory.UserViewModelProviderFactory
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class MainActivity : AppCompatActivity() {
    lateinit var adoptionViewModel: AdoptionViewModel
    lateinit var userViewModel: UserViewModel
    lateinit var sessionManager: SessionManager
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var tv_not_logged_in_message: TextView

    lateinit var homeFragment: HomeFragment
    lateinit var timeLineFragment: TimelineFragment
    lateinit var feedFragment: FeedFragment
    lateinit var profileFragment: ProfileFragment
    lateinit var settingsFragment: SettingsFragment
    lateinit var settingsViewPagerAdapter: SettingsViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        sessionManager = SessionManager(this)

        homeFragment = HomeFragment()
        timeLineFragment = TimelineFragment()
        feedFragment = FeedFragment()
        profileFragment = ProfileFragment()
        settingsFragment = SettingsFragment()

        bottomNavigationView.setOnNavigationItemSelectedListener( BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    navigateToFragment(homeFragment)
                    true
                }
                R.id.nav_timeline -> {
                    navigateToFragment(timeLineFragment)
                    true
                }
                R.id.nav_feed -> {
                    navigateToFragment(feedFragment)
                    true
                }
                R.id.nav_profile -> {
                    navigateToFragment(profileFragment)
                    true
                }
                R.id.nav_settings -> {
                    navigateToFragment(settingsFragment)
                    true
                }
                else -> super.onOptionsItemSelected(it)
            }
        })

//        if(!sessionManager.isLogin()) {
////            not_logged_in_message.visibility = View.VISIBLE
//            bottomNavigationView.visibility = View.GONE
//            navigateToFragment("LoginFragment")
//        } else if (sessionManager.isLogin())
////            R.id.not_logged_in_message
////            not_logged_in_message.visibility = View.VISIBLE
//            bottomNavigationView.visibility = View.GONE
//            navigateToFragment("HomeFragment")

        initializeViewModels()

        sessionManager = SessionManager(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.nav_settings) {
            // launch settings activity
            startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
            return true
        }
        return when (item.itemId) {
            R.id.nav_home -> { navigateToFragment(homeFragment); true }
            R.id.nav_timeline -> { navigateToFragment(timeLineFragment); true }
            R.id.nav_feed -> { navigateToFragment(feedFragment); true }
            R.id.nav_profile -> { navigateToFragment(profileFragment); true }
            R.id.nav_settings -> {
//                setUpTabs()
                navigateToFragment(settingsFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = getSupportFragmentManager().beginTransaction()
        transaction.replace(R.id.main_frame, fragment)
        transaction.commit()
    }

    private fun initializeViewModels() {
        val adoptionRepository = AdoptionRepository()
        val adoptionViewModelFactory = AdoptionViewModelFactory(adoptionRepository = AdoptionRepository(), this)
        adoptionViewModel = ViewModelProvider(this, adoptionViewModelFactory).get(AdoptionViewModel::class.java)

        val userRepository = UserRepository()
        val userViewModelProviderFactory = UserViewModelProviderFactory(userRepository, this)
        userViewModel = ViewModelProvider(this, userViewModelProviderFactory).get(UserViewModel::class.java)
    }

//    private fun setUpTabs() {
//        val adapter = SettingsViewPagerAdapter(supportFragmentManager)
//        adapter.addFragment(SettingsProfileFragment(), "Profile")
//        adapter.addFragment(SettingsAppFragment(), "Profile")
//        settingsViewPager.adapter = adapter
//        tabs.setupWithViewPager(settingsViewPager)
//
//        tabs.getTabAt(0)!!
//        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_cake_24)
//    }
}