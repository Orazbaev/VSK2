package com.example.midterm.widget

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("widget_preferences")

class WidgetRepository(private val context: Context) {
    private val lastHobbyKey = stringPreferencesKey("last_hobby")
    
    suspend fun saveLastViewedHobby(hobbyTitle: String) {
        context.dataStore.edit { preferences ->
            preferences[lastHobbyKey] = hobbyTitle
        }
    }
    
    suspend fun getLastViewedHobby(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[lastHobbyKey]
        }.first()
    }
}
