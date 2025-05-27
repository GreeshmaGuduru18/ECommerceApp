package com.example.ecommerceapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.databinding.ItemProductBinding
import com.example.ecommerceapp.models.Product

class ProductAdapter(
    private val list: List<Product>,
    private val onAddToCartClicked: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = list[position]

        holder.binding.tvName.text = product.name
        holder.binding.tvDesc.text = product.description
        holder.binding.tvPrice.text = "$ ${product.price}"
        holder.binding.ivProduct.setImageResource(product.imageResId)
        holder.binding.tvRating.text = "${product.rating} ★"

        holder.binding.tvAddToCart.setOnClickListener {
            onAddToCartClicked(product)
        }
    }

    override fun getItemCount(): Int = list.size
}
