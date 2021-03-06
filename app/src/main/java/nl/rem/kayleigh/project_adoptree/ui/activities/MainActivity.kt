package nl.rem.kayleigh.project_adoptree.ui.activities

import android.animation.TimeAnimator
import android.annotation.SuppressLint
import android.bluetooth.BluetoothClass
import android.nfc.Tag
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.ContentAdapter
import nl.rem.kayleigh.project_adoptree.adapters.UserAdapter
import nl.rem.kayleigh.project_adoptree.model.OrderProduct
import nl.rem.kayleigh.project_adoptree.repository.*
import nl.rem.kayleigh.project_adoptree.ui.fragments.*
import nl.rem.kayleigh.project_adoptree.ui.fragments.screens.AboutAppFragment
import nl.rem.kayleigh.project_adoptree.ui.fragments.screens.ContractInformationFragment
import nl.rem.kayleigh.project_adoptree.ui.fragments.screens.PrivacyPolicyFragment
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.*
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.factory.*
import nl.rem.kayleigh.project_adoptree.util.SessionManager
import java.lang.Exception
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var mainHandler: Handler

    lateinit var adoptionViewModel: AdoptionViewModel
    lateinit var orderViewModel: OrderViewModel
    lateinit var userViewModel: UserViewModel
    lateinit var contentViewModel: ContentViewModel
    lateinit var homeViewModel: HomeViewModel
    lateinit var sessionManager: SessionManager
    lateinit var bottomNavigationView: BottomNavigationView

    var userAdapter: UserAdapter = UserAdapter()
    var userRepository: UserRepository = UserRepository()
    var treeRepository: TreeRepository = TreeRepository()
    var contentAdapter: ContentAdapter = ContentAdapter()

    var adoptionFragment: AdoptionFragment = AdoptionFragment()
    var loginFragment: LoginFragment = LoginFragment()
    var adoptionOverviewFragment: AdoptionOverviewFragment = AdoptionOverviewFragment()
    var signUpFragment: SignUpFragment = SignUpFragment()

    var homeFragment: HomeFragment = HomeFragment()
    var timeLineFragment: TimelineFragment = TimelineFragment()
    var feedFragment: FeedFragment = FeedFragment()
    var profileFragment: ProfileFragment = ProfileFragment()
    var settingsFragment: SettingsFragment = SettingsFragment()
    var adoptionResponseFragment: AdoptionResponseFragment = AdoptionResponseFragment()
    var aboutAppFragment: AboutAppFragment = AboutAppFragment()
    var contractInformationFragment: ContractInformationFragment = ContractInformationFragment()
    var privacyPolicyFragment: PrivacyPolicyFragment = PrivacyPolicyFragment()

    private companion object {
        const val TAG = "Main Activity"
        const val DEFAULT_LAYOUT = "fl_fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val defaultlayout: FrameLayout = findViewById(R.id.fl_fragment)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

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

    fun navigateToFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_fragment, fragment)
        transaction.commit()
    }

    private fun initializeViewModels() {
        val orderRepository = OrderRepository()
        val orderViewModelFactory = OrderViewModelFactory.OrderViewModelFactory(orderRepository, this)
        orderViewModel = ViewModelProvider(this, orderViewModelFactory).get(OrderViewModel::class.java)

        val adoptionRepository = AdoptionRepository()
        val adoptionViewModelFactory = AdoptionViewModelFactory(adoptionRepository, this)
        adoptionViewModel = ViewModelProvider(this, adoptionViewModelFactory).get(AdoptionViewModel::class.java)

        val userRepository = UserRepository()
        val userViewModelProviderFactory = UserViewModelProviderFactory(userRepository, this)
        userViewModel = ViewModelProvider(this, userViewModelProviderFactory).get(UserViewModel::class.java)

        val contentRepository = ContentRepository()
        val contentViewModelFactory = ContentViewModelFactory(contentRepository, this)
        contentViewModel = ViewModelProvider(this, contentViewModelFactory).get(ContentViewModel::class.java)

        val homeViewModelFactory = HomeViewModelFactory(this@MainActivity, this)
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
    }

    fun activateHandler() {
//        mainHandler = Handler(Looper.getMainLooper())
//        mainHandler.post(object : Runnable {
//            override fun run() {
//                getToken()
//                mainHandler.postDelayed(this, 1200000)
//            }
//        })

        Handler().postDelayed({
//            getToken()
        }, 1000) // TODO: should be 1200002
    }

    @SuppressLint("SimpleDateFormat")
    fun getToken() {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        try {
            println("old access token: " + sessionManager.getUserDetails().accessToken)
            println("old refresh token: " + sessionManager.getUserDetails().refreshToken)
            userViewModel.refreshToken(sessionManager.getUserDetails().accessToken)
            println("test ")
            userViewModel.getLoggedInUser(sessionManager.getUserDetails())
            println("loggedin user? " + userViewModel.loggedinUserResponse.value!!.data!!.username)

            println("new access token: " + sessionManager.getUserDetails().accessToken)
            println("new refresh token: " + sessionManager.getUserDetails().refreshToken)
        } catch (e: Exception) {
//            println("get logged in user by catch? " + userViewModel.loggedinUserResponse.value!!.data)
            Log.e(
                TAG,
                "${e.message}"
            )
        }

        println("test token every minute " + currentDate)
    }
}