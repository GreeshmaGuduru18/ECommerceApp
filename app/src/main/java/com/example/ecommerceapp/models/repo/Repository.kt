package com.example.ecommerceapp.models.repo

import com.example.ecommerceapp.models.LoginRequest
import com.example.ecommerceapp.models.LoginResponse
import com.example.ecommerceapp.models.RegisterRequest
import com.example.ecommerceapp.models.RegisterResponse
import retrofit2.Call

interface Repository {
    fun loginUser(request: LoginRequest): Call<LoginResponse>
    fun registerUser(request: RegisterRequest): Call<RegisterResponse>
}