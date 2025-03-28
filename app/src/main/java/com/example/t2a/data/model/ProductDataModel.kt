package com.example.t2a.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDataModel(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
): Parcelable

@Parcelize
data class Rating(
    val rate: Double,
    val count: Int
): Parcelable

