package com.example.ecommerceapp.models

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
   val status: Int,
   val message: String,
   val categories : List<Category>

)

data class Category(
    @SerializedName("category_id")
    val categoryId : String,

    @SerializedName("category_name")
    val categoryName : String,

    @SerializedName("category_image_url")
    val categoryImageUrl : String,

    @SerializedName("is_active")
    val isActive : String
)
