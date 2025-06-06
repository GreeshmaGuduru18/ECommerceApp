package com.example.ecommerceapp.models.repo

import com.example.ecommerceapp.models.LoginRequest
import com.example.ecommerceapp.models.LoginResponse
import com.example.ecommerceapp.models.Product
import com.example.ecommerceapp.models.ProductResponse
import com.example.ecommerceapp.models.RegisterRequest
import com.example.ecommerceapp.models.RegisterResponse
import com.example.ecommerceapp.remote.ApiService
import retrofit2.Call

class RepositoryImpl(private val apiService: ApiService) : Repository {
    override fun loginUser(request: LoginRequest): Call<LoginResponse> {
        return apiService.loginUser(request)
    }

    override fun registerUser(request: RegisterRequest): Call<RegisterResponse> {
        return apiService.registerUser(request)
    }

    override fun getProductsBySubCategory(subCategoryId: String): Call<ProductResponse> {
        return apiService.getProductsBySubCategory(subCategoryId)
    }
}