package com.example.ecommerceapp.models

data class Product(
    val name: String,
    val description: String,
    val price: Double,
    val imageResId: Int,
    val rating: Double
)
