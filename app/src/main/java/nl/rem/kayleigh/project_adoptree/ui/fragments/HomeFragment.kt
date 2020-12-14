package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.fragment_home.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.UserAdapter
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.HomeViewModel
import nl.rem.kayleigh.project_adoptree.util.LinearLayoutManagerWrapper
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var userAdapter: UserAdapter
    lateinit var sessionManager: SessionManager
    lateinit var homeViewModel: HomeViewModel
    lateinit var cardLayout: CardView
//    lateinit var recyclerView: RecyclerView
    lateinit var linearLayout: LinearLayout
    lateinit var expandableLayout: RelativeLayout
//    lateinit var not_logged_in_message: TextView
    private var authToken: String? = null

    companion object {
        const val TAG = "HomeFragment"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        linearLayout = view.findViewById(R.id.cv_rl_tree_item)
//        cardLayout = view.findViewById(R.id.cv_rl_tree_item)
//        recyclerView = view.findViewById(R.id.recycleView)
//        expandableLayout = view.findViewById(R.id.expandable_card_layout)
//        not_logged_in_message = view.findViewById(R.id.tv_not_logged_in_message)

        sessionManager = SessionManager(view.context)


//         If not logged in, don't show the tree cards
        if (!sessionManager.isLogin()) {
            rl_home_not_logged_in.visibility = View.VISIBLE
            fl_home_logged_in.visibility = View.GONE

        } else if (sessionManager.isLogin()) {
            rl_home_not_logged_in.visibility = View.GONE
            fl_home_logged_in.visibility = View.VISIBLE
            setUpRecyclerView()
        }

        initializeUI()
        setUpRecyclerView()
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
    }

    private fun setUpRecyclerView() {
        userAdapter = UserAdapter()
        rv_home_tree_items.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManagerWrapper(activity)
//            addOnScrollListener(this@HomeFragment.scrollListener)

        }
    }
}