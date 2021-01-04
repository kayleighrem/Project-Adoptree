package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_adoption_overview.*
import kotlinx.android.synthetic.main.fragment_adoption_overview.rv_overview
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.OrderAdapter
import nl.rem.kayleigh.project_adoptree.model.OrderProduct
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.OrderViewModel
import nl.rem.kayleigh.project_adoptree.util.LinearLayoutManagerWrapper
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class AdoptionOverviewFragment : Fragment(R.layout.fragment_adoption_overview) {
    private lateinit var sessionManager: SessionManager
    lateinit var orderViewModel: OrderViewModel
    lateinit var orderAdapter: OrderAdapter
    var productList: List<OrderProduct> = ArrayList()

    companion object {
        const val TAG = "AdoptionOverviewFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderViewModel = (activity as MainActivity).orderViewModel
        orderAdapter = OrderAdapter()
        sessionManager = SessionManager(view.context)
        initializeUI()

        btn_next_step_pay.setOnClickListener {
            println("session is logged in? " + sessionManager.isLogin())
            if (!sessionManager.isLogin()) {
                findNavController().navigate(R.id.action_adoptionOverviewFragment_to_signUpFragment)
            }
        }

        btn_logged_in_adopt_more.setOnClickListener {
            findNavController().navigate(R.id.action_adoptionOverviewFragment_to_adoptionFragment)
        }

        if (!orderViewModel.products.isNullOrEmpty()) {
            println("try 1: " + orderViewModel.products)
            productList = orderViewModel.products
            orderAdapter.differ.submitList(productList.toList())
            setUpRecyclerView()
        } else {
            println("else 1: " + orderViewModel.products)
        }
        total_euro.text = orderViewModel.totalPrice.toString()
    }

    @SuppressLint("SetTextI18n")
    private fun initializeUI() {
        orderAdapter.setOnPayButtonClickListener { product, i ->
            Toast.makeText(
                    requireContext(),
                    R.string.successfully_added,
                    Toast.LENGTH_SHORT
            ).show()
            orderViewModel.add(product)
        }

        orderAdapter.setOnPlusButtonClickListener { product, i ->
            orderViewModel.increaseQuantity(product.product!!)
            total_euro.text = orderViewModel.totalPrice.toString()
        }

        orderAdapter.setOnMinusButtonClickListener { product, i ->
            orderViewModel.decreaseQuantity(product.product!!)
            total_euro.text = orderViewModel.totalPrice.toString()
        }

        orderAdapter.setOnRemoveButtonClickListener { product, i ->
            orderViewModel.remove(product.product!!)
            total_euro.text = orderViewModel.totalPrice.toString()
            setUpRecyclerView()
            orderAdapter.notifyDataSetChanged()
        }
    }

    private fun setUpRecyclerView() {
        rv_overview.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManagerWrapper(activity)
        }
    }
}