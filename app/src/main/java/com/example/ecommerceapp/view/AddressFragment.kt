package com.example.ecommerceapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import com.example.ecommerceapp.models.local.CartDatabase
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentAddressBinding
import com.example.ecommerceapp.models.local.OrderDatabase
import com.example.ecommerceapp.models.local.OrderEntity

class AddressFragment : Fragment() {

    private lateinit var binding: FragmentAddressBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle next button click
        binding.btnNextToPayment.setOnClickListener {
            val selectedId = binding.radioGroupAddress.checkedRadioButtonId
            if (selectedId == -1) {
                Toast.makeText(requireContext(), "Please select a delivery address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedRadio = view.findViewById<RadioButton>(selectedId)
            val address = selectedRadio.text.toString()

            Toast.makeText(requireContext(), "Address Selected: $address", Toast.LENGTH_SHORT).show()


            parentFragment?.childFragmentManager?.beginTransaction()
                ?.replace(R.id.frameContainer, PaymentFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}