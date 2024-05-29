package com.example.fintrack

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo("Key")
    val name: String
)
