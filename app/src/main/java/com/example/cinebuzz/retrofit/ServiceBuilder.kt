package com.example.cinebuzz.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://2858-2401-4900-30ca-d9dd-6cb8-4119-4716-d764.ngrok.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun buildService():ApiInterface{
        return retrofit.create(ApiInterface::class.java)
    }
}
