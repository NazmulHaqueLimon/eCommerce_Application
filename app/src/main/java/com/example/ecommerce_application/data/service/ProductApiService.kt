package com.example.ecommerce_application.data.service

import com.example.ecommerce_application.data.ProductListResponse
import com.example.ecommerce_application.data.ProductResponse
import com.example.ecommerce_application.domain.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ProductApiService {

    @GET("products")
    suspend fun getAllProducts(): Response<ProductListResponse>



}
