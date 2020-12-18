package nl.rem.kayleigh.project_adoptree.ui.activities

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.ui.fragments.SplashFragment

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        Handler().postDelayed({
            if(onBoardingFinished()) {
                findNavController(R.id.action_splashFragment_to_loginFragment)
            } else {
                findNavController(R.id.action_splashFragment_to_viewPagerFragment)
            }
        }, 3000)
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

//    ): View? {
//        Handler().postDelayed({
//            if(onBoardingFinished()){
//                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
////                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
//            }else{
//                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
//            }
//        }, 3000)
//
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_splash, container, false)
//    }
//
//    fun onBoardingFinished(): Boolean{
//        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
//        return sharedPref.getBoolean("Finished", false)
//    }
}