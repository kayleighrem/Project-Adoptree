package nl.rem.kayleigh.project_adoptree.ui.fragments.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_intro_page1.view.*
import nl.rem.kayleigh.project_adoptree.R
class IntroPage1Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_intro_page1, container, false)
        val viewpager = activity?.findViewById<ViewPager2>(R.id.viewpager)

        view.next.setOnClickListener {
            viewpager?.currentItem = 1
        }

        return view
    }
}