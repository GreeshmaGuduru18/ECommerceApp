package com.example.ecommerceapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentLoginBinding
import com.example.ecommerceapp.models.LoginRequest
import com.example.ecommerceapp.models.repo.RepositoryImpl
import com.example.ecommerceapp.viewmodel.LoginViewModel
import com.example.ecommerceapp.viewmodel.createFactory

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? LoginActivity)?.findViewById<ImageView>(R.id.ivLogo)?.visibility = View.VISIBLE

        val factory = LoginViewModel(
            RepositoryImpl(ApiClient.getApiService())
        ).createFactory()

        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]


        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val request = LoginRequest(email, password)
                viewModel.loginUser(request)
            } else {
                Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvRegisterLink.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, RegisterFragment())
                .addToBackStack(null)
                .commit()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loginResult.observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(requireContext(), "Login Success: ${it.message}", Toast.LENGTH_SHORT).show()


                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, DashboardFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
}
