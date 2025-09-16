package com.example.playcooltimer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class TimerSettings(
    val playMinutes: Int = 3,
    val playSeconds: Int = 0,
    val coolMinutes: Int = 1,
    val coolSeconds: Int = 0,
    val bellStartVolume: Int = 5,
    val bellCoolVolume: Int = 5,
    val repeatCount: Int = 1
)

class TimerViewModel : ViewModel() {
    private val _settings = MutableStateFlow(TimerSettings())
    val setting: StateFlow<TimerSettings> = _settings

    fun updatePlayTime(min: Int, sec: Int) {
        _settings.value = _settings.value.copy(playMinutes = min, playSeconds = sec)
    }

    fun updateCoolTime(min: Int, sec: Int) {
        _settings.value = _settings.value.copy(coolMinutes = min, coolSeconds = sec)
    }

    fun updateBellStartVolume(v: Int) {
        _settings.value = _settings.value.copy(bellStartVolume = v)
    }

    fun updateBellCoolVolume(v: Int) {
        _settings.value = _settings.value.copy(bellCoolVolume = v)
    }

    fun updateRepeatCount(count: Int) {
        _settings.value = _settings.value.copy(repeatCount = count)
    }
}