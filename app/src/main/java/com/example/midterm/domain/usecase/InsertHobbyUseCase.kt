package com.example.midterm.domain.usecase

import com.example.midterm.domain.model.Hobby
import com.example.midterm.domain.repository.HobbyRepository

class InsertHobbyUseCase(
    private val hobbyRepository: HobbyRepository
) {
    suspend operator fun invoke(hobby: Hobby): Long {
        return hobbyRepository.insertHobby(hobby)
    }
}
