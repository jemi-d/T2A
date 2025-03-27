package com.example.t2a.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.t2a.data.model.ProductDataModel
import com.example.t2a.databinding.ItemProductBinding

@SuppressLint("NotifyDataSetChanged")
class ProductAdapter(private var productList: List<ProductDataModel>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>()  {

    private var filteredList = productList.toMutableList()

    class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = filteredList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = filteredList[position]
        holder.binding.apply {
            textTitle.text = product.title
            textPrice.text = "$${product.price}"
            textDescription.text = product.description

            Glide.with(imageProduct.context)
                .load(product.image)
                .into(imageProduct)

            //--Navigate to Product Details Screen--
            root.setOnClickListener {
                val context = root.context
                val intent = Intent(context, ProductDetailsActivity::class.java).apply {
                    putExtra("PRODUCT", product)
                }

                context.startActivity(intent)
            }
        }
    }

    //--Filter products by name
    fun filterProducts(query: String) {
        filteredList = if (query.isEmpty()) {
            productList.toMutableList()
        } else {
            productList.filter { it.title.contains(query, ignoreCase = true) }.toMutableList()
        }
        notifyDataSetChanged()
    }

    //--Update sorting
    fun sortProducts(sortBy: String) {
        filteredList = when (sortBy) {
            "Price" -> filteredList.sortedBy { it.price }.toMutableList()
            "Category" -> filteredList.sortedBy { it.category }.toMutableList()
            else -> productList.toMutableList()
        }
        notifyDataSetChanged()
    }

    //--Update full product list
    fun updateProductList(newList: List<ProductDataModel>) {
        productList = newList
        filteredList = newList.toMutableList()
        notifyDataSetChanged()
    }

}