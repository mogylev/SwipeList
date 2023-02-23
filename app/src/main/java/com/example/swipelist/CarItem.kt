package com.example.swipelist

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

private const val SWIPE_ANIMATION_DURATION = 500

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DraggableCarItem(
    modifier: Modifier = Modifier,
    carDataItem: CarDataItem
) {
    val cardOffset = 300f
    var isRevealed by remember {
        mutableStateOf(true)
    }
    var offsetX by remember { mutableStateOf(0f) }
    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(targetState = transitionState, label = "")
    val offsetTransition by transition.animateFloat(
        label = "test",
        transitionSpec = { tween(durationMillis = SWIPE_ANIMATION_DURATION) },
        targetValueByState = {
            if (isRevealed) { (-cardOffset) - (offsetX)} else offsetX
        }
    )
    Card(
        modifier = modifier
            .offset { IntOffset(((-offsetX) - (-offsetTransition)).roundToInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    val originalOffset = Offset(offsetX, 0f)
                    val draggedOffset = originalOffset + Offset(x = dragAmount, y = 0f)
                    val resultOffset = Offset(x = draggedOffset.x.coerceIn(-cardOffset, 0f), y = 0f)
                    if (resultOffset.x <= -10) {
                        isRevealed = true
                        return@detectHorizontalDragGestures
                    } else if (resultOffset.x >= 0) {
                        isRevealed = false
                        return@detectHorizontalDragGestures
                    }
                    if (change.positionChange() != Offset.Zero) change.consume()
                    offsetX = resultOffset.x
                }
            },
        backgroundColor = Color.Cyan
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CarIcon(
                modifier = Modifier.size(64.dp),
                carUrl = carDataItem.url
            )
            Spacer(modifier = Modifier.size(16.dp))

            Text(text = carDataItem.model)
        }
    }

}

@Composable
private fun CarIcon(modifier: Modifier = Modifier, carUrl: String) {
    Icon(
        modifier = modifier,
        imageVector = Icons.Filled.CheckCircle,
        contentDescription = ""
    )
}