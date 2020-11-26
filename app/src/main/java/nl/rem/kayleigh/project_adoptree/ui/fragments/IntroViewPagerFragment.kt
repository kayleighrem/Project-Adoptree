package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_intro_view_pager.view.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.ViewPagerAdapter
import nl.rem.kayleigh.project_adoptree.ui.fragments.screens.IntroPage1Fragment
import nl.rem.kayleigh.project_adoptree.ui.fragments.screens.IntroPage2Fragment
import nl.rem.kayleigh.project_adoptree.ui.fragments.screens.IntroPage3Fragment

class IntroViewPagerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_intro_view_pager, container, false)

        val fragmentlist = arrayListOf<Fragment> (
            IntroPage1Fragment(),
            IntroPage2Fragment(),
            IntroPage3Fragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentlist,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        view.viewpager.adapter = adapter

        return view
    }
}