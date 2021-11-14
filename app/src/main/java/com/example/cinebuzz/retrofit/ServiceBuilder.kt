package com.example.cinebuzz.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://fc42-2401-4900-445b-bca7-69a5-8c66-fba7-3f0b.ngrok.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun buildService():ApiInterface{
        return retrofit.create(ApiInterface::class.java)
    }
}
