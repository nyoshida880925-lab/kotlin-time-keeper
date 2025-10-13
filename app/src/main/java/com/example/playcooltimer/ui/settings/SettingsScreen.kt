package com.example.playcooltimer.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.playcooltimer.TimerSettings
import com.example.playcooltimer.TimerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: TimerViewModel, onBack: () -> Unit) {
    val settings by viewModel.setting.collectAsState()

    // 初期値を ViewModel から取得して remember にセット
    var playMinutes by remember(settings.playMinutes) { mutableStateOf(settings.playMinutes) }
    var playSeconds by remember(settings.playSeconds) { mutableStateOf(settings.playSeconds) }
    var coolMinutes by remember(settings.coolMinutes) { mutableStateOf(settings.coolMinutes) }
    var coolSeconds by remember(settings.coolSeconds) { mutableStateOf(settings.coolSeconds) }
    var bellStartVolume by remember(settings.bellStartVolume) { mutableStateOf(settings.bellStartVolume) }
    var bellCoolVolume by remember(settings.bellCoolVolume) { mutableStateOf(settings.bellCoolVolume) }
    var repeatCount by remember(settings.repeatCount) { mutableStateOf(settings.repeatCount) }

    fun updateStore() {
        viewModel.saveSetting(
            TimerSettings(
                playMinutes,
                playSeconds,
                coolMinutes,
                coolSeconds,
                bellStartVolume,
                bellCoolVolume,
                repeatCount
            )
        )
    }

    LaunchedEffect(
        playMinutes,
        playSeconds,
        coolMinutes,
        coolSeconds,
        bellStartVolume,
        bellCoolVolume,
        repeatCount
    ) {
        kotlinx.coroutines.delay(500L)
        viewModel.saveSetting(
            TimerSettings(
                playMinutes,
                playSeconds,
                coolMinutes,
                coolSeconds,
                bellStartVolume,
                bellCoolVolume,
                repeatCount
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("設定") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "戻る")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(48.dp, 100.dp, 24.dp, 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)

        ) {
            // --- Play Time ---
            Text("Play Time (min:sec)")
            MinuteSecondPicker(
                minutes = playMinutes,
                seconds = playSeconds,
                onMinutesChange = {
                    playMinutes = it
                    },
                onSecondsChange = {
                    playSeconds = it
                }
            )

            // --- Cool Time ---
            Text("Cool Time (min:sec)")
            MinuteSecondPicker(
                minutes = coolMinutes,
                seconds = coolSeconds,
                onMinutesChange = {
                    coolMinutes = it
                },
                onSecondsChange = {
                    coolSeconds = it
                }
            )

            // --- bellStart Volume ---
            Text("bellStart Volume (${settings.bellStartVolume})")
            Slider(
                value = settings.bellStartVolume.toFloat(),
                onValueChange = {
                    bellStartVolume = it.toInt()
                },
                valueRange = 0f..10f,
                steps = 10
            )

            // --- bellCool Volume ---
            Text("bellCool Volume (${settings.bellCoolVolume})")
            Slider(
                value = settings.bellCoolVolume.toFloat(),
                onValueChange = {
                    bellCoolVolume = it.toInt()
                },
                valueRange = 0f..10f,
                steps = 10
            )

            // --- Repeat Count ---
            Text("繰り返し回数 (${settings.repeatCount})")
            NumberSelector(
                value = settings.repeatCount,
                range = 1..99,
                onValueChange = {
                    repeatCount = it
                    updateStore()
                },
                label = "回"
            )
        }
    }
}

/** 数値選択用の簡易コンポーネント */
@Composable
fun NumberSelector(value: Int, range: IntRange, onValueChange: (Int) -> Unit, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = { if (value > range.first) onValueChange(value - 1) }) { Text("-") }
        Text("$value $label", modifier = Modifier.padding(horizontal = 8.dp))
        Button(onClick = { if (value < range.last) onValueChange(value + 1) }) { Text("+") }
    }
}