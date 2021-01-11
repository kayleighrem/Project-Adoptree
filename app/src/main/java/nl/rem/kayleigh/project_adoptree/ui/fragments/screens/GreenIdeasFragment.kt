package nl.rem.kayleigh.project_adoptree.ui.fragments.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_green_ideas.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity
import nl.rem.kayleigh.project_adoptree.util.LinearLayoutManagerWrapper

class GreenIdeasFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var mainActivity = (activity as MainActivity)
        setUpRecyclerView(mainActivity)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_green_ideas, container, false)
    }

    private fun setUpRecyclerView(mainActivity: MainActivity) {
        mainActivity.contentAdapter = (activity as MainActivity).contentAdapter
        rv_green_ideas.apply {
            adapter = mainActivity.contentAdapter
            layoutManager = LinearLayoutManagerWrapper(activity)
        }
    }
}