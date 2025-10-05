package com.example.playcooltimer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.playcooltimer.data.SettingsDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class TimerSettings(
    val playMinutes: Int = 3,
    val playSeconds: Int = 0,
    val coolMinutes: Int = 1,
    val coolSeconds: Int = 0,
    val bellStartVolume: Int = 5,
    val bellCoolVolume: Int = 5,
    val repeatCount: Int = 1
)

class TimerViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = SettingsDataStore(application)

    private val _settings = MutableStateFlow(TimerSettings())
    val setting = dataStore.settingsFlow.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        TimerSettings(
            _settings.value.playMinutes,
            _settings.value.playSeconds,
            _settings.value.coolMinutes,
            _settings.value.coolSeconds,
            _settings.value.bellStartVolume,
            _settings.value.bellCoolVolume,
            _settings.value.repeatCount
        )
    )

    fun saveSetting(newSetting: TimerSettings) {
        viewModelScope.launch {
            dataStore.saveSettings(newSetting)
        }
    }

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