package com.example.ecommerceapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerceapp.models.LoginRequest
import com.example.ecommerceapp.models.LoginResponse
import com.example.ecommerceapp.models.repo.RepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val repository: RepositoryImpl) : ViewModel() {

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun loginUser(request: LoginRequest) {
        repository.loginUser(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful && response.body()?.status == 0) {
                    _loginResponse.postValue(response.body())
                } else {
                    _errorMessage.postValue("Invalid username or password")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _errorMessage.postValue("Login failed: ${t.message}")
            }
        })
    }
}
