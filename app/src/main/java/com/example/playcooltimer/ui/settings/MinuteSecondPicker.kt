package com.example.playcooltimer.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MinuteSecondPicker(
    minutes: Int = 0,
    seconds: Int = 1,
    onMinutesChange: (Int) -> Unit,
    onSecondsChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .height(120.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        InfiniteNumberPicker(
            value = minutes,
            range = 0..59,
            onValueChange = { onMinutesChange(it) }
        )
        Text(":",
            modifier = Modifier.padding(horizontal = 8.dp),
            fontSize = 32.sp,
            color = Color(0xFFDCDCDC)
        )

        InfiniteNumberPicker(
            value = seconds,
            range = 0..59,
            onValueChange = { onSecondsChange(it) }
        )
    }
}