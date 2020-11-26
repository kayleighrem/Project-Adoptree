package nl.rem.kayleigh.project_adoptree.ui.fragments.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_intro_page2.view.*
import nl.rem.kayleigh.project_adoptree.R
class IntroPage2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_intro_page2, container, false)

        val viewpager = activity?.findViewById<ViewPager2>(R.id.viewpager)

        view.next.setOnClickListener {
            viewpager?.currentItem = 2
        }

        return view
    }
}