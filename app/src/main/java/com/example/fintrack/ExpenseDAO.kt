package com.example.fintrack

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExpenseDAO {
    @Query("SELECT * FROM expenseentity")
    fun getAll(): List<ExpenseEntity>

    @Insert
        (onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(expenseEntity: List<ExpenseEntity>)

    @Delete
    suspend fun delete(entity: ExpenseEntity)
}