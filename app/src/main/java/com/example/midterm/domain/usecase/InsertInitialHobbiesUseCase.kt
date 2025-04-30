package com.example.midterm.domain.usecase

import com.example.midterm.R
import com.example.midterm.domain.model.Hobby
import com.example.midterm.domain.repository.HobbyRepository

class InsertInitialHobbiesUseCase(
    private val hobbyRepository: HobbyRepository
) {
    suspend operator fun invoke() {
        hobbyRepository.clearHobbies()
        val initialHobbies = listOf(
            Hobby(
                title = "Программирование",
                description = "Программирование — моё ключевое хобби. Начинал с интереса к автоматизации и написания простых приложений, позже увлёкся разработкой на Android и вебом. Пишу на Kotlin, JavaScript, немного пробовал Go и Python. Программирование для меня — это и творчество, и логика, и постоянное развитие.",
                imageResId = R.drawable.pro,
                priority = 1
            ),
            Hobby(
                title = "Плавание",
                description = "Я интересуюсь плаванием. ЧТО - прекрасного плавании это во 1 здоровое тело и здоровый дух. Во 2 ты всегда чистый и опрятный это залог успешного человека. И третьих ты расслабляешься после всех своих дел.",
                imageResId = R.drawable.hobby,
                priority = 2
            ),
            Hobby(
                title = "Музыка",
                description = "Я меломан — слушаю музыку практически постоянно. Интересуюсь самыми разными жанрами: от инди и электроники до хип-хопа и джаза. Люблю находить редкие треки, следить за новыми релизами и составлять собственные подборки. Музыка — мой способ настроиться на нужный лад и вдохновиться.",
                imageResId = R.drawable.music,
                priority = 3
            )
        )
        hobbyRepository.insertInitialHobbies(initialHobbies)
    }
}
