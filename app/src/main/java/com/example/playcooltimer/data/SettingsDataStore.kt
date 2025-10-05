package com.example.playcooltimer.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.playcooltimer.TimerSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val SETTINGS_NAME = "timer_settings"
val Context.dataStore by preferencesDataStore(name = SETTINGS_NAME)

class SettingsDataStore(private val context: Context) {
    companion object {
        val PLAY_MINUTES = intPreferencesKey("play_minutes")
        val PLAY_SECONDS = intPreferencesKey("play_seconds")
        val COOL_MINUTES = intPreferencesKey("cool_minutes")
        val COOL_SECONDS = intPreferencesKey("cool_seconds")
        val BELL_START = intPreferencesKey("bell_start_volume")
        val BELL_COOL = intPreferencesKey("bell_cool_volume")
        val REPEAT_COUNT = intPreferencesKey("repeat_count")
    }

    val settingsFlow: Flow<TimerSettings> = context.dataStore.data.map { prefs ->
        TimerSettings(
            playMinutes = prefs[PLAY_MINUTES] ?: 3,
            playSeconds = prefs[PLAY_SECONDS] ?: 0,
            coolMinutes = prefs[COOL_MINUTES] ?: 1,
            coolSeconds = prefs[COOL_SECONDS] ?: 0,
            bellStartVolume = prefs[BELL_START] ?: 5,
            bellCoolVolume = prefs[BELL_COOL] ?: 5,
            repeatCount = prefs[REPEAT_COUNT] ?: 3
        )
    }

    suspend fun saveSettings(settings: TimerSettings) {
        context.dataStore.edit { prefs ->
            prefs[PLAY_MINUTES] = settings.playMinutes
            prefs[PLAY_SECONDS] = settings.playSeconds
            prefs[COOL_MINUTES] = settings.coolMinutes
            prefs[COOL_SECONDS] = settings.coolSeconds
            prefs[BELL_START] = settings.bellStartVolume
            prefs[BELL_COOL] = settings.bellCoolVolume
            prefs[REPEAT_COUNT] = settings.repeatCount
        }
    }
}

data class TimerSettings(
    val playMinutes: Int,
    val playSeconds: Int,
    val coolMinutes: Int,
    val coolSeconds: Int,
    val bellStartVolume: Int,
    val bellCoolVolume: Int,
    val repeatCount: Int
)