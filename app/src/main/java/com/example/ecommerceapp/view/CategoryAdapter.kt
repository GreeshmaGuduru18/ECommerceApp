package com.example.ecommerceapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ItemCategoryBinding
import com.example.ecommerceapp.models.Category

class CategoryAdapter(
    private val list: List<Category>,
    private val onItemClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = list[position]
        Log.d("CATEGORY_IMAGE_MATCH", "categoryImageUrl = ${category.categoryImageUrl}")

        holder.binding.tvCategory.text = category.categoryName

        val imageResId = when (category.categoryImageUrl.lowercase()) {
            "smartphones.png" -> R.drawable.smartphones
            "laptops.png" -> R.drawable.laptops
            "mensfashion.png" -> R.drawable.mensfashion
            "womensfashion.png" -> R.drawable.womensfashion
            "grocery.png" -> R.drawable.grocery
            "kidsfashion.png" -> R.drawable.kidsfashion
            else -> R.drawable.ic_android_black_24dp
        }


        holder.binding.ivCategory.setImageResource(imageResId)

        holder.binding.root.setOnClickListener {
            onItemClick(category)
        }
    }

    override fun getItemCount(): Int = list.size
}