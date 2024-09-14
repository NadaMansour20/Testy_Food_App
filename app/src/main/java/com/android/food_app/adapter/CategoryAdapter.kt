package com.android.food_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.android.food_app.api.Category
import com.android.testy_food.R
import com.android.testy_food.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val categories: List<Category>,
    private val onItemClick: (Category) -> Unit
) : Adapter<CategoryAdapter.RecipeViewHolder>() {
    private lateinit var binding: ItemCategoryBinding


    inner class RecipeViewHolder(binding:ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        private val categoryName: TextView=binding.categoryName
        fun bind(category: Category) {
            categoryName.text = category.strCategory

            itemView.setOnClickListener { onItemClick(category) }
        }

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.RecipeViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.RecipeViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)


    }

    override fun getItemCount(): Int =categories.size


}