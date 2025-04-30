package com.example.midterm.presentation

import android.app.Application
import com.example.midterm.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class HobbyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Инициализация Koin
        startKoin {
            // Логирование (в релизе можно отключить или установить Level.ERROR)
            androidLogger(Level.DEBUG)
            // Передаем контекст приложения
            androidContext(this@HobbyApp)
            // Загружаем модули
            modules(appModules)
        }
    }
}
