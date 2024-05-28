package com.example.fintrack

import androidx.annotation.DrawableRes

data class Expense(
    val name : String,
    val barColor : Int,
    @DrawableRes val icon: Int,
    val value: Double,
    val category: String
)
