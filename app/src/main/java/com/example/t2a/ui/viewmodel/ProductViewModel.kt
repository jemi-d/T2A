package com.example.t2a.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.t2a.data.model.ProductDataModel
import com.example.t2a.data.repository.ProductRepository
import com.example.t2a.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class ProductViewModel @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    fun getProducts() = liveData(Dispatchers.IO) {
        emit(ApiResult.Loading)
        emit(repository.fetchProducts())
    }

//    private val _products = MutableLiveData<List<ProductDataModel>>()
//    val products: LiveData<List<ProductDataModel>> = _products
//
//    private var allProducts: List<ProductDataModel> = emptyList()  // Store all fetched products
//    private var currentPage = 0
//    private val pageSize = 10  // Load 10 items at a time
//
//    fun fetchAllProducts() = viewModelScope.launch {
//        when (val result = repository.fetchProducts()) {
//            is ApiResult.Success -> {
//                allProducts = result.data
//                loadMoreProducts() // Load first batch
//            }
//            is ApiResult.Error -> {
//                _products.postValue(emptyList()) // Handle error case
//            }
//            else -> {}
//        }
//    }
//
//    private fun loadMoreProducts() {
//        val nextPage = allProducts.drop(currentPage * pageSize).take(pageSize)
//        if (nextPage.isNotEmpty()) {
//            _products.postValue(_products.value.orEmpty() + nextPage)
//            currentPage++
//        }
//    }
}