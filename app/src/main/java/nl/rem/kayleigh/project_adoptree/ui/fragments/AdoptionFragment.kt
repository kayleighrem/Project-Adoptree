package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_adoption.*
import kotlinx.android.synthetic.main.fragment_adoption.view.*
import kotlinx.android.synthetic.main.fragment_intro_page3.view.*
import kotlinx.android.synthetic.main.item_adoption_tree_card.view.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.AdoptionAdapter
import nl.rem.kayleigh.project_adoptree.adapters.TreeAdapter
import nl.rem.kayleigh.project_adoptree.ui.activities.AdoptionActivity
import nl.rem.kayleigh.project_adoptree.ui.activities.HomeActivity
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.AdoptionViewModel
import nl.rem.kayleigh.project_adoptree.util.LinearLayoutManagerWrapper

class AdoptionFragment : Fragment(R.layout.fragment_adoption) {
    lateinit var adoptionAdapter: AdoptionAdapter
    lateinit var adoptionViewModel: AdoptionViewModel

    companion object {
        const val TAG = "AdoptionFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adoptionViewModel = (activity as HomeActivity).adoptionViewModel
        println("test adoptionfragment 28")
        setUpRecyclerView()
        initializeUI()
    }

    private fun initializeUI() {

        if (adoptionViewModel.trees.value == null) {
            println("empty? 43")
            adoptionViewModel.getTrees()
        }
        else {
            println("empty? 47")
        }

        adoptionAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("tree", it)
            }
//            findNavController().navigate(
//                    R.id.action_articlesFragment_to_detailsArticleActivity,
//                    bundle
//            )
        }

//        adoptionAdapter.setOnLikedButtonClickListener { tree, i ->
//            if (!tree.IsLiked!!) {
//                savedArticleViewModel.likeAnArticle(article.Id!!, authToken!!)
//                Toast.makeText(
//                        requireContext(),
//                        R.string.add_to_favourite,
//                        Toast.LENGTH_SHORT
//                ).show()
//                articleAdapter.differ.currentList[i].IsLiked = true
//                articleAdapter.notifyItemChanged(i)
//                savedArticleViewModel.getLikedArticles(authToken!!)
//            } else {
//                savedArticleViewModel.unlikeAnArticle(article.Id!!, authToken!!)
//                Toast.makeText(
//                        requireContext(),
//                        R.string.remove_from_favourite,
//                        Toast.LENGTH_SHORT
//                ).show()
//                articleAdapter.differ.currentList[i].IsLiked = false
//                articleAdapter.notifyItemChanged(i)
//                savedArticleViewModel.getLikedArticles(authToken!!)
//            }
//        }
    }

    private fun setUpRecyclerView() {
        println("test adoption 33")
        adoptionAdapter = AdoptionAdapter()
        rv_adoptionRecyclerView.apply {
            adapter = adoptionAdapter
            layoutManager = LinearLayoutManagerWrapper(activity)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_adoption, container, false)

        view.number_results.text = "test"

//        view.btn_next_step.setOnClickListener {
//            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
//        }
//
//        view.btn_adopt.setOnClickListener{
//            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
//        }
//
//        view.btn_info.setOnClickListener{
//            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
//        }
        return view
    }
}