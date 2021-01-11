package nl.rem.kayleigh.project_adoptree.api

import nl.rem.kayleigh.project_adoptree.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

interface AdoptreeService {

    @Headers("Content-Type: application/json")
    @GET("tree")
    suspend fun getTrees() : Response<List<Tree>>

    @Headers("Content-Type: application/json")
    @GET("product")
    suspend fun getProducts(
//            @Query("isUpForAdoption") isUpForAdoption: Boolean = true,
//            @Query("categoryId") categoryId: Int = 1
    ) : Response<List<Product>>

    @Headers("Content-Type: application/json")
    @GET("product/2")
    suspend fun getBigTrees() : Response<List<Product>>

    @Headers("Content-Type: application/json")
    @GET("product/9")
    suspend fun getSaplings() : Response<List<Product>>

    @Headers("Content-Type: application/json")
    @GET("category")
    suspend fun getCategories() : Response<List<Category>>
}