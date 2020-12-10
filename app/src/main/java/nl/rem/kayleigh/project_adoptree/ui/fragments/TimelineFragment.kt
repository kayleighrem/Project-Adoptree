package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.item_tree_card.*
import nl.rem.kayleigh.project_adoptree.R


class TimelineFragment : Fragment(R.layout.fragment_timeline) {
    companion object {
        const val TAG = "TimelineFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline, container, false)
    }
}