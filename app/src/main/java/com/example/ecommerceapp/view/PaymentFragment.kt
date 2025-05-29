package com.example.ecommerceapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentPaymentBinding
import com.example.ecommerceapp.models.local.CartDatabase
import com.example.ecommerceapp.models.local.OrderDatabase
import com.example.ecommerceapp.models.local.OrderEntity


class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnProceed.setOnClickListener {
            val selectedId = binding.radioGroup.checkedRadioButtonId
            if (selectedId == -1) {
                Toast.makeText(requireContext(), "Please select a payment method", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedRadio = view.findViewById<RadioButton>(selectedId)
            val paymentMethod = selectedRadio.text.toString()

            val cartDb = CartDatabase.getInstance(requireContext())
            val orderDb = OrderDatabase.getInstance(requireContext())

            Thread {
                val cartItems = cartDb.cartDao().getAllItems()
                val orderBatchId = System.currentTimeMillis()

                cartItems.forEach { item ->
                    val order = OrderEntity(
                        productName = item.name,
                        productPrice = item.price,
                        quantity = item.quantity,
                        address = "123 Main Street",  // Dummy or use saved
                        state = "Springfield, MO",    // Dummy or use saved
                        paymentMethod = paymentMethod,
                        orderBatchId = orderBatchId
                    )
                    orderDb.orderDao().insertOrder(order)
                }

                cartDb.cartDao().clearCart()

                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Order placed via $paymentMethod", Toast.LENGTH_SHORT).show()

                    // Navigate to Orders screen
                    parentFragment?.childFragmentManager?.beginTransaction()
                        ?.replace(R.id.frameContainer, OrderFragment())
                        ?.addToBackStack(null)
                        ?.commit()
                }
            }.start()
        }
    }
}