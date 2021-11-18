package com.example.cinebuzz.retrofit

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @POST("signup")
    fun signup(@Body data: MyDataItem): Call<ResponseBody>

    @POST("login")
    fun login(@Body value:MyDataItem): Call<MyDataItem>

    @POST("forgot")
    fun verify(@Body email:MyDataItem): Call<ResponseBody>
  
    @POST("otp")
    fun otp(@Body data: MyDataItem): Call<ResponseBody>

    @PUT("password")
    fun password(@Body data: MyDataItem): Call<MyDataItem>

    @PUT("resetpass")
    fun resetPassword(@Body data: MyDataItem): Call<MyDataItem>

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

    @POST("random")
    fun random(@Body genre:MoviesDataItem):Call<MoviesDataItem>

    @POST("search")
    fun search(@Body data:MoviesDataItem): Call<List<MoviesDataItem>>

    @POST("movie")
    fun movie(@Body data:MoviesDataItem): Call<MoviesDataItem>

    @POST("movie/rating")
    fun rating(@Body Movieid:String):Call<ResponseBody>

    @PUT("movie/wishlist")
    fun wishlist(@Body data:WishlistDataItem): Call<ResponseBody>

    @POST("movie/wishlist")
    fun wishlist1(@Body data: WishlistDataItem): Call<ResponseBody>

    @POST("wishlist")
    fun wishlist2(@Body data: WishlistDataItem): Call<ArrayList<String>>
}