package com.example.t2a.ui.main

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresExtension
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t2a.R
import com.example.t2a.databinding.ProductActivityBinding
import com.example.t2a.ui.viewmodel.ProductViewModel
import com.example.t2a.utils.ApiResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ProductActivityBinding
    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductActivityBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupObservers()
        setupSearchView()
        setupSortingSpinner()

        productViewModel.getProducts()
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(emptyList())
        binding.productRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ProductActivity)
            adapter = productAdapter
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    private fun setupObservers() {
        productViewModel.getProducts().observe(this) { result ->
            when (result) {
                is ApiResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.productRecyclerView.visibility = View.GONE
                    binding.errorText.visibility = View.GONE
                }
                is ApiResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.productRecyclerView.visibility = View.VISIBLE
                    binding.errorText.visibility = View.GONE
                    productAdapter.updateProductList(result.data)
                }
                is ApiResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.productRecyclerView.visibility = View.GONE
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = result.message
                }
            }
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                productAdapter.filterProducts(newText.orEmpty())
                if (productAdapter.itemCount == 0) {
                    binding.errorText.text = getString(R.string.no_results_found)
                    binding.errorText.visibility = View.VISIBLE
                } else {
                    binding.errorText.visibility = View.GONE
                }
                return true
            }
        })
    }

    private fun setupSortingSpinner() {
        val sortOptions = resources.getStringArray(R.array.sort_options)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sortOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSort.adapter = adapter

        binding.spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                productAdapter.sortProducts(sortOptions[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}