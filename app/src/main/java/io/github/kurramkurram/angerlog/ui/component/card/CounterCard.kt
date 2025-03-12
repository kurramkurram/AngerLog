package io.github.kurramkurram.angerlog.ui.component.card

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AngerLogCounterCard(
    modifier: Modifier = Modifier,
    size: Int,
    unit: String = "",
    animationDuration: Int = 1000,
    animationEasing: Easing = LinearEasing
) {
    Row(
        modifier = modifier
            .padding(10.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {
        var animating by remember { mutableStateOf(false) }
        val animation by animateIntAsState(
            targetValue = if (animating) size else 0,
            animationSpec = tween(
                durationMillis = animationDuration,
                easing = animationEasing
            )
        )

        LaunchedEffect(Unit) {
            animating = true
        }

        Text(
            modifier = modifier.padding(10.dp).alignByBaseline(),
            text = "$animation",
            fontSize = 30.sp,
        )
        Text(
            modifier = modifier.padding(10.dp).alignByBaseline(),
            text = unit,
            fontSize = 20.sp
        )
    }
}