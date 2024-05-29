package com.example.fintrack

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class ExpenseEntity(
    @PrimaryKey
    @ColumnInfo("Key")
    val name: String,
    val barColor: Int,
    val icon: Int,
    val value: Double,
    val category: String

) :Serializable
