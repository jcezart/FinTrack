package com.example.fintrack

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExpenseDAO {
    @Query("SELECT * FROM expense")
    fun getAll(): List<Expense>

    @Insert
        (onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(expense: List<Expense>)

    @Delete
    suspend fun delete(entity: Expense)
}