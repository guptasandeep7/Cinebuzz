package com.example.cinebuzz.retrofit

import com.example.cinebuzz.SplashScreen.Companion.BASEURL
import com.example.cinebuzz.SplashScreen.Companion.TOKEN
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder2 {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASEURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun buildService():ApiInterface{
        return retrofit.create(ApiInterface::class.java)
    }
}
