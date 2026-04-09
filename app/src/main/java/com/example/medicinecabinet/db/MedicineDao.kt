package com.example.medicinecabinet.db

import androidx.room.*

@Dao
interface MedicineDao {
    @Query("SELECT * FROM medicines ORDER BY expiryDate")
    suspend fun getAll(): List<Medicine>
    
    @Query("SELECT * FROM medicines WHERE id = :id")
    suspend fun getById(id: Int): Medicine?
    
    @Insert
    suspend fun insert(medicine: Medicine)
    
    @Update
    suspend fun update(medicine: Medicine)
    
    @Delete
    suspend fun delete(medicine: Medicine)
    
    @Query("DELETE FROM medicines WHERE id = :id")
    suspend fun deleteById(id: Int)
    
    @Query("SELECT * FROM medicines WHERE expiryDate < :date")
    suspend fun getExpired(date: Date): List<Medicine>
    
    @Query("SELECT category, COUNT(*) as count FROM medicines GROUP BY category")
    suspend fun getCategoryStats(): List<CategoryStats>
}

data class CategoryStats(
    val category: String,
    val count: Int
)
