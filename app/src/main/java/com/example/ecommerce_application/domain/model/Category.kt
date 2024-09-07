package com.example.ecommerce_application.domain.model

data class Category(
    val id: Int,
    val name: String,
    val products: List<Product>

)
