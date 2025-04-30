package com.example.midterm.domain.usecase

import com.example.midterm.domain.model.Hobby
import com.example.midterm.domain.repository.HobbyRepository
import kotlinx.coroutines.flow.Flow

class GetAllHobbiesUseCase(
    private val hobbyRepository: HobbyRepository
) {
    operator fun invoke(): Flow<List<Hobby>> {
        return hobbyRepository.getAllHobbies()
    }
}
