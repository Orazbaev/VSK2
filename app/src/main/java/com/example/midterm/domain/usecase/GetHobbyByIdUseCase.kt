package com.example.midterm.domain.usecase

import com.example.midterm.domain.model.Hobby
import com.example.midterm.domain.repository.HobbyRepository

class GetHobbyByIdUseCase(
    private val hobbyRepository: HobbyRepository
) {
    suspend operator fun invoke(id: Int): Hobby? {
        return hobbyRepository.getHobbyById(id)
    }
}
