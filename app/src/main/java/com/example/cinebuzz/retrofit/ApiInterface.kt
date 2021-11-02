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
}