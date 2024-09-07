package com.example.ecommerce_application.domain.model

data class User(
    val id: Int,
    val name: String,
    val address: String,
    val contact: String,
    val image: String,
)