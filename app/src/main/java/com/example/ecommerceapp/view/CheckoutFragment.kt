package com.example.ecommerceapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentCheckoutBinding
import com.example.ecommerceapp.models.local.CartItem
import com.example.ecommerceapp.viewmodel.CartViewModel

class CheckoutFragment : Fragment() {

    private lateinit var binding: FragmentCheckoutBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var checkoutAdapter: CheckoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        binding.recyclerCheckout.layoutManager = LinearLayoutManager(requireContext())
        checkoutAdapter = CheckoutAdapter(emptyList())
        binding.recyclerCheckout.adapter = checkoutAdapter

        // Setup ViewModel
        cartViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[CartViewModel::class.java]

        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            checkoutAdapter.updateList(items)
            updateTotalBill(items)
        }

        cartViewModel.getAllItems()

        // Navigate to AddressFragment (Delivery step)
        binding.btnNext.setOnClickListener {
            parentFragment?.childFragmentManager?.beginTransaction()
                ?.replace(R.id.frameContainer, AddressFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    private fun updateTotalBill(cartItems: List<CartItem>) {
        val total = cartItems.sumOf { it.price * it.quantity }
        binding.tvBillAmount.text = "Total Bill Amount: $${"%.2f".format(total)}"
        binding.tvBillAmount.setTextColor(resources.getColor(android.R.color.holo_red_dark))
    }
}