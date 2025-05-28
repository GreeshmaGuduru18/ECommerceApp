package com.example.ecommerceapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecommerceapp.models.local.OrderDatabase
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapp.databinding.FragmentOrderBinding


class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewOrders.layoutManager = LinearLayoutManager(requireContext())

        Thread {
            val allOrders = OrderDatabase.getInstance(requireContext()).orderDao().getAllOrders()

            val latestBatchId = allOrders.maxOfOrNull { it.orderBatchId }

            val recentOrders = allOrders.filter { it.orderBatchId == latestBatchId }

            requireActivity().runOnUiThread {
                binding.recyclerViewOrders.adapter = OrdersAdapter(recentOrders)
            }
        }.start()
    }
}