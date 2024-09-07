package com.example.ecommerce_application.data.repository

import android.util.Log
import com.example.ecommerce_application.data.ProductResponse
import com.example.ecommerce_application.data.Resource
import com.example.ecommerce_application.domain.model.Product
import com.example.ecommerce_application.data.service.ProductApiService
import com.example.ecommerce_application.domain.model.Category
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productApiService: ProductApiService
) {

    companion object {
        private const val TAG = "ProductRepository"
    }

    suspend fun getAllProducts(): Resource<List<ProductResponse>> {
        return try {
            Log.d(TAG, "Fetching all products from API")
            val response = productApiService.getAllProducts() // Call API
            if (response.isSuccessful) {
                Log.d(TAG, "API call successful: ${response.body()}")
                response.body()?.products?.let {
                    Resource.Success(it) // Return success with the list of products
                } ?: Resource.Error("Unknown error") // Handle null response body
            } else {
                Log.e(TAG, "API call failed: ${response.message()}")
                Resource.Error("Error: ${response.message()}") // Handle non-success response
            }
        } catch (e: Exception) {
            Log.e(TAG, "Network error: ${e.message}", e)
            Resource.Error("Network error: ${e.message}") // Handle exceptions
        }
    }


    fun extractCategories(products: List<Product>): List<Category> {
        val categoryMap = mutableMapOf<String, MutableList<Product>>()

        for (product in products) {
            val categoryName = product.category
            val productList = categoryMap.getOrPut(categoryName) { mutableListOf() }
            productList.add(product)
        }

        val categories = categoryMap.map { (categoryName, productList) ->
            Category(
                id = categoryName.hashCode(), // Generate a unique ID for this example
                name = categoryName,
                products = productList
            )
        }

        Log.d(TAG, "Extracted categories: $categories")
        return categories
    }
}