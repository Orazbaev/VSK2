package com.example.midterm.data.repository

import com.example.midterm.data.db.dao.HobbyDao
import com.example.midterm.data.db.entity.HobbyEntity
import com.example.midterm.domain.model.Hobby
import com.example.midterm.domain.repository.HobbyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HobbyRepositoryImpl(
    private val hobbyDao: HobbyDao
) : HobbyRepository {

    override fun getAllHobbies(): Flow<List<Hobby>> {
        return hobbyDao.getAllHobbies().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getHobbyById(id: Int): Hobby? {
        return hobbyDao.getHobbyById(id)?.toDomain()
    }

    override suspend fun clearHobbies() {
        hobbyDao.clearAll()
    }

    override suspend fun insertHobby(hobby: Hobby): Long {
        return hobbyDao.insertHobby(hobby.toEntity())
    }

    override suspend fun updateHobby(hobby: Hobby) {
        hobbyDao.updateHobby(hobby.toEntity())
    }

    override suspend fun deleteHobby(hobby: Hobby) {
        hobbyDao.deleteHobby(hobby.toEntity())
    }

    override suspend fun insertInitialHobbies(hobbies: List<Hobby>) {
        hobbyDao.insertHobbies(hobbies.map { it.toEntity() })
    }

    private fun HobbyEntity.toDomain(): Hobby {
        return Hobby(
            id = id,
            title = title,
            description = description,
            imageResId = imageResId,
            priority = priority
        )
    }

    private fun Hobby.toEntity(): HobbyEntity {
        return HobbyEntity(
            id = id,
            title = title,
            description = description,
            imageResId = imageResId,
            priority = priority
        )
    }
}
