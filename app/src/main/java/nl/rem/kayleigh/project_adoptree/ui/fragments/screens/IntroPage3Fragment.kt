package nl.rem.kayleigh.project_adoptree.ui.fragments.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_intro_page3.view.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class IntroPage3Fragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_intro_page3, container, false)
        var sessionmanager: SessionManager = SessionManager(requireContext())

        view.finish.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
//            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
//            findNavController().navigate(R.id.action_viewPagerFragment_to_homeFragment)

            if (sessionmanager.isLogin()) {
                onBoardingFinished()
            }
        }
        return view
    }

    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}