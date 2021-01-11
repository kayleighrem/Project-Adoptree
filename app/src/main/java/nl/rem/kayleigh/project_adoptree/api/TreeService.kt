package nl.rem.kayleigh.project_adoptree.api

import nl.rem.kayleigh.project_adoptree.model.*
import retrofit2.Response
import retrofit2.http.*

interface TreeService {
    @Headers("Content-Type: application/json")
    @GET("tree/{id}/image")
    suspend fun getImagesByTreeId(@Path ("id") id: Int) : TreeImage

    @Headers("Content-Type: application/json")
    @PUT("tree/renew")
    suspend fun renewTreeForAYear()

    @Headers("Content-Type: application/json")
    @GET("treesign/{id}")
    suspend fun getTreeSign(@Path ("id") id: Int) : TreeSign

    @Headers("Content-Type: application/json")
    @GET("/api/v1/telemetry/{id}")
    suspend fun getTelemetryByTreeId(@Path ("id") id: Int,
            @Header("Authorization") token: String ) : Response<Telemetry>

    @Headers("Content-Type: application/json")
    @GET("trees/{id}")
    suspend fun getTreesByUserByTreeId(@Header("Authorization") token: String,
                                       @Path("id") id: Int) : Response<Tree>

    @Headers("Content-Type: application/json")
    @PUT("trees/personalize")
    suspend fun personalizeTree(
            @Header("Authorization") token: String,
            @Body assignedTree: AssignedTree) : Response<AssignedTree>
}