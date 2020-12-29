package nl.rem.kayleigh.project_adoptree.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.rem.kayleigh.project_adoptree.model.*
import nl.rem.kayleigh.project_adoptree.repository.OrderRepository
import java.lang.Exception

class OrderViewModel(private val orderRepository: OrderRepository, val context: Context) : ViewModel() {
    private val mutableCart: MutableLiveData<List<OrderLine>> = MutableLiveData()
    var products: MutableList<OrderProduct> = mutableListOf<OrderProduct>()
    lateinit var availableProducts: List<Product>
    var treeSign: Product? = null
    lateinit var categories: List<Category>
    var totalPrice: Double = 0.0
    var orderResponse: OrderResponse? = null
    lateinit var order: Order

    fun getCart(): MutableList<OrderProduct> {
        return products
    }

    fun add(product: Product) = viewModelScope.launch  {
        try {
            val i = products.indexOfFirst { it.product?.id == product.id }
            if ((products.find { it.product?.id == product.id }) != null) {
                products[i].quantity = products[i].quantity?.plus(1)
                calculateTotal()
                println("test cart1: " + products)
            } else {
                products.add(OrderProduct(product = product, quantity = 1, id = null))
                calculateTotal()
                println("test cart2: " + products)
            }
        } catch (e: Exception ) {
            println("test cart3: " + products)
            println("test add error: " + e)
        }
    }

    fun remove(product: Product) {
        try {
            val i = products.indexOfFirst { it.product?.id == product.id }
            if ((products.find { it.product?.id == product.id }) != null) {
                products.removeAt(i)
                calculateTotal()
            }
        } catch (e: Exception ) { }
    }

    fun increaseQuantity(product: Product) {
        try {
            val i = products.indexOfFirst { it.product?.id == product.id }
            if ((products.find { it.product?.id == product.id }) != null) {
                products[i].quantity = products[i].quantity?.plus(1)
                calculateTotal()
            }
        } catch (e: Exception ) { }
    }

    fun decreaseQuantity(product: Product) {
        try {
            val i = products.indexOfFirst { it.product?.id == product.id }
            if ((products.find { it.product?.id == product.id }) != null) {
                if (products[i].quantity != 1) {
                    products[i].quantity = products[i].quantity?.minus(1)
                    calculateTotal()
                }
            }
        } catch (e: Exception ) { }
    }

    private fun createOrderObject(userId: Int) : Order {
        var orderLines: List<OrderLine> = ArrayList()
        products.forEach { orderProduct ->
            if (orderProduct.isSignActivated == true) {
                if (treeSign == treeSign) {
                    orderLines.plus(OrderLine(id = null, orderId = null, productId = treeSign?.id, price = null, vat = null, quantity = 1))
                }
            }
            orderLines.plus(OrderLine(id = null, orderId = null, productId = orderProduct.product?.id, price = null, vat = null, quantity = orderProduct.quantity))
        }
        return Order(id = null, paymentRedirectLink = "mollie-app//payment-return", paymentStatus = null, orderStatus = null, userId = userId, createdAt = null, orderLines = orderLines)
    }

    private fun calculateTotal() {
        var total: Double = 0.0

        products.forEach { product ->
            total += (product.product?.price?.times(product.quantity!!)!!)
            if (product.isSignActivated == true) {
                total += treeSign?.price!!
            }
            this.totalPrice = total
        }
    }

    private fun initCart() {
        mutableCart.value = ArrayList<OrderLine>()
    }
}