package com.android.food_app.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.food_app.adapter.CategoryAdapter
import com.android.food_app.adapter.ListRecipeAdapter
import com.android.food_app.api.ApiService
import com.android.food_app.api.Category
import com.android.food_app.api.CategoryResponse
import com.android.food_app.api.recipeclass
import com.android.testy_food.R
import com.android.testy_food.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {
    private lateinit var recyclerViewCategory: RecyclerView
    private  var adapterCategory: CategoryAdapter?=null
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListRecipeAdapter
    private val apiService: ApiService
            by lazy {
                Retrofit.Builder()
                    .baseUrl("https://www.themealdb.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)
            }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentHomeBinding.inflate(inflater,container,false)


        recyclerViewCategory=binding.rvCategory
        recyclerViewCategory.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        recyclerView=binding.rvRecipes
        recyclerView.layoutManager=GridLayoutManager(context,2)
        fetchCategories()
        search()
        return binding.root
    }
    private fun fetchCategories() {
        apiService.getCategories().enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                if (response.isSuccessful) {
                    val categories = response.body()?.categories ?: emptyList()
                    adapterCategory = CategoryAdapter(categories) { category ->

                        val bundle = Bundle().apply {
                            putString("CATEGORY_NAME", category.strCategory)
                        }
                        findNavController().navigate(R.id.action_homeFragment_to_listRecipesFragment, bundle)
                    }
                    recyclerViewCategory.adapter = adapterCategory
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Toast.makeText(context, "Check Your Internet Connection", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchRecipes(query:String) {
        apiService.getRecipesBySearch(query).enqueue(object : Callback<recipeclass> {


            override fun onResponse(call: Call<recipeclass>, response: Response<recipeclass>) {
                if (response.isSuccessful) {
                    val recipes = response.body()?.meals ?: emptyList()
                    adapter = ListRecipeAdapter(recipes) { recipe ->
                        // Handle recipe item click
                        val idmeal = recipe.idMeal
                      findNavController().navigate(  HomeFragmentDirections.actionHomeFragmentToDetailsFragment(idmeal))
                    }
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(context, "Check Your Internet Connection", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<recipeclass>, t: Throwable) {
                Toast.makeText(context, "Check Your Internet Connection", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun search(){
        // the action that taken when click on search_view
        binding.search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                fetchRecipes(query!!)
                recyclerView=binding.rvRecipes


                return true


            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText.isNullOrBlank()){

                    fetchRecipes("")

                }
                return false

            }

        })

    }

}