package com.example.fintrack

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CategoryEntity::class,ExpenseEntity::class], version = 4)
abstract class FinTrackDataBase: RoomDatabase() {

    abstract fun getCategoryDAO(): CategoryDAO
    abstract fun getExpenseDAO(): ExpenseDAO
}