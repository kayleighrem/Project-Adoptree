package nl.rem.kayleigh.project_adoptree.api

import nl.rem.kayleigh.project_adoptree.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

interface AdoptreeService {

    @GET("user/{id}/tree")
    suspend fun getTrees(@Header("x-authtoken") authToken: String?): Response<Tree>

    @DELETE("user/{id}/like")
    suspend fun unlikeAnArticle(
            @Path("id") id: Int,
            @Header("x-authtoken") authToken: String
    ): Response<Unit>

    @Headers("Content-Type: application/json")
    @GET("tree")
    suspend fun getAvailableTrees() : Response<List<Tree>>
//    suspend fun getAllTrees() : Response<List<Tree>>
//    suspend fun getAllTrees(@Query("assignedTree") notassigned: String) : Response<TreeResult>
}