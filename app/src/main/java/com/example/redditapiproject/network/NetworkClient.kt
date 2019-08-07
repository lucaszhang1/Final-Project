package com.example.redditapiproject.network

import okhttp3.Interceptor
import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {

    private val BASE_URL = "https://reddit.com/"
    private const val API_KEY = "ALKFJDLFJAKLDJFLDKJFDLKJFDKL"
    private val TIMEOUT = 10
    var retrofit: Retrofit? = null
    /*
    This public static method will return Retrofit client
    anywhere in the appplication
    */
    val retrofitClient: Retrofit
        get() {
            if (retrofit == null) {
                val okHttpClientBuilder = OkHttpClient.Builder()
                    .addInterceptor(apiKeyInterceptor())
                    .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
//                okHttpClientBuilder.connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClientBuilder.build())
                    .build()
            }
            return retrofit!!
        }

    private fun apiKeyInterceptor() = injectQueryParams(
        "api_key" to API_KEY
    )

    private fun injectQueryParams(
        vararg params: Pair<String, String>
    ): Interceptor = Interceptor { chain ->

        val originalRequest = chain.request()
        val urlWithParams = originalRequest.url().newBuilder()
            .apply { params.forEach { addQueryParameter(it.first, it.second) } }
            .build()
        val newRequest = originalRequest.newBuilder().url(urlWithParams).build()

        chain.proceed(newRequest)
    }

}
