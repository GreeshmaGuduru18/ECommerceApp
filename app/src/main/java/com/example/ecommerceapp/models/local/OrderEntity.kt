package com.example.ecommerceapp.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val productName: String,
    val productPrice: Double,
    val quantity: Int,
    val address: String,
    val state: String,
    val orderBatchId: Long
)