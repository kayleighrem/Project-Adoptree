package nl.rem.kayleigh.project_adoptree.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.repository.UserRepository
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.UserViewModel
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.factory.UserViewModelProviderFactory
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class HomeActivity : AppCompatActivity() {
    lateinit var userViewModel: UserViewModel
    lateinit var sessionManager: SessionManager
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var not_logged_in_message: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
//        not_logged_in_message = findViewById(R.id.not_logged_in_message)

        sessionManager = SessionManager(this)

        if(!sessionManager.isLogin()) {
//            not_logged_in_message.visibility = View.VISIBLE
            bottomNavigationView.visibility = View.GONE
            navigateToFragment("LoginFragment")
        } else if (sessionManager.isLogin())
//            R.id.not_logged_in_message
//            not_logged_in_message.visibility = View.VISIBLE
            bottomNavigationView.visibility = View.GONE
            navigateToFragment("HomeFragment")

        initializeViewModels()

        sessionManager = SessionManager(this)
    }

    private fun navigateToFragment(fragmentname: String) {
       val fragment =
            when (fragmentname) {
                "LoginFragment" -> R.id.loginFragment
                "HomeFragment" -> R.id.homeFragment
                "AdoptionFragment" -> R.id.adoptionFragment
                else -> {
                    R.id.loginFragment
                }
            }
//        val fragment =
//            if (fragmentname == "LoginFragment") R.id.loginFragment
//            else R.id.homeFragment

    }

    private fun initializeViewModels() {
        val userRepository = UserRepository()
        val userViewModelProviderFactory = UserViewModelProviderFactory(userRepository, this)
        userViewModel =
            ViewModelProvider(this, userViewModelProviderFactory).get(UserViewModel::class.java)
    }
}