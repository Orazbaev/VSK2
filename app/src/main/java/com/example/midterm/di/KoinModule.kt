package com.example.midterm.di

import android.app.Application
import com.example.midterm.data.db.AppDatabase
import com.example.midterm.data.repository.HobbyRepositoryImpl
import com.example.midterm.domain.repository.HobbyRepository
import com.example.midterm.domain.usecase.GetAllHobbiesUseCase
import com.example.midterm.domain.usecase.GetHobbyByIdUseCase
import com.example.midterm.domain.usecase.InsertHobbyUseCase
import com.example.midterm.domain.usecase.InsertInitialHobbiesUseCase
import com.example.midterm.presentation.detail.DetailViewModel
import com.example.midterm.presentation.main.MainViewModel
import com.example.midterm.widget.WidgetRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// Модуль для базы данных и репозиториев
val databaseModule = module {
    // Синглтон базы данных
    single { AppDatabase.getInstance(androidApplication()) }

    // DAO
    single { get<AppDatabase>().hobbyDao() }

    // Репозитории
    single<HobbyRepository> { HobbyRepositoryImpl(get()) }
    single { WidgetRepository(androidApplication()) }
}

// Модуль для юз-кейсов
val useCaseModule = module {
    factory { GetAllHobbiesUseCase(get()) }
    factory { GetHobbyByIdUseCase(get()) }
    factory { InsertHobbyUseCase(get()) }
    factory { InsertInitialHobbiesUseCase(get()) }
}

// Модуль для ViewModel
val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { (hobbyId: Int) -> DetailViewModel(get(), get(), hobbyId) }
}

// Список всех модулей для удобства
val appModules = listOf(databaseModule, useCaseModule, viewModelModule)
