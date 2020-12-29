package nl.rem.kayleigh.project_adoptree.repository

import nl.rem.kayleigh.project_adoptree.api.RetrofitInstance
import nl.rem.kayleigh.project_adoptree.model.Order

class AdoptionRepository {
    suspend fun getAvailableTrees() = RetrofitInstance.api.getTrees()
    suspend fun getProducts() = RetrofitInstance.api.getProducts()
    suspend fun getCategories() = RetrofitInstance.api.getCategories()
    suspend fun getOrderById() = RetrofitInstance.api.getOrderById()
    suspend fun createOrder(order: Order) = RetrofitInstance.api.createOrder(order = order)
    suspend fun updateOrder() = RetrofitInstance.api.updateOrder()
}