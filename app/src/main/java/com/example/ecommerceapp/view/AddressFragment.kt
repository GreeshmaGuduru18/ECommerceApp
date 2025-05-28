package com.example.ecommerceapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        binding.btnCod.setOnClickListener {
            val address = binding.etAddress.text.toString()
            val state = binding.etState.text.toString()
            Toast.makeText(requireContext(), "COD Selected for $address, $state", Toast.LENGTH_SHORT).show()
        }

        binding.btnPlaceOrder.setOnClickListener {
            val address = binding.etAddress.text.toString()
            val state = binding.etState.text.toString()

            if (address.isBlank() || state.isBlank()) {
                Toast.makeText(requireContext(), "Please enter address and state", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cartDb = CartDatabase.getInstance(requireContext())
            val orderDb = OrderDatabase.getInstance(requireContext())

            Thread {
                val cartItems = cartDb.cartDao().getAllItems()

                val batchId = System.currentTimeMillis()
                cartItems.forEach { item ->
                    val order = OrderEntity(
                        productName = item.name,
                        productPrice = item.price,
                        quantity = item.quantity,
                        address = address,
                        state = state,
                        orderBatchId = batchId
                    )
                    orderDb.orderDao().insertOrder(order)
                }

                cartDb.cartDao().clearCart()

                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Order Placed Successfully!", Toast.LENGTH_SHORT).show()

                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, OrderFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }.start()
        }
    }
}