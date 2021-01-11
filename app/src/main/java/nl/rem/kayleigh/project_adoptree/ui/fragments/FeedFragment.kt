package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_timeline.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.ContentAdapter
import nl.rem.kayleigh.project_adoptree.model.Content
import nl.rem.kayleigh.project_adoptree.repository.ContentRepository
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.ContentViewModel
import nl.rem.kayleigh.project_adoptree.util.LinearLayoutManagerWrapper
import nl.rem.kayleigh.project_adoptree.util.Resource
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class FeedFragment : Fragment(R.layout.fragment_feed) {
    lateinit var mainActivity: MainActivity
    lateinit var sessionManager: SessionManager
    lateinit var contentAdapter: ContentAdapter
    lateinit var contentViewModel: ContentViewModel
    lateinit var contentRepository: ContentRepository

    var informativeList: List<Content> = listOf()

    companion object {
        const val TAG = "FeedFragment"
        const val INFORMATIVE = "informative"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(view.context)
        mainActivity = (activity as MainActivity)
        contentViewModel =  mainActivity.contentViewModel

        if (!sessionManager.isLogin()) { // if not logged in, don't show the 'add' button for booking a new tour
            btn_add_tour.visibility = View.GONE
        } else if (sessionManager.isLogin()) { // if logged in, show the button for booking a new tour
            btn_add_tour.visibility = View.VISIBLE
        }

        initializeUI()
    }

    private fun initializeUI() {
        println("hallo?")
        contentViewModel.getContent()
        contentViewModel.contentResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    try {
                        println("response = " + response)
                        contentViewModel.contentResponse.value?.data?.forEach {
                        }
                    } catch (e: Exception) {
                        Log.e(ProfileFragment.TAG, "${getString(R.string.error_log)} ${e.message}")
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(
                            requireContext(),
                            R.string.something_went_wrong,
                            Toast.LENGTH_LONG
                    ).show()
                    response.message?.let { message ->
                        Log.e(ProfileFragment.TAG, "${getString(R.string.error_log)} $message")
                    }
                }
            }
        })
        action_read_more_tips.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_greenIdeasFragment)
        }
        btn_add_tour.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_bookATourActivity)
        }
    }
}