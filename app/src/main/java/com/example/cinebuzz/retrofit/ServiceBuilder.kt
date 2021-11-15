package com.example.cinebuzz.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://cine---buzz.herokuapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun buildService():ApiInterface{
        return retrofit.create(ApiInterface::class.java)
    }
}
