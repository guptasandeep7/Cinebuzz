package com.example.cinebuzz.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

//    @GET("login")
//    fun getData(@Query("api_key")api_key:String): Call<MyDataItem>

    //post/login  -> email , pass            -> welcome  ,  incorrect email/pass
    //post/signup  -> name , email        -> otp send
    //post/otp    -> email , enterotp      ->  otp verified  , verification failed
    //put/password  -> email , pass      -> password must be same! , password set


    @POST("signup")
    fun signup(@Body data: MyDataItem): Call<ResponseBody>


    @POST("login")
    fun login(@Body value:MyDataItem): Call<ResponseBody>

    @POST("forgot")
    fun verify(@Body email:MyDataItem): Call<ResponseBody>
  
    @POST("otp")
    fun otp(@Body data: MyDataItem): Call<ResponseBody>

    @PUT("password")
    fun password(@Body data: MyDataItem): Call<ResponseBody>

    @PUT("resetpass")
    fun resetPassword(@Body data: MyDataItem): Call<ResponseBody>

    @GET("Premiere")
    fun latest():Call<List<Latest>>

    @GET("trending")
    fun trending():Call<List<MoviesDataItem>>

    @GET("history")
    fun history():Call<List<SearchHistoryDataItem>>

    @GET("comedy")
    fun  comedy():Call<List<MoviesDataItem>>

    @GET("action")
    fun  action():Call<List<MoviesDataItem>>

    @GET("horror")
    fun  horror():Call<List<MoviesDataItem>>

    @GET("drama")
    fun  drama():Call<List<MoviesDataItem>>

    @POST("search")
    fun search(@Body data:MoviesDataItem): Call<List<MoviesDataItem>>
}