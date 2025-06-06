package com.example.ecommerceapp.remote

import com.example.ecommerceapp.models.CategoryResponse
import com.example.ecommerceapp.models.LoginRequest
import com.example.ecommerceapp.models.LoginResponse
import com.example.ecommerceapp.models.Product
import com.example.ecommerceapp.models.ProductResponse
import com.example.ecommerceapp.models.RegisterRequest
import com.example.ecommerceapp.models.RegisterResponse
import com.example.ecommerceapp.models.local.CartItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("User/auth")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("User/register")
    fun registerUser(@Body registerRequest: RegisterRequest):
            Call<RegisterResponse>

    @GET("SubCategory/products/{sub_category_id}")
    fun getProductsBySubCategory(
        @Path("sub_category_id") subCategoryId: String
    ): Call<ProductResponse>

    @GET("category")
    fun getCategories(): Call<CategoryResponse>

}