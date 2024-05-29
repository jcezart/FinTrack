package com.example.fintrack

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoryDAO {
    @Query("SELECT * FROM categoryentity")
    fun getAll(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categoryEntity: List<CategoryEntity>)

    @Delete
    suspend fun delete(category: CategoryEntity)
}