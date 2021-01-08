package nl.rem.kayleigh.project_adoptree.ui.viewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.rem.kayleigh.project_adoptree.R
import nl.rem.kayleigh.project_adoptree.model.*
import nl.rem.kayleigh.project_adoptree.repository.OrderRepository
import nl.rem.kayleigh.project_adoptree.util.Constants.Companion.MOLLIE_PAYMENT_REDIRECT_LINK
import nl.rem.kayleigh.project_adoptree.util.Resource
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

class OrderViewModel(private val orderRepository: OrderRepository, val context: Context) : ViewModel() {
    private val mutableCart: MutableLiveData<List<OrderLine>> = MutableLiveData()
    var products: MutableList<OrderProduct> = mutableListOf<OrderProduct>()
    lateinit var availableProducts: List<Product>
    var treeSign: Product? = null
    lateinit var categories: List<Category>
    var totalPrice: Double = 0.0
//    var orderResponse: OrderResponse? = null
    lateinit var order: Order
    var orderLines: MutableList<OrderLine> = mutableListOf<OrderLine>()

    private val _orderResponse: MutableLiveData<Resource<OrderResponse>> =
            MutableLiveData()
    val orderResponse: LiveData<Resource<OrderResponse>>
        get() = _orderResponse

    companion object {
        const val TAG = "OrderViewModel"
    }

    fun getCart(): MutableList<OrderProduct> {
        return products
    }

    fun add(product: Product) = viewModelScope.launch  {
        try {
            val i = products.indexOfFirst { it.product?.id == product.id }
            if ((products.find { it.product?.id == product.id }) != null) { // test if product to be added is already in the cart
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

//    fun createOrderObject(userId: Int?) : Order {
    fun createOrderObject() : Order {
//        var orderLines: List<OrderLine>
        products.forEach { orderProduct ->
            if (orderProduct.isSignActivated == true) {
                if (treeSign == treeSign) {
                    orderLines.plus(OrderLine(id = null, orderId = null, productId = treeSign?.id, price = null, vat = null, quantity = 1, orderLineStatus = null))
                }
            }
            val orderline = OrderLine(id = null, orderId = null, productId = orderProduct.product?.id, price = null, vat = null, quantity = orderProduct.quantity, orderLineStatus = null)
            println("orderline? = " + orderline)
            orderLines.add(orderline)
            println("[orderlines] 1 = " + orderLines.size)
            orderLines.plus(OrderLine(id = null, orderId = null, productId = orderProduct.product?.id, price = null, vat = null, quantity = orderProduct.quantity, orderLineStatus = null))
            println("[orderlines] 2 = " + orderLines)
        }
        println("orderlines is being made???? " + orderLines)
//        println("return value of order = " + Order(id = null, paymentRedirectLink = MOLLIE_PAYMENT_REDIRECT_LINK, paymentStatus = null, orderStatus = null, userId = userId, createdAt = null, orderLines = orderLines))
//        return Order(id = null, paymentRedirectLink = MOLLIE_PAYMENT_REDIRECT_LINK, paymentStatus = null, orderStatus = null, userId = userId, createdAt = null, orderLines = orderLines) // TODO: this should work
        return Order(id = null, paymentRedirectLink = MOLLIE_PAYMENT_REDIRECT_LINK, paymentStatus = null, orderStatus = null, userId = null, createdAt = null, orderLines = orderLines)
    }

    fun createOrder(order: Order, userId: Int) = viewModelScope.launch  {
        try {
            order.userId = userId
            _orderResponse.value = handleOrderResponse(orderRepository.createOrder(order))
        } catch (e: Exception) {
            _orderResponse.postValue(Resource.Error(context.getString(R.string.connection_error)))
            Log.e(
                    TAG,
                    "${context.getString(R.string.error_log)} ${e.message}"
            )
        }
    }

    private fun handleOrderResponse(response:Response<OrderResponse>): Resource<OrderResponse>? {
        if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
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
}