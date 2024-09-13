package com.android.food_app.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment

import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.android.food_app.api.Recipe
import com.android.testy_food.R
import com.android.testy_food.R.id.fav_btn

import com.android.testy_food.databinding.RecipeListItemBinding
import com.bumptech.glide.Glide



class ListRecipeAdapter(

    private val recipes: List<Recipe>,
    private val onItemClick: (Recipe) -> Unit,

) : Adapter<ListRecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(val binding: RecipeListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val recipename: TextView = binding.recipeName
        private val recipeimage: ImageView = binding.recipeImage

        fun bind(recipe: Recipe) {
           recipename.text = recipe.strMeal

            Glide.with(itemView.context)
                .load(recipe.strMealThumb)
                .placeholder(R.drawable.broken_image)
                .error(R.drawable.error_outline)
                .into(recipeimage)


        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListRecipeAdapter.RecipeViewHolder {
        val binding =
            RecipeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListRecipeAdapter.RecipeViewHolder, position: Int) {

        val recipe = recipes[position]
        holder.bind(recipe)
        holder.itemView.setOnClickListener { onItemClick(recipe) }
    }

    override fun getItemCount(): Int = recipes.size
}