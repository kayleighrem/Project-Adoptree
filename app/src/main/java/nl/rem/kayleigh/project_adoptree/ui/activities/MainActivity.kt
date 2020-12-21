package nl.rem.kayleigh.project_adoptree.ui.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import nl.rem.kayleigh.project_adoptree.R
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

        bottomNavigationView.setOnNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.nav_home -> navigateToFragment(homeFragment)
                R.id.nav_timeline -> navigateToFragment(timeLineFragment)
                R.id.nav_feed -> navigateToFragment(feedFragment)
                R.id.nav_profile -> navigateToFragment(profileFragment)
                R.id.nav_settings -> navigateToFragment(settingsFragment)
            }
            true
        }

        initializeViewModels()

        sessionManager = SessionManager(this)
    }

    private fun navigateToFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_fragment, fragment)
        transaction.commit()
    }

    private fun initializeViewModels() {
        val adoptionRepository = AdoptionRepository()
        val adoptionViewModelFactory = AdoptionViewModelFactory(adoptionRepository, this)
        adoptionViewModel = ViewModelProvider(this, adoptionViewModelFactory).get(AdoptionViewModel::class.java)

        val userRepository = UserRepository()
        val userViewModelProviderFactory = UserViewModelProviderFactory(userRepository, this)
        userViewModel = ViewModelProvider(this, userViewModelProviderFactory).get(UserViewModel::class.java)
    }
}