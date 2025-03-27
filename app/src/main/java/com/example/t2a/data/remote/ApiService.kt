package com.example.t2a.data.remote

import com.example.t2a.data.model.ProductDataModel
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<ProductDataModel>
}