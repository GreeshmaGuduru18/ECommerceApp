package com.example.ecommerceapp.models

import com.google.gson.annotations.SerializedName

data class Product(
    val id : String,
    val name: String,
    val description: String,
    val price: String,
    val imageResId: String,
    val rating: String
)