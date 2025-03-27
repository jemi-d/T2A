package com.example.t2a.data.repository

import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.t2a.data.remote.ApiService
import javax.inject.Inject

class ProductRepository @Inject constructor(private val apiService: ApiService) : BaseRepository(){
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun fetchProducts() = safeApiCall { apiService.getProducts() }
}