package com.example.ecommerceapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ItemCategoryBinding
import com.example.ecommerceapp.models.Category

class CategoryAdapter(
    private val list: List<Category>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = list[position]
        holder.binding.tvCategory.text = category.categoryName

        when (category.categoryName.lowercase()) {
            "smart phones" -> holder.binding.ivCategory.setImageResource(R.drawable.smartphones)
            "laptops" -> holder.binding.ivCategory.setImageResource(R.drawable.laptops)
            "mens wear" -> holder.binding.ivCategory.setImageResource(R.drawable.menswear)
            "women's wear" -> holder.binding.ivCategory.setImageResource(R.drawable.womenwear)
            "grocery" -> holder.binding.ivCategory.setImageResource(R.drawable.grocery)
            "kids wear" -> holder.binding.ivCategory.setImageResource(R.drawable.kidswear)
            else -> holder.binding.ivCategory.setImageResource(R.drawable.ic_android_black_24dp)
        }

        // ✅ Pass the actual clicked category name
        holder.binding.root.setOnClickListener {
            onItemClick(category.categoryName)
        }
    }

    override fun getItemCount(): Int = list.size
}
