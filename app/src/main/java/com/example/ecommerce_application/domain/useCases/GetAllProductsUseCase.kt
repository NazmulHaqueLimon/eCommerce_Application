package com.example.ecommerce_application.domain.useCases

import com.example.ecommerce_application.data.ProductResponse
import com.example.ecommerce_application.data.Resource
import com.example.ecommerce_application.data.repository.ProductRepository
import com.example.ecommerce_application.domain.model.Category
import com.example.ecommerce_application.domain.model.Product
import javax.inject.Inject


class GetAllProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): Resource<List<Product>> {
        // Fetch all products from the repository
        val productResponses = productRepository.getAllProducts()

        return when (productResponses) {
            is Resource.Success -> {
                // Map the ProductResponse to Product domain model
                val productList = productResponses.data?.map { it.toDomain() } ?: emptyList()
                Resource.Success(productList)
            }
            is Resource.Error -> {
                // Return the error message from the repository
                Resource.Error(productResponses.message ?: "Unknown error")
            }
            is Resource.Loading -> {
                // Handle loading state if applicable
                Resource.Loading()
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


