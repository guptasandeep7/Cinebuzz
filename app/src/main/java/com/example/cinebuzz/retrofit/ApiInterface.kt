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
    fun rating(@Body data:WishlistDataItem):Call<String>

    @PUT("movie/wishlist")
    fun wishlistToggle(@Body data:WishlistDataItem): Call<ResponseBody>

    @POST("movie/wishlist")
    fun wishlistCheck(@Body data: WishlistDataItem): Call<String>

    @POST("wishlist")
    fun wishlistAll(@Body data: WishlistDataItem): Call<ArrayList<String>>

    @GET("refreshlist")
    fun refreshList():Call<ResponseBody>

    @POST("movie/history")
    fun history(@Body data: WishlistDataItem):Call<String>

    @POST("history")
    fun showHistory(@Body data: WishlistDataItem): Call<ArrayList<String>>

    @POST("count")
    fun movieCount(@Body data: WishlistDataItem):Call<String>

    @POST("delete/history")
    fun deleteHistory(@Body data: WishlistDataItem):Call<String>

    @POST("changepass")
    fun changePassword(@Body data: MyDataItem): Call<String>

    @POST("feedback")
    fun feedback(@Body data: MyDataItem): Call<String>
}