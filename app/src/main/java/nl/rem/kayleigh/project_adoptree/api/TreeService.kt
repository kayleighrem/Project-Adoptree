package nl.rem.kayleigh.project_adoptree.api

import nl.rem.kayleigh.project_adoptree.model.TreeImage
import nl.rem.kayleigh.project_adoptree.model.TreeSign
import nl.rem.kayleigh.project_adoptree.model.User
import retrofit2.http.*

interface TreeService {
    @Headers("Content-Type: application/json")
    @GET("tree/{id}/image")
    suspend fun getImagesByTreeId(@Path ("id") id: Int) : TreeImage

    @Headers("Content-Type: application/json")
    @PUT("tree/personalize")
    suspend fun personalizeTree()

    @Headers("Content-Type: application/json")
    @PUT("tree/renew")
    suspend fun renewTreeForAYear()

    @Headers("Content-Type: application/json")
    @GET("treesign/{id}")
    suspend fun getTreeSign(@Path ("id") id: Int) : TreeSign
}