package com.example.t2a.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.t2a.R
import com.example.t2a.data.model.ProductDataModel
import com.example.t2a.databinding.ProductDetailsActivityBinding
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel

class ProductDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ProductDetailsActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductDetailsActivityBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.imageProduct.shapeAppearanceModel = ShapeAppearanceModel().toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, 38f) // 32f = corner radius
            .build()

        // Get product data from intent
        val product = intent.getParcelableExtra<ProductDataModel>("PRODUCT")

        product?.let {
            binding.textTitle.text = it.title
            binding.textPrice.text = "$ ${it.price} "
            binding.textDescription.text = it.description

            // **Load Image using Glide**
            Glide.with(this)
                .load(it.image)
                .into(binding.imageProduct)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed() // Handle back action
        return true
    }
}