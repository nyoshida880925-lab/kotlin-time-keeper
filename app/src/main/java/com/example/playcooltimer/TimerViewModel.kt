package com.example.playcooltimer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

data class TimerConfig(
    val playTimeSec: Int = 180,
    val coolTimeSec: Int = 60
)

class TimerViewModel : ViewModel() {
    private val _config = MutableStateFlow(TimerConfig())
}