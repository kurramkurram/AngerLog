package io.github.kurramkurram.angerlog.ui.component.chart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AngerLogPieChart(
    modifier: Modifier = Modifier,
    pieces: List<PiePiece>,
    animationDuration: Int = 1000,
    animationEasing: Easing = LinearEasing
) {
    val animationList = remember { List(pieces.size) { Animatable(0f) } }
    val coroutineScope = rememberCoroutineScope()

    // アニメーションを順番にスタート
    LaunchedEffect(Unit) {
        for ((index, progress) in animationList.withIndex()) {
            val rate = pieces[index].rate / 100
            coroutineScope.launch {
                progress.animateTo(
                    targetValue = 360 * rate,
                    animationSpec = tween(
                        durationMillis = (animationDuration * rate).toInt(),
                        easing = animationEasing
                    )
                )
            }
            delay((animationDuration * rate).toLong())
        }
    }

    // 円グラフ
    Canvas(
        modifier = modifier.padding(10.dp),
        onDraw = {
            var startAngle = 270f
            for ((index, progress) in animationList.withIndex()) {
                val sweepAngle = progress.value
                drawArc(
                    color = pieces[index].backgroundColor,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true
                )
                startAngle += sweepAngle
            }
        },
    )

    // 凡例
    Row(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        pieces.forEach {
            Text(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = it.backgroundColor)
                    .padding(horizontal = 20.dp, vertical = 5.dp),
                text = it.label
            )
        }
    }
}

data class PiePiece(
    val rate: Float,
    val label: String = "",
    val backgroundColor: Color
)