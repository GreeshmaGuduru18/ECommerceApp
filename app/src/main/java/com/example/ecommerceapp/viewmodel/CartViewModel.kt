package com.example.ecommerceapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerceapp.models.Product
import com.example.ecommerceapp.models.ProductResponse
import com.example.ecommerceapp.models.local.CartDatabase
import com.example.ecommerceapp.models.local.CartItem
import com.example.ecommerceapp.models.repo.Repository
import com.example.ecommerceapp.models.repo.RepositoryImpl
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CartViewModel(application: Application) : AndroidViewModel(application) {


    private val repository: Repository = RepositoryImpl(ApiClient.getApiService())
    private val cartDao = CartDatabase.getInstance(application).cartDao()

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    val productsLiveData = MutableLiveData<List<Product>>()

    fun fetchProducts(subCategoryId: String) {
        val call = repository.getProductsBySubCategory(subCategoryId)
        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val productJsonString = response.body()!!.products

                    try {
                        val jsonArray = JSONArray(productJsonString)
                        val productList = mutableListOf<Product>()

                        for (i in 0 until jsonArray.length()) {
                            val obj = jsonArray.getJSONObject(i)
                            val product = Product(
                                id = obj.getString("product_id"),
                                name = obj.getString("product_name"),
                                description = obj.getString("description"),
                                price = obj.getString("price"),
                                imageResId = obj.getString("product_image_url"),
                                rating = obj.getString("average_rating")
                            )
                            productList.add(product)
                        }

                        productsLiveData.postValue(productList)

                    } catch (e: Exception) {
                        _message.postValue("Parsing failed: ${e.message}")
                    }
                } else {
                    _message.postValue("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                _message.postValue("Failed to load products: ${t.message}")
            }
        })
    }


    fun insertItem(item: CartItem) {
        Thread {
            cartDao.insertItem(item)
            _message.postValue("${item.name} added to cart")
            loadCartItems()
        }.start()
    }

    fun deleteItem(item: CartItem) {
        Thread {
            cartDao.deleteCartItem(item)
            _message.postValue("${item.name} removed from cart")
            loadCartItems()
        }.start()
    }


    private fun loadCartItems() {
        Thread {
            val items = cartDao.getAllItems()
            _cartItems.postValue(items)
        }.start()
    }

    fun getAllItems() {
        loadCartItems()
    }
}