package com.example.ecommerce_application.data

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("images")
    val images: List<String> // Maps to images array in the JSON
)
