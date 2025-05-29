package com.example.ecommerceapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.FragmentCheckoutParentBinding

class CheckoutParentFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutParentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutParentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadStepFragment(CheckoutFragment())
        updateTabSelection(binding.tvCartItems)

        binding.tvCartItems.setOnClickListener {
            loadStepFragment(CheckoutFragment())
            updateTabSelection(binding.tvCartItems)
        }

        binding.tvDelivery.setOnClickListener {
            loadStepFragment(AddressFragment())
            updateTabSelection(binding.tvDelivery)
        }

        binding.tvPayment.setOnClickListener {
            loadStepFragment(PaymentFragment())
            updateTabSelection(binding.tvPayment)
        }

        binding.tvSummary.setOnClickListener {
            loadStepFragment(OrderFragment())
            updateTabSelection(binding.tvSummary)
        }

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun loadStepFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, fragment)
            .commit()
    }

    private fun updateTabSelection(selectedTab: TextView) {
        val tabs = listOf(binding.tvCartItems, binding.tvDelivery, binding.tvPayment, binding.tvSummary)

        for (tab in tabs) {
            if (tab == selectedTab) {
                tab.setBackgroundResource(R.drawable.tab_selected)
            } else {
                tab.setBackgroundResource(R.drawable.tab_unselected)
            }
        }
    }
}
