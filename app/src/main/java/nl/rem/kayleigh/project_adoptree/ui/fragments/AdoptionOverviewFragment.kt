package nl.rem.kayleigh.project_adoptree.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_adoption_overview.*
import kotlinx.android.synthetic.main.fragment_adoption_overview.rv_overview
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.item_adoption_tree_card.view.*
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.adapters.OrderAdapter
import nl.rem.kayleigh.project_adoptree.model.OrderProduct
import nl.rem.kayleigh.project_adoptree.ui.activities.MainActivity
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.OrderViewModel
import nl.rem.kayleigh.project_adoptree.ui.viewmodels.UserViewModel
import nl.rem.kayleigh.project_adoptree.util.LinearLayoutManagerWrapper
import nl.rem.kayleigh.project_adoptree.util.Resource
import nl.rem.kayleigh.project_adoptree.util.SessionManager

class AdoptionOverviewFragment : Fragment(R.layout.fragment_adoption_overview) {
    private lateinit var sessionManager: SessionManager
    lateinit var mainActivity: MainActivity
    lateinit var orderViewModel: OrderViewModel
    lateinit var orderAdapter: OrderAdapter
    lateinit var userViewModel: UserViewModel
    var productList: List<OrderProduct> = ArrayList()

    companion object {
        const val TAG = "AdoptionOverviewFragment"
    }

    @SuppressLint("LongLogTag")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderViewModel = (activity as MainActivity).orderViewModel
        userViewModel = (activity as MainActivity).userViewModel
        mainActivity = (activity as MainActivity)
        orderAdapter = OrderAdapter()
        sessionManager = SessionManager(view.context)
        setUpRecyclerView()
        initializeUI()

        orderViewModel.orderResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    try {
                        // TODO: go to payment url
//                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        Toast.makeText(
                                requireContext(),
                                "${getString(R.string.pay)} ",
                                Toast.LENGTH_LONG
                        ).show()
                    } catch (e: Exception) {
                        Log.e(TAG, "${getString(R.string.error_log)} ${e.message}")
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(
                            requireContext(),
                            R.string.wrong_credentials,
                            Toast.LENGTH_LONG
                    ).show()
                    response.message?.let { message ->
                        Log.e(TAG, "${getString(R.string.error_log)} $message")
                    }
                }
            }
        })

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

    @SuppressLint("SetTextI18n", "LongLogTag")
    private fun initializeUI() {
        btn_next_step_pay.setOnClickListener {
            try { // try to create an order
                val order = orderViewModel.createOrderObject()
                if (sessionManager.isLogin()) { // if user is logged in, go to mollie
                    // TODO
                    userViewModel.getLoggedInUser(sessionManager.getUserDetails())
                    println("logged in user response: " + userViewModel.loggedinUserResponse.value!!.data)

                    try {
                        orderViewModel.createOrder(order, userViewModel.loggedinUserResponse.value!!.data!!.id!!, sessionManager.getUserDetails().accessToken)
                        orderViewModel.orderResponse.observe(viewLifecycleOwner, Observer { response ->
                            when (response) {
                                is Resource.Success -> {
                                    try {
                                        pay(response.data!!.paymentLink)
                                    } catch (e: Exception) {
                                        Log.e(TAG, "${getString(R.string.error_log)} ${getString(R.string.test)} \${getString(R.string.test)} ${e.message}")
                                    }
                                }
                            }
                        })
                    } catch (e: Exception) {
                        Log.e(TAG, "${getString(R.string.error_log)} ${getString(R.string.test)} \${getString(R.string.test)} ${e.message}")
                    }

                } else { // if user is not logged in, sign up first
//                    mainActivity.navigateToFragment(mainActivity.signUpFragment)
                    try {
                        val bundle = bundleOf("order" to order)
                        mainActivity.navigateToFragment(mainActivity.signUpFragment)
//                        findNavController().navigate(R.id.action_adoptionOverviewFragment_to_signUpFragment, bundle)
                    } catch (e: Exception) {}

                }
            } catch (e: Exception) {
                Log.e(TAG, "${getString(R.string.error_log)} ${getString(R.string.test)} ${e.message}")
            }
        }

        btn_logged_in_adopt_more.setOnClickListener {
            try {
                mainActivity.navigateToFragment(mainActivity.adoptionFragment)
//                findNavController().navigate(R.id.action_adoptionOverviewFragment_to_adoptionFragment)
            }catch (e: Exception) {}

        }

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

            orderAdapter.notifyDataSetChanged()
            setUpRecyclerView()
        }
    }

    private fun setUpRecyclerView() {
        rv_overview.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManagerWrapper(activity)
        }
    }

    fun pay(url: String?) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        
        mainActivity.navigateToFragment(mainActivity.adoptionResponseFragment)
    }
}