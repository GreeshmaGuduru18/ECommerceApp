package com.example.ecommerceapp.view

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentRegisterBinding
import com.example.ecommerceapp.models.RegisterRequest
import com.example.ecommerceapp.models.repo.RepositoryImpl
import com.example.ecommerceapp.viewmodel.RegisterViewModel
import com.example.ecommerceapp.viewmodel.createFactory

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = RegisterViewModel(
            RepositoryImpl(ApiClient.getApiService())
        ).createFactory()

        viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]


        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val phone = binding.etMobileNum.text.toString()
            val password = binding.etPassword.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty()) {
                val request = RegisterRequest(name, email, phone, password)
                viewModel.registerUser(request)
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvLoginLink.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, LoginFragment())
                .addToBackStack(null)
                .commit()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.registerResult.observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(requireContext(), "Registered: ${it.message}", Toast.LENGTH_SHORT).show()

                val sharedPref = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("name", binding.etName.text.toString())
                    putString("email", binding.etEmail.text.toString())
                    putString("phone", binding.etMobileNum.text.toString())
                    apply()
                }

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, LoginFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
}
