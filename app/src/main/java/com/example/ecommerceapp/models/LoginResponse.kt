package com.example.ecommerceapp.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val status: Int,
    val message: String,
    val user: User
)

data class User(
    @SerializedName("user_id")
    val userId: String,

    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("mobile_no")
    val mobileNo: String,

    @SerializedName("email_id")
    val emailId: String
)