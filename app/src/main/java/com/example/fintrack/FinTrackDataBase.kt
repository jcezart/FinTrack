package com.example.fintrack

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CategoryEntity::class,Expense::class], version = 5)
abstract class FinTrackDataBase: RoomDatabase() {

    abstract fun getCategoryDAO(): CategoryDAO
    abstract fun getExpenseDAO(): ExpenseDAO
}