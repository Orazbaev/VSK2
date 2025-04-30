package com.example.midterm.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.midterm.data.db.entity.HobbyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HobbyDao {
    @Query("SELECT * FROM hobbies ORDER BY priority DESC")
    fun getAllHobbies(): Flow<List<HobbyEntity>>

    @Query("SELECT * FROM hobbies WHERE id = :hobbyId")
    suspend fun getHobbyById(hobbyId: Int): HobbyEntity?

    @Query("DELETE FROM hobbies")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHobby(hobby: HobbyEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHobbies(hobbies: List<HobbyEntity>)

    @Update
    suspend fun updateHobby(hobby: HobbyEntity)

    @Delete
    suspend fun deleteHobby(hobby: HobbyEntity)

    @Query("DELETE FROM hobbies")
    suspend fun deleteAllHobbies()
}
