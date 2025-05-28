package com.example.ecommerceapp.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        val fragment = if (isLoggedIn) DashboardFragment() else LoginFragment()

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, LoginFragment())
                .commit()


    }

    fun showLogo() {
        findViewById<ImageView>(R.id.ivLogo).visibility = View.VISIBLE
    }

    fun hideLogo() {
        findViewById<ImageView>(R.id.ivLogo).visibility = View.GONE
    }


}