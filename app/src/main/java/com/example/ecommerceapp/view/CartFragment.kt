package com.example.ecommerceapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentCartBinding
import com.example.ecommerceapp.models.local.CartItem
import com.example.ecommerceapp.models.repo.CartRepositoryImpl
import com.example.ecommerceapp.viewmodel.CartViewModel
import com.example.ecommerceapp.viewmodel.createFactory

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartViewModel: CartViewModel
    private var cartList: MutableList<CartItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.title = "Your Cart"

        cartViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[CartViewModel::class.java]

        binding.recyclerCart.layoutManager = LinearLayoutManager(requireContext())

        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            cartList = items.toMutableList()
            binding.recyclerCart.adapter = CartAdapter(
                cartItems = cartList,
                onQuantityChanged = { updateTotalBill() },
                onDeleteClicked = { itemToDelete ->
                    cartViewModel.deleteItem(itemToDelete)
                }
            )
            updateTotalBill()
        }

        cartViewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        binding.btnCheckout.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, CheckoutFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        cartViewModel.getAllItems()
    }

    private fun updateTotalBill() {
        val total = cartList.sumOf { it.price * it.quantity }
        binding.tvBillAmount.text = "Total Bill Amount: $${"%.2f".format(total)}"
    }
}
