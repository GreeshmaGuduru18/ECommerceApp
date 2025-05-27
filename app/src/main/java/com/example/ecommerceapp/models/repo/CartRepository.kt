package com.example.ecommerceapp.models.repo

import com.example.ecommerceapp.models.local.CartItem
import retrofit2.Call

interface CartRepository {
    fun insertItem(item: CartItem)
    fun getAllItems(): List<CartItem>
    fun deleteItem(item: CartItem)
    fun clearCart()
}
