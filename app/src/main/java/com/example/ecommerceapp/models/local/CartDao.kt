package com.example.ecommerceapp.models.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface CartDao {
    @Insert
    fun insertItem(item: CartItem)

    @Query("SELECT * FROM cart_items")
    fun getAllItems(): List<CartItem>

    @Delete
    fun deleteCartItem(item: CartItem)

    @Query("DELETE FROM cart_items")
    fun clearCart()
}