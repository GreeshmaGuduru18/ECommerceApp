package com.example.ecommerceapp.models

data class ProductResponse(
    val status: Int,
    val message: String,
    val products: List<Product>
)