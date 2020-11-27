package nl.rem.kayleigh.project_adoptree.ui.activities

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import nl.rem.kayleigh.project_adoptree.R

class LaunchActivity : AppCompatActivity() {
    companion object {
        private const val SPLASH_TIME_OUT: Long = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

//        Handler().postDelayed({
            startActivity(
                Intent(this, HomeActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
            finish()
//        }, SPLASH_TIME_OUT)
    }
}