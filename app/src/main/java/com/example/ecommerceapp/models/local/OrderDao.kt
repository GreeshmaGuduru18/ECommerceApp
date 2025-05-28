package com.example.ecommerceapp.models.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderDao {
    @Insert
    fun insertOrder(order: OrderEntity)

    @Query("SELECT * FROM orders")
    fun getAllOrders(): List<OrderEntity>
}