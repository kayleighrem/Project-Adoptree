package nl.rem.kayleigh.project_adoptree.api

import com.squareup.moshi.Json
import nl.rem.kayleigh.project_adoptree.model.*
import retrofit2.Response
import retrofit2.http.*

interface UserService {
    @Headers("Content-Type: application/json")
    @POST("auth/register")
    suspend fun register(@Body user: User) : Response<UserResponseRegister>

    @Headers("Content-Type: application/json")
    @POST("auth/logout")
    suspend fun logout()

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    suspend fun login(
            @Body user: UserLogin
    ) : Response<LoginResponse>

    @Headers("Content-Type: application/json")
    @GET("loggedinuser")
    suspend fun getLoggedInUser(@Header("Authorization") token: String) : Response<User>

    @Headers("Content-Type: application/json")
    @POST("auth/refreshToken")
    suspend fun newTokens(@Header("Authorization") token: String) : Response<RefreshTokenResponse>

    @POST("user/forgetpassword")
    suspend fun forgotpassword(
        @Field("username") username: String,
        @Field("email") email: String
    )

    @POST("user/resetpassword")
    suspend fun resetpassword(
        @Field("token") token: String,
        @Field("password") password: String,
        @Field("validate_password") validate_password: String
    )

    @Headers("Content-Type: application/json")
    @GET("user/trees")
    suspend fun getTreesByUser(@Header("Authorization") token: String) : Response<List<Tree>>

    @PUT("user")
    suspend fun updateUser(
        @Field("firstname") firstname: String,
        @Field("lastname") lastname: String,
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    )
}