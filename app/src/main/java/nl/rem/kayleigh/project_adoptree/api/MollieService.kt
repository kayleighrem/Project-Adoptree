package nl.rem.kayleigh.project_adoptree.api

import retrofit2.http.Field
import retrofit2.http.Headers
import retrofit2.http.POST

interface MollieService {
    @Headers()
    @POST("https://api.mollie.com/v2/payments")
    suspend fun pay(@Field("amount") username: String,
                    @Field("description") description: String,
                    @Field("redirectUrl") redirectUrl: String,
                    @Field("webhookUrl") webhookUrl: String,
                    @Field("metadata") metadata: String)
}