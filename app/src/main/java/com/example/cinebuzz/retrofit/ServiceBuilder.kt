package com.example.cinebuzz.retrofit

import com.example.cinebuzz.SplashScreen.Companion.BASEURL
import com.example.cinebuzz.SplashScreen.Companion.TOKEN
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
//        .client(OkHttpClient.Builder().addInterceptor { chain ->
//            val request = chain.request().newBuilder().addHeader("Authorization", "Bearer ${TOKEN}").build()
//            chain.proceed(request)
//        }.build())
        .build()

    fun buildService():ApiInterface{
        return retrofit.create(ApiInterface::class.java)
    }
}
