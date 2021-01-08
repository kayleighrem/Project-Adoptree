package nl.rem.kayleigh.project_adoptree.api

import nl.rem.kayleigh.project_adoptree.model.Order
import nl.rem.kayleigh.project_adoptree.model.OrderResponse
import retrofit2.Response
import retrofit2.http.*

interface OrderService {
    @Headers("Content-Type: application/json")
    @POST("order")
    suspend fun createOrder(@Body order: Order,
                            @Header("Authorization") token: String) : Response<OrderResponse>

    @Headers("Content-Type: application/json")
    @GET("order/{id}")
    suspend fun getOrderById() : Response<Order>

    @Headers("Content-Type: application/json")
    @PUT("order")
    suspend fun updateOrder()
}