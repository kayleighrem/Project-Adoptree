package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.item_tree_card.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.TreeAdapter
import kotlinx.android.synthetic.main.fragment_home.recycleView
import androidx.recyclerview.widget.RecyclerView
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.HomeViewModel
import nl.rem.kayleigh.project_adoptree.util.LinearLayoutManagerWrapper
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var treeAdapter: TreeAdapter
    lateinit var sessionManager: SessionManager
    lateinit var homeViewModel: HomeViewModel
    lateinit var linearLayout: LinearLayout
    lateinit var expandableLayout: RelativeLayout
    private var authToken: String? = null

    companion object {
        const val TAG = "HomeFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        linearLayout = findViewById(R.id.card_linear_layout)
//        expandableLayout = view.findViewById(R.id.expandable_card_layout)
        sessionManager = SessionManager(view.context)


        // If not logged in, don't show the tree cards
//        if (!sessionManager.isLogin()) {
//            linearLayout.visibility = View.GONE
//        } else if (sessionManager.isLogin()) {
//            setUpRecyclerView()
//        }

//        initializeUI()
//        setUpRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

//        card_linear_layout.setOnClickListener {
//            if (sessionManager.isLogin()) {
//                expandableLayout.visibility = View.VISIBLE
////                if (article.IsLiked == true) {
////                    likeButton.setImageResource(R.drawable.ic_baseline_favorite_24)
////                } else {
////                    likeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
////                }
//            }
//        }
    }

    private fun initializeUI() {
        if (sessionManager.isLogin()) {
            authToken = sessionManager.getUserDetails().AuthToken.toString()
        }
        treeAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("tree", it)
            }
//            findNavController().navigate(
//                R.id.,
//                bundle
//            )
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