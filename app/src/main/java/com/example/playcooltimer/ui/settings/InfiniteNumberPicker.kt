package com.example.playcooltimer.ui.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("ModifierParameter")
@Composable
fun InfiniteNumberPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    range: IntRange = 0..59,
    modifier: Modifier = Modifier,
    itemHeightDp: Dp = 56.dp,   // 1行の高さを固定
    visibleCount: Int = 3       // 奇数にする（3/5 など）
) {
    val itemCount = range.count()
    // 0..59 を100倍して6000件にする
    val displayList = remember { List(itemCount * 100) { it % itemCount } }

    val pickerHeight = itemHeightDp * visibleCount
    // LazyColumnの状態
    val listState = rememberLazyListState()
    LaunchedEffect(Unit) {
        val visibleCenterOffset = 1
        val centerIndex = (displayList.size / 2) + (value % itemCount) - visibleCenterOffset
        listState.scrollToItem(centerIndex)
    }
    val flingBehavior = rememberSnapFlingBehavior(listState)

    // スクロール位置に応じて選択値を更新
    LaunchedEffect(
        listState.firstVisibleItemIndex,
        listState.firstVisibleItemScrollOffset
    ) {
        // 中央アイテムの位置を計算
        val centerIndex = listState.firstVisibleItemIndex + 1
        val newValue = displayList[centerIndex % itemCount]
        if (newValue != value) onValueChange(newValue)
    }

    LazyColumn(
        state = listState,
        flingBehavior = flingBehavior,
        modifier = modifier.height(pickerHeight),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(displayList.size) { i ->
            Text(
                text = "%02d".format(displayList[i]),
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}