package com.example.midterm.domain.model

data class Hobby(
    val id: Int = 0,
    val title: String,
    val description: String,
    val imageResId: Int? = null,
    val priority: Int = 0
)
