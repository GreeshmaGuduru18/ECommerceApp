package com.example.ecommerceapp.models

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email_id") val emailId: String,
    val password: String
)
