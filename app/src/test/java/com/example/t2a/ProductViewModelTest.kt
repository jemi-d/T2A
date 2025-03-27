package com.example.t2a

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.t2a.data.model.ProductDataModel
import com.example.t2a.data.model.Rating
import com.example.t2a.data.repository.ProductRepository
import com.example.t2a.ui.viewmodel.ProductViewModel
import com.example.t2a.utils.ApiResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class ProductViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: ProductRepository

    private lateinit var viewModel: ProductViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = ProductViewModel(repository)
    }

    @Test
    fun `getProducts should emit Loading, then Success`() = runTest {
        // Arrange
        val mockProducts = listOf(ProductDataModel(1, "Test Product", 10.0, "Description", "category", "image", Rating(4.5, 10)))
        Mockito.`when`(repository.fetchProducts()).thenReturn(ApiResult.Success(mockProducts))

        val observer: Observer<ApiResult<List<ProductDataModel>>> = mock()
        viewModel.getProducts().observeForever(observer)

        // Act
        viewModel.getProducts()

        // Assert
        Mockito.verify(observer).onChanged(ApiResult.Loading)
        Mockito.verify(observer).onChanged(ApiResult.Success(mockProducts))
    }

    @Test
    fun `getProducts should emit Loading, then Error when API fails`() = runTest {
        // Arrange
        val errorMessage = "Network Error"
        Mockito.`when`(repository.fetchProducts()).thenReturn(ApiResult.Error(errorMessage))

        val observer: Observer<ApiResult<List<ProductDataModel>>> = mock()
        viewModel.getProducts().observeForever(observer)

        // Act
        viewModel.getProducts()

        // Assert
        Mockito.verify(observer).onChanged(ApiResult.Loading)
        Mockito.verify(observer).onChanged(ApiResult.Error(errorMessage))
    }
}