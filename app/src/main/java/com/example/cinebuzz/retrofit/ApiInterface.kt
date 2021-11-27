package com.example.cinebuzz.retrofit

import com.example.cinebuzz.dashboard.profile.ReviewDataItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @POST("signup")
    fun signup(@Body data: MyDataItem): Call<ResponseBody>

    @POST("login")
    fun login(@Body value: MyDataItem): Call<MyDataItem>

    @POST("forgot")
    fun verify(@Body email: MyDataItem): Call<ResponseBody>

    @POST("otp")
    fun otp(@Body data: MyDataItem): Call<ResponseBody>

    @PUT("password")
    fun password(@Body data: MyDataItem): Call<MyDataItem>

    @PUT("resetpass")
    fun resetPassword(@Body data: MyDataItem): Call<MyDataItem>

    @GET("Premiere")
    fun latest(): Call<List<Latest>>

    @GET("trending")
    fun trending(): Call<List<MoviesDataItem>>

    @GET("history")
    fun history(): Call<List<SearchHistoryDataItem>>

    @GET("action")
    fun action(@Query("genre") genre: String): Call<List<MoviesDataItem>>

    @POST("random")
    fun random(@Body genre: MoviesDataItem): Call<MoviesDataItem>

    @POST("search")
    fun search(@Body data: MoviesDataItem): Call<List<MoviesDataItem>>

    @POST("movie")
    fun movie(@Body data: MoviesDataItem): Call<MoviesDataItem>

    @POST("movie/rating")
    fun rating(@Body data: WishlistDataItem): Call<String>

    @PUT("movie/wishlist")
    fun wishlistToggle(@Body data: WishlistDataItem): Call<ResponseBody>

    @POST("movie/wishlist")
    fun wishlistCheck(@Body data: WishlistDataItem): Call<String>

    @POST("wishlist")
    fun wishlistAll(@Body data: WishlistDataItem): Call<ArrayList<String>>

    @POST("refreshlist")
    fun refreshList(@Body data: WishlistDataItem): Call<ResponseBody>

    @POST("movie/history")
    fun history(@Body data: WishlistDataItem): Call<String>

    @POST("history")
    fun showHistory(@Body data: WishlistDataItem): Call<ArrayList<String>>

    @POST("count")
    fun movieCount(@Body data: WishlistDataItem): Call<String>

    @POST("delete/history")
    fun deleteHistory(@Body data: WishlistDataItem): Call<String>

    @POST("changepass")
    fun changePassword(@Body data: MyDataItem): Call<String>

    @PUT("movie/rating")
    fun sendRating(@Body data: WishlistDataItem): Call<ResponseBody>

    @POST("movie/rating/your")
    fun getRating(@Body data: WishlistDataItem): Call<String>

    @POST("movie/review")
    fun showReview(@Body data: WishlistDataItem): Call<ArrayList<WishlistDataItem>>

    @PUT("movie/review")
    fun sendReview(@Body data: WishlistDataItem): Call<ResponseBody>

    @POST("userDetails")
    fun userDetails(@Body data: WishlistDataItem): Call<ReviewDataItem>

    @POST("feedback")
    fun feedback(@Body data: MyDataItem): Call<String>

    @Multipart
    @PATCH("dp")
    fun changeDp(
        @Part("email") email: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<String>

}