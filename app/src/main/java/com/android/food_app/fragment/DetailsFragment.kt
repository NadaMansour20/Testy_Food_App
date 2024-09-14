package com.android.food_app.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.android.food_app.api.ApiService
import com.android.food_app.api.Recipe
import com.android.food_app.api.recipeclass
import com.android.testy_food.R
import com.android.testy_food.databinding.FragmentDetailsBinding
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DetailsFragment : Fragment() {

    private val args:DetailsFragmentArgs by navArgs()
   private lateinit var binding: FragmentDetailsBinding
   private lateinit var apiservice: ApiService
    private lateinit var recipeImage: ImageView
    private lateinit var recipeName: TextView
    private lateinit var recipeInstructions: TextView
    private lateinit var recipeType: TextView
    private lateinit var recipeIngredients: TextView
    private lateinit var recipeVideoWebView: WebView
    private lateinit var shareIcon: FloatingActionButton
    private var isInstructionsVisible = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiservice = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
        val recipeId = args.idmeal
        recipeImage = binding.recipeImage
        recipeName = binding.recipeName
        recipeInstructions = binding.recipeInstructions
        recipeType = binding.recipeType
        recipeIngredients = binding.recipeIngredients
        recipeVideoWebView = binding.recipeVideoWebView
        shareIcon = binding.shareFab

        fetchRecipeById(recipeId)
        recipeName.setOnClickListener {
            toggleInstructionsVisibility()
        }
        shareIcon.setOnClickListener {
            shareRecipe()
        }


    }
    private fun fetchRecipeById(id: String) {
        apiservice.getRecipeById(id).enqueue(object : Callback<recipeclass> {

            override fun onResponse(call: Call<recipeclass>, response: Response<recipeclass>) {
                if (response.isSuccessful) {
                    val recipe = response.body()?.meals?.firstOrNull()
                    recipe?.let {
                        //mealtofav=recipe
                        recipeName.text = it.strMeal
                        recipeInstructions.text = it.strInstructions ?: "No instructions available"
                        recipeType.text = it.strCategory ?: "Unknown type"
                        recipeIngredients.text = extractIngredients(it)
                        //recipeImage.load(it.strMealThumb)
                        Glide.with(requireContext())
                            .load(it.strMealThumb)
                            .placeholder(R.drawable.broken_image)
                            .error(R.drawable.error_outline)
                            .into(recipeImage)

                        recipeInstructions.visibility = View.GONE // Hide instructions initially

                        // Store the image URL in arguments for sharing
                        arguments?.putString("RECIPE_IMAGE_URL", it.strMealThumb)

                        // إعداد وتشغيل الفيديو التحكم
                        val videoUrl = it.strYoutube?.replace("watch?v=", "embed/") ?: "https://www.youtube.com/watch?v=8pPwWqtOFWk"
                        setupAndLoadVideo(videoUrl)
                    }
                } else {
                    showError("Failed to load recipe details")}
            }

            override fun onFailure(call: Call<recipeclass>, t: Throwable) {
                showError("Failed to connect to the server")
            }
        })
    }
    private fun setupAndLoadVideo(videoUrl: String) {
        if (videoUrl.isNotEmpty()) {
            recipeVideoWebView.webViewClient = WebViewClient()
            val webSettings: WebSettings = recipeVideoWebView.settings
            webSettings.javaScriptEnabled = true
            recipeVideoWebView.loadUrl(videoUrl)
        }
    }
    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    private fun extractIngredients(recipe: Recipe): String {
        val ingredients = mutableListOf<String>()

        if (!recipe.strIngredient1.isNullOrEmpty()) {
            ingredients.add("${recipe.strIngredient1} - ${recipe.strMeasure1.orEmpty()}")
        }
        if (!recipe.strIngredient2.isNullOrEmpty()) {
            ingredients.add("${recipe.strIngredient2} - ${recipe.strMeasure2.orEmpty()}")
        }
        if (!recipe.strIngredient3.isNullOrEmpty()) {
            ingredients.add("${recipe.strIngredient3} - ${recipe.strMeasure3.orEmpty()}")
        }
        if (!recipe.strIngredient4.isNullOrEmpty()) {
            ingredients.add("${recipe.strIngredient4} - ${recipe.strMeasure4.orEmpty()}")
        }
        if (!recipe.strIngredient5.isNullOrEmpty()) {
            ingredients.add("${recipe.strIngredient5} - ${recipe.strMeasure5.orEmpty()}")
        }

        return ingredients.joinToString(separator = "\n")
    }
    private fun toggleInstructionsVisibility() {
        isInstructionsVisible = !isInstructionsVisible
        recipeInstructions.visibility = if (isInstructionsVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
    private fun shareRecipe() {
        val recipeName = recipeName.text.toString()
        val recipeInstructions = recipeInstructions.text.toString()
        val recipeImageUrl = arguments?.getString("RECIPE_IMAGE_URL").orEmpty()

        val shareText = """
        Check out this recipe!
        
        Name: $recipeName
        Instructions: $recipeInstructions
        Image: $recipeImageUrl
    """.trimIndent()

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }

        startActivity(Intent.createChooser(shareIntent, "Share recipe via"))
    }

}