package com.example.t2a

import com.example.t2a.data.model.ProductDataModel
import com.example.t2a.data.model.Rating
import com.example.t2a.data.remote.ApiService
import com.example.t2a.data.repository.ProductRepository
import com.example.t2a.utils.ApiResult
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductRepositoryTest {

    private lateinit var repository: ProductRepository
    private val apiService: ApiService = mockk()

    @Before
    fun setUp() {
        repository = ProductRepository(apiService)
    }

    @Test
    fun `fetchProducts should return Success when API call is successful`() = runTest {
        // Arrange
        val mockProducts = listOf(ProductDataModel(1, "Test Product", 10.0, "Description", "category", "image", Rating(4.5, 10)))
        coEvery { apiService.getProducts() } returns mockProducts

        // Act
        val result = repository.fetchProducts()

        // Assert
        assertTrue(result is ApiResult.Success && result.data == mockProducts)
    }

    @Test
    fun `fetchProducts should return Error when API call fails`() = runTest {
        // Arrange
        val errorMessage = "Network Error"
        coEvery { apiService.getProducts() } throws Exception(errorMessage)

        // Act
        val result = repository.fetchProducts()

        // Assert
        assertTrue(result is ApiResult.Error)
    }
}