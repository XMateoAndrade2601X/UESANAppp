package com.example.uesanapp.data.remote.apifootball

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://v3.football.api-sports.io/"
private const val API_KEY = "b6177a72452bab38c2942e5801fc7e39" // El usuario debe poner su clave aquí

object RetrofitInstance {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-apisports-key", API_KEY)
                .build()
            chain.proceed(request)
        }
        .build()

    val api: ApiFootballService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiFootballService::class.java)
    }
}
