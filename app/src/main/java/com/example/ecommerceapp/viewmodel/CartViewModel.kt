package com.example.ecommerceapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerceapp.models.local.CartDatabase
import com.example.ecommerceapp.models.local.CartItem
import kotlin.concurrent.thread

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val cartDao = CartDatabase.getInstance(application).cartDao()

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun getAllItems() {
        thread {
            val items = cartDao.getAllItems()
            _cartItems.postValue(items)
        }
    }

    fun insertItem(item: CartItem) {
        thread {
            try {
                cartDao.insertItem(item)
                _message.postValue("Item added to cart")
                getAllItems()
            } catch (e: Exception) {
                _message.postValue("Insert failed: ${e.message}")
            }
        }
    }

    fun deleteItem(item: CartItem) {
        thread {
            cartDao.deleteCartItem(item)
            getAllItems()
        }
    }

    fun clearCart() {
        thread {
            cartDao.clearCart()
            getAllItems()
        }
    }
}
