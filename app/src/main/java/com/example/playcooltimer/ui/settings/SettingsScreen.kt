package com.example.playcooltimer.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                .padding(0.dp, 100.dp, 0.dp, 0.dp)
                .background(color = Color(0xFF0D0D0D)),
            verticalArrangement = Arrangement.spacedBy(24.dp)

        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    "Time",
                    fontSize = 24.sp,
                    color = Color(0xFFDCDCDC)
                )
                HorizontalDivider(
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp),
                    thickness = 2.dp,
                    color = Color(0xFFDCDCDC)
                )
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        // --- Play Time ---
                        Text(
                            "PLAY TIME",
                            fontSize = 18.sp,
                            color = Color(0xFFDCDCDC)
                        )
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
                    }

                    Column {
                        // --- Cool Time ---
                        Text(
                            "COOL TIME",
                            fontSize = 18.sp,
                            color = Color(0xFFDCDCDC)
                        )
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
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Volume",
                    fontSize = 24.sp,
                    color = Color(0xFFDCDCDC)
                )
                HorizontalDivider(
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp),
                    thickness = 2.dp,
                    color = Color(0xFFDCDCDC)
                )
                // --- bellStart Volume ---
                Text(
                    "PLAY TIME",
                    fontSize = 18.sp,
                    color = Color(0xFFDCDCDC)
                )
                Slider(
                    value = settings.bellStartVolume.toFloat(),
                    onValueChange = {
                        bellStartVolume = it.toInt()
                    },
                    valueRange = 0f..10f,
                    steps = 10
                )

                Spacer(modifier = Modifier.height(16.dp))

                // --- bellCool Volume ---
                Text(
                    "COOL TIME",
                    fontSize = 18.sp,
                    color = Color(0xFFDCDCDC)
                )
                Slider(
                    value = settings.bellCoolVolume.toFloat(),
                    onValueChange = {
                        bellCoolVolume = it.toInt()
                    },
                    valueRange = 0f..10f,
                    steps = 10
                )

                Spacer(modifier = Modifier.height(24.dp))

                // --- Repeat Count ---
                Text(
                    "Repeat Count",
                    fontSize = 24.sp,
                    color = Color(0xFFDCDCDC)
                )
                HorizontalDivider(
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp),
                    thickness = 2.dp,
                    color = Color(0xFFDCDCDC)
                )
                NumberSelector(
                    value = settings.repeatCount,
                    range = 1..99,
                    onValueChange = {
                        repeatCount = it
                        updateStore()
                    },
                    label = "Count",
                )
            }
        }
    }
}

/** 数値選択用の簡易コンポーネント */
@Composable
fun NumberSelector(value: Int, range: IntRange, onValueChange: (Int) -> Unit, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = { if (value > range.first) onValueChange(value - 1) }) { Text("-") }
        Text(
            "$value $label",
            modifier = Modifier.padding(horizontal = 8.dp),
            fontSize = 24.sp,
            color = Color(0xFFDCDCDC)
        )
        Button(onClick = { if (value < range.last) onValueChange(value + 1) }) { Text("+") }
    }
}