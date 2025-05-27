package com.example.ecommerceapp.remote

import com.example.ecommerceapp.models.CategoryResponse
import com.example.ecommerceapp.models.LoginRequest
import com.example.ecommerceapp.models.LoginResponse
import com.example.ecommerceapp.models.RegisterRequest
import com.example.ecommerceapp.models.RegisterResponse
import com.example.ecommerceapp.models.local.CartItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

interface ApiService {
    @POST("User/auth")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("User/register")
    fun registerUser(@Body registerRequest: RegisterRequest):
            Call<RegisterResponse>

    @GET("Category")
    fun getCategories(): Call<CategoryResponse>

    @POST("add-to-cart")
    fun addToCart(@Body item: CartItem): Call<Boolean>

    @GET("cart-items")
    fun getCartItems(): Call<List<CartItem>>


    @HTTP(method = "DELETE", path = "delete-cart-item", hasBody = true)
    fun deleteCartItem(@Body item: CartItem): Call<Boolean>

    @DELETE("clear-cart")
    fun clearCart(): Call<Boolean>

}