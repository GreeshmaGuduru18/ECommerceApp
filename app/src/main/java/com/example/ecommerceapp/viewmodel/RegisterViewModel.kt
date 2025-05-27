package com.example.ecommerceapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerceapp.models.RegisterRequest
import com.example.ecommerceapp.models.RegisterResponse
import com.example.ecommerceapp.models.repo.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val repository: Repository) : ViewModel() {

    private val _registerResult = MutableLiveData<RegisterResponse?>()
    val registerResult: LiveData<RegisterResponse?> = _registerResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun registerUser(request: RegisterRequest) {
        repository.registerUser(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    _registerResult.value = response.body()
                } else {
                    _error.value = "Registration failed: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _error.value = "Registration error: ${t.message}"
            }
        })
    }
}
