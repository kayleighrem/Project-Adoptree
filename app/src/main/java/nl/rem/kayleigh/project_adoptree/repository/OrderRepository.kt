package nl.rem.kayleigh.project_adoptree.repository

import nl.rem.kayleigh.project_adoptree.api.RetrofitInstance
import nl.rem.kayleigh.project_adoptree.model.Order

class OrderRepository {
    suspend fun createOrder(order: Order) = RetrofitInstance.orderapi.createOrder(order)
    suspend fun getOrderById() = RetrofitInstance.orderapi.getOrderById()
    suspend fun updateOrder() = RetrofitInstance.orderapi.updateOrder()
}