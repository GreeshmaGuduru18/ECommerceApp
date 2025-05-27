package com.example.ecommerceapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.R
import com.example.ecommerceapp.models.local.CartItem

class CheckoutAdapter(private var cartItems: List<CartItem>) :
    RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder>() {

    inner class CheckoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProduct: ImageView = itemView.findViewById(R.id.ivProduct)
        val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
        val tvUnitPrice: TextView = itemView.findViewById(R.id.tvUnitPrice)
        val tvQuantity: TextView = itemView.findViewById(R.id.tvQuantity)
        val tvTotal: TextView = itemView.findViewById(R.id.tvTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_checkout, parent, false)
        return CheckoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: CheckoutViewHolder, position: Int) {
        val item = cartItems[position]
        holder.ivProduct.setImageResource(item.imageResId)
        holder.tvProductName.text = item.name
        holder.tvUnitPrice.text = "Unit Price: $${item.price}"
        holder.tvQuantity.text = "Quantity: ${item.quantity}"
        val total = item.price * item.quantity
        holder.tvTotal.text = "Amount: $${"%.2f".format(total)}"
    }

    override fun getItemCount(): Int = cartItems.size

    fun updateList(newItems: List<CartItem>) {
        cartItems = newItems
        notifyDataSetChanged()
    }
}