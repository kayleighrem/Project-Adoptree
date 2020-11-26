package nl.rem.kayleigh.project_adoptree.api

import nl.rem.kayleigh.project_adoptree.model.Tree
import nl.rem.kayleigh.project_adoptree.model.User
import nl.rem.kayleigh.project_adoptree.model.UserResponse
import nl.rem.kayleigh.project_adoptree.model.UserResponseRegister
import retrofit2.Response
import retrofit2.http.*

interface AdoptreeService {


    @Headers("Content-Type: application/json")
    @POST("Users/register")
    suspend fun createUser(@Body user: User): Response<UserResponseRegister>

    @Headers("Content-Type: application/json")
    @POST("user/login")
    suspend fun login(@Body user: User): Response<UserResponse>

    @GET("tree")
    suspend fun getAllTrees(@Header("x-authtoken") authToken: String): Response<Tree>

//    @Headers("Content-Type: application/json")
//    @POST("user")
//    suspend fun register(@Body user: User): Response<UserResponseRegister>
//
//    @Headers("Content-Type: application/json")
//    @POST("user/{id}")
//    suspend fun login(@Path("id") id: Int, @Body user: User): Response<UserResponse>

//    @FormUrlEncoded
//    @POST("user/{id}")
//    fun login(
//        @Field("UserName") username: String,
//        @Field("Password") password: String
//    ): Call<LoginResponse>
//
//    @FormUrlEncoded
//    @POST("user")
//    fun register(
//        @Field("firstname") firstname: String,
//        @Field("lastname") lastname: String,
//        @Field("username") username: String,
//        @Field("password") password: String,
//        @Field("forgetToken") token: String,
//        @Field("role") role: Int,
//        @Field("createdAt") createdAt: LocalDateTime
//    ): Call<RegisterResponse>
}