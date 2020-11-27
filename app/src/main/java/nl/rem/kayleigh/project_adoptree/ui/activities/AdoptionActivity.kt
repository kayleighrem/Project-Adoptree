package nl.rem.kayleigh.project_adoptree.ui.activities

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.UserViewModel
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class AdoptionActivity : AppCompatActivity() {

    lateinit var userViewModel: UserViewModel
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adoption)
    }
}