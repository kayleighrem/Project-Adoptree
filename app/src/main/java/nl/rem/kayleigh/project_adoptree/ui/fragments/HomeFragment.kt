package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_tree_card.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.TreeAdapter
import kotlinx.android.synthetic.main.fragment_home.recycleView
import androidx.recyclerview.widget.RecyclerView
import nl.rem.kayleigh.project_adoptree.util.LinearLayoutManagerWrapper

class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var treeAdapter: TreeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

        card_linear_layout.setOnClickListener {

        }
    }

    private fun setUpRecyclerView() {
        treeAdapter = TreeAdapter()
        recycleView.apply {
            adapter = treeAdapter
            layoutManager = LinearLayoutManagerWrapper(activity)
//            addOnScrollListener(this@HomeFragment.scrollListener)

        }
    }

}