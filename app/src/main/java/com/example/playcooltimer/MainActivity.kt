package com.example.playcooltimer

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimeScreen()
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun TimeScreen() {
    val playTimeInit = 10
    val coolTimeInit = 5

    var playTimeLeft by remember { mutableStateOf(playTimeInit) }
    var playMilliLeft by remember { mutableStateOf(0L) }
    var coolTimeLeft by remember { mutableStateOf(coolTimeInit) }

    var isRunning by remember { mutableStateOf(false) }
    var isPlayPhase by remember { mutableStateOf(true) }
    var timer: CountDownTimer? by remember { mutableStateOf(null) }

    val context = LocalContext.current
    val bellStart = remember { MediaPlayer.create(context, R.raw.start_bell) }
    bellStart.setVolume(1.0f, 1.0f)
    val bellCool = remember { MediaPlayer.create(context, R.raw.cool_bell) }
    bellCool.setVolume(1.0f, 1.0f)

    fun startTimer() {
        timer?.cancel()
        val totalMillis =
            if (isPlayPhase) playTimeLeft * 1000L + playMilliLeft
            else coolTimeLeft * 1000L
        timer = object : CountDownTimer(
            totalMillis,
            10L
        ) {
            override fun onTick(ms: Long) {
                if (isPlayPhase) {
                    playTimeLeft = (ms / 1000).toInt()
                    playMilliLeft = ms % 1000
                } else {
                    coolTimeLeft = (ms / 1000).toInt()
                }
            }
            override fun onFinish() {
                if (isPlayPhase) {
                    bellCool.start()
                    isPlayPhase = false
                } else {
                    bellStart.start()
                    isPlayPhase = true
                }
                playTimeLeft = playTimeInit
                playMilliLeft = 0
                coolTimeLeft = coolTimeInit
                startTimer()
            }
        }.start()
        isRunning = true
    }

    fun pauseTimer() {
        timer?.cancel()
        isRunning = false
    }

    fun resetTimer() {
        timer?.cancel()
        playTimeLeft = playTimeInit
        playMilliLeft = 0
        coolTimeLeft = coolTimeInit
        isPlayPhase = true
        isRunning = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF303030))
            .padding(48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "Play Time",
            fontSize = 32.sp,
            color = Color(0xFFF2F2F2),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.Start)
        )
        Text(
            buildAnnotatedString {
                append(
                    String.format(
                        "%02d:%02d",
                        playTimeLeft / 60,
                        playTimeLeft % 60
                    )
                )
                withStyle(SpanStyle(fontSize = 64.sp)) {
                    append(
                        String.format(
                            ".%02d",
                            playMilliLeft / 10
                        )
                    )
                }
            },
            fontSize = 82.sp,
            color = Color(0xFFF2F2F2)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Cool Time",
                fontSize = 24.sp,
                color = Color(0xFFF2F2F2),
                modifier = Modifier
                    .wrapContentWidth(Alignment.Start)
            )
            Text(
                text = String.format(
                    "%02d:%02d",
                    coolTimeLeft / 60,
                    coolTimeLeft % 60
                ),
                fontSize = 48.sp,
                color = Color(0xFFF2F2F2),
                modifier = Modifier
                    .wrapContentWidth(Alignment.Start)
                    .padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(64.dp))

        Row {
            Button(
                onClick = { resetTimer() },
                enabled = !isRunning,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = Color(0xFFF2F2F2),
                    disabledContainerColor = Color(0xFF822E2E),
                    disabledContentColor = Color(0xFFF2F2F2)
                ),
                modifier = Modifier.padding(8.dp)
            ) {
                Text("リセット")
            }
            Button(
                onClick = { if (isRunning) pauseTimer() else startTimer() },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(if (isRunning) "ストップ" else "スタート")
            }
        }
    }
}
