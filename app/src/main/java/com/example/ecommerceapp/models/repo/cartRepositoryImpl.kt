package com.example.ecommerceapp.models.repo

import android.content.Context
import com.example.ecommerceapp.models.local.CartDatabase
import com.example.ecommerceapp.models.local.CartItem

class CartRepositoryImpl(context: Context) : CartRepository {

    private val cartDao = CartDatabase.getInstance(context).cartDao()

    override fun insertItem(item: CartItem) {
        cartDao.insertItem(item)
    }

    override fun getAllItems(): List<CartItem> {
        return cartDao.getAllItems()
    }

    override fun deleteItem(item: CartItem) {
        cartDao.deleteCartItem(item)
    }

    override fun clearCart() {
        cartDao.clearCart()
    }
}
