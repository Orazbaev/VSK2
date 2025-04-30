package com.example.midterm.domain.repository

import com.example.midterm.domain.model.Hobby
import kotlinx.coroutines.flow.Flow

interface HobbyRepository {
    fun getAllHobbies(): Flow<List<Hobby>>
    suspend fun getHobbyById(id: Int): Hobby?
    suspend fun clearHobbies()
    suspend fun insertHobby(hobby: Hobby): Long
    suspend fun updateHobby(hobby: Hobby)
    suspend fun deleteHobby(hobby: Hobby)
    suspend fun insertInitialHobbies(hobbies: List<Hobby>)
}
