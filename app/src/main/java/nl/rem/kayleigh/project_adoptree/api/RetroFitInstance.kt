package nl.rem.kayleigh.project_adoptree.api

import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import nl.rem.kayleigh.project_adoptree.util.Constants.Companion.BASE_URL
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class RetrofitInstance {
    companion object {

        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES).addInterceptor(logging).build()

            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build()
        }

        val api: AdoptreeService by lazy {
            retrofit.create(AdoptreeService::class.java)
        }

        val userapi: UserService by lazy {
            retrofit.create(UserService::class.java)
        }
    }
}