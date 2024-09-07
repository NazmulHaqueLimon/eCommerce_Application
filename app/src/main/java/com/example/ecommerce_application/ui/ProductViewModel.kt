package com.example.ecommerce_application.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce_application.data.ProductResponse
import com.example.ecommerce_application.data.Resource
import com.example.ecommerce_application.data.repository.ProductRepository
import com.example.ecommerce_application.domain.model.Product
import com.example.ecommerce_application.domain.model.Category
import com.example.ecommerce_application.domain.useCases.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProductViewModel @Inject internal constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    // Flow for holding the product list
    private val _productList = MutableStateFlow<List<Product>>(emptyList())
    val productList: StateFlow<List<Product>> = _productList

    // Flow for holding the category list
    private val _categoryList = MutableStateFlow<List<Category>>(emptyList())
    val categoryList: StateFlow<List<Category>> = _categoryList

    // Flow for handling UI state (loading, error, success)
    private val _uiState = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val uiState: StateFlow<Resource<List<Product>>> = _uiState

    init {
        // Load all products and categories when ViewModel is initialized
        viewModelScope.launch {
            loadProductsAndCategories()
        }
    }

    // Method to get a specific product by its ID
    fun getProduct(productId: Int): Product? {
        return _productList.value.find { it.id == productId }
    }

    private suspend fun loadProductsAndCategories() {
        // Set the state to loading before making the call
        _uiState.value = Resource.Loading()
        Log.d("ProductViewModel", "Loading products and categories...")

        // Call the repository to get all products
        when (val result = productRepository.getAllProducts()) {
            is Resource.Success -> {
                // Convert ProductResponse to Product domain model
                val products = result.data?.map { it.toDomain() } ?: emptyList()

                // Log the products received
                Log.d("ProductViewModel", "Products fetched successfully: $products")

                // Set the product list if the result is successful
                _productList.value = products

                // Extract categories from the product list if not empty
                if (_productList.value.isNotEmpty()) {
                    val categories = productRepository.extractCategories(_productList.value)
                    _categoryList.value = categories

                    // Log the extracted categories
                    Log.d("ProductViewModel", "Categories extracted: $categories")
                } else {
                    // Log if the product list is empty
                    Log.d("ProductViewModel", "Product list is empty; no categories to extract.")
                }

                // Update the state to success
                _uiState.value = Resource.Success(_productList.value)
                Log.d("ProductViewModel", "UI state updated to Success with ${_productList.value.size} products.")
            }
            is Resource.Error -> {
                // Log the error message
                Log.e("ProductViewModel", "Error loading products: ${result.message}")
                // Update the state with the error message
                _uiState.value = Resource.Error(result.message ?: "An unknown error occurred")
            }
            is Resource.Loading -> {
                // Log that loading is still in progress
                Log.d("ProductViewModel", "Loading products...")
                // Keep it in the loading state if still loading
                _uiState.value = Resource.Loading()
            }
        }
    }

    private fun ProductResponse.toDomain(): Product {
        return Product(
            id = this.id,
            title = this.title,
            description = this.description,
            category = this.category,
            price = this.price,
            images = this.images
        )
    }
}