package com.android.food_app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.food_app.adapter.ListRecipeAdapter
import com.android.food_app.api.ApiService
import com.android.food_app.api.recipeclass
import com.android.testy_food.R
import com.android.testy_food.databinding.FragmentListRecipesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ListRecipesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter:ListRecipeAdapter
    private lateinit var binding: FragmentListRecipesBinding
    private lateinit var apiService: ApiService
    private lateinit var categoryName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryName = it.getString("CATEGORY_NAME") ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentListRecipesBinding.inflate(inflater,container,false)
        recyclerView=binding.rvMealsList
        recyclerView.layoutManager= GridLayoutManager(context,2)

        apiService = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
        val numberOfColumns = 2 // Number of columns in the grid
        val gridLayoutManager = GridLayoutManager(context, numberOfColumns)
        recyclerView.layoutManager = gridLayoutManager
        fetchRecipesByCategory()


        return binding.root
    }
    private fun fetchRecipesByCategory() {
        apiService.getRecipesByCategory(categoryName).enqueue(object : Callback<recipeclass> {

            override fun onResponse(call: Call<recipeclass>, response: Response<recipeclass>) {
                if (response.isSuccessful) {
                    val recipes = response.body()?.meals ?: emptyList()
                    listAdapter = ListRecipeAdapter(recipes) { recipe ->
                        // Handle recipe item click
                         val action = ListRecipesFragmentDirections.actionListRecipesFragmentToDetailsFragment(recipe.idMeal)
                        findNavController().navigate(action)
                       // findNavController().navigate(R.id.action_listRecipesFragment_to_detailsFragment)
                    }
                    recyclerView.adapter = listAdapter
                }
            }

            override fun onFailure(call: Call<recipeclass>, t: Throwable) {
                Toast.makeText(context, "Check Your Internet Connection", Toast.LENGTH_SHORT).show()
            }
        })
    }


}