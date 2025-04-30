package com.example.midterm.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.example.midterm.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LastUserWidgetReceiver : AppWidgetProvider(), KoinComponent {

    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    // Используем Koin для получения зависимости
    private val widgetRepository: WidgetRepository by inject()

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        coroutineScope.launch {
            val lastHobby = widgetRepository.getLastViewedHobby()

            appWidgetIds.forEach { appWidgetId ->
                updateAppWidget(context, appWidgetManager, appWidgetId, lastHobby)
            }
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        hobbyTitle: String?
    ) {
        val views = RemoteViews(context.packageName, R.layout.widget_placeholder)

        val text = if (hobbyTitle.isNullOrEmpty()) {
            "Нет просмотренных хобби"
        } else {
            "Последнее хобби: $hobbyTitle"
        }

        views.setTextViewText(R.id.widget_text, text)
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    override fun onDisabled(context: Context) {
        job.cancel()
    }
}
