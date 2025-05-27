package com.example.ecommerceapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.databinding.ItemCartBinding
import com.example.ecommerceapp.models.local.CartItem

class CartAdapter(
    private val cartItems: List<CartItem>,
    private val onQuantityChanged: (CartItem) -> Unit,
    private val onDeleteClicked: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]

        holder.binding.ivCartImage.setImageResource(item.imageResId)
        holder.binding.tvName.text = item.name
        holder.binding.tvUnitPrice.text = "Unit Price: $${item.price}"
        holder.binding.tvQuantity.text = item.quantity.toString()
        holder.binding.tvTotalPrice.text = "$${item.price * item.quantity}"

        holder.binding.btnPlus.setOnClickListener {
            item.quantity += 1
            notifyItemChanged(position)
            onQuantityChanged(item)
        }

        holder.binding.btnMinus.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity -= 1
                notifyItemChanged(position)
                onQuantityChanged(item)
            }
        }

        holder.binding.btnDelete.setOnClickListener {
            onDeleteClicked(item)
        }
    }

    override fun getItemCount(): Int = cartItems.size
}
