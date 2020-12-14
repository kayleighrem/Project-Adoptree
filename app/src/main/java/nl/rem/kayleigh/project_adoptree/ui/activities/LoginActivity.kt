package nl.rem.kayleigh.project_adoptree.ui.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.activity_main.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.repository.UserRepository
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.UserViewModel
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.factory.UserViewModelProviderFactory
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class LoginActivity : AppCompatActivity() {
//    lateinit var userViewModel: UserViewModel
//    lateinit var sessionManager: SessionManager
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_login)
//
//        initializeViewModels()
//
//        sessionManager = SessionManager(this)
//    }
//
//    private fun initializeViewModels() {
//    }
//
//    private fun navigateToSelectedFragment(fragmentName: String) {
//        // TODO
//    }
//    private fun getNavOptions(): NavOptions {
//        return NavOptions.Builder()
//            .setPopUpTo(R.id.adoptionFragment, true)
//            .build();
//    }
}