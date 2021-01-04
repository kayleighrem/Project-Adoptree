package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.UserAdapter
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.HomeViewModel
import nl.rem.kayleigh.project_adoptree.util.LinearLayoutManagerWrapper
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class HomeFragment : Fragment(R.layout.fragment_home) {
    lateinit var userAdapter: UserAdapter
    lateinit var sessionManager: SessionManager
    private var authToken: String? = null
    lateinit var bottomNavigationView: BottomNavigationView

    companion object {
        const val TAG = "HomeFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(view.context)
        this.bottomNavigationView = (activity as MainActivity).bottomNavigationView
        bottomNavigationView = bottomNavigationView.findViewById(R.id.bottomNavigationView)
        bottomNavigationView.visibility = View.VISIBLE
        initializeUI()

        if (!sessionManager.isLogin()) { // if not logged in
            rl_home_not_logged_in.visibility = View.VISIBLE
            fl_home_logged_in.visibility = View.GONE

        } else if (sessionManager.isLogin()) { // if logged in
            rl_home_not_logged_in.visibility = View.GONE
            fl_home_logged_in.visibility = View.VISIBLE
            setUpRecyclerView()
        }
        setUpRecyclerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun initializeUI() {
        if (sessionManager.isLogin()) {
//            authToken = sessionManager.getUserDetails().AuthToken.toString()
            authToken = sessionManager.getUserDetails().accessToken.toString()
        }

        btn_adopt_now.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_adoptionFragment)
        }
        btn_start_adopt_now.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_adoptionFragment)
        }
    }

    private fun setUpRecyclerView() {
        userAdapter = UserAdapter()
        rv_home_tree_items.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManagerWrapper(activity)
        }
    }
}