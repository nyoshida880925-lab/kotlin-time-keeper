package com.example.playcooltimer.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MinuteSecondPicker(
    minutes: Int = 0,
    seconds: Int = 1,
    onMinutesChange: (Int) -> Unit,
    onSecondsChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .height(180.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        InfiniteNumberPicker(
            value = minutes,
            range = 0..59,
            onValueChange = { onMinutesChange(it) }
        )
        Text("分", modifier = Modifier.padding(horizontal = 8.dp))

        InfiniteNumberPicker(
            value = seconds,
            range = 0..59,
            onValueChange = { onSecondsChange(it) }
        )
        Text("秒", modifier = Modifier.padding(horizontal = 8.dp))
    }
}