package com.example.finalassignment.API

import com.example.finalassignment.ENTITY.User
import com.example.finalassignment.Response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserAPI {
    //register user
    @POST("registration/insert")
    suspend fun registerUser(
        @Body user: User
    ): Response<LoginResponse>

    //login user
    @FormUrlEncoded
    @POST("user/login")
    suspend fun checkUser(
        @Field("username") username:String,
        @Field("password") password:String
    ):Response<LoginResponse>
}