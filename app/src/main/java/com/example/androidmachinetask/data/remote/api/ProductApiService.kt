package com.example.androidmachinetask.data.remote.api

import com.example.androidmachinetask.data.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): Response<List<Product>>
}