package com.example.ecommerceapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentProductBinding
import com.example.ecommerceapp.models.Product
import com.example.ecommerceapp.models.local.CartItem
import com.example.ecommerceapp.models.repo.CartRepositoryImpl
import com.example.ecommerceapp.viewmodel.CartViewModel
import com.example.ecommerceapp.viewmodel.createFactory

class ProductFragment : Fragment() {

    private lateinit var binding: FragmentProductBinding
    private lateinit var cartViewModel: CartViewModel
    private val productList = mutableListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)



        cartViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[CartViewModel::class.java]

        // Observe toast messages
        cartViewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        // Read category from arguments
        val category = arguments?.getString("category") ?: "Smart Phones"
        binding.tvTitle.text = category

        // Clear existing list
        productList.clear()

        // Load dummy products based on category
        when (category) {
            "Smart Phones" -> {
                productList.add(Product("RealMe Nazro 50", "Helio G96 Processor, 4GB RAM+64GB", 200.0, R.drawable.phone1, 4.5))
                productList.add(Product("Redmi Note 11T", "Dimensity 810 5G, 6GB RAM, 128GB ROM", 220.0, R.drawable.phone2, 4.6))
                productList.add(Product("Xiaomi 11 Lite NE 5G", "Slimmest & Lightest 5G", 300.0, R.drawable.phonw3, 4.4))
                productList.add(Product("Redmi 9A", "Helio G25 Processor, 5000mAh Battery", 249.0, R.drawable.phone4, 4.2))
                productList.add(Product("Redmi A3x", "6.71\" Display, 64GB Storage", 179.0, R.drawable.phone5, 4.3))
                productList.add(Product("OnePlus Nord CE 3 Lite", "Snapdragon, 67W SUPERVOOC", 325.0, R.drawable.phone6, 4.7))
            }


            else -> {
                productList.add(Product("No Products Available", "Please select a valid category.", 0.0, R.drawable.ic_launcher_background, 0.0))
            }
        }

        // RecyclerView setup
        binding.recyclerProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerProducts.adapter = ProductAdapter(productList) { selectedProduct ->
            val cartItem = CartItem(
                id = 0,
                name = selectedProduct.name,
                price = selectedProduct.price,
                quantity = 1,
                imageResId = selectedProduct.imageResId
            )
            cartViewModel.insertItem(cartItem)
        }
    }

}
