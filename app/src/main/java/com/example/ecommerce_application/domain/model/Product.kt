package com.example.ecommerce_application.domain.model

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val images: List<String> // Images should be a list
)