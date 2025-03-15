package io.github.kurramkurram.angerlog.ui.component.chart

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp

@Composable
fun AngerLogBarChart(
    modifier: Modifier = Modifier,
    // TODO クラス化する
    data: List<BarData>,
    animationDuration: Int = 1000,
    animationEasing: Easing = LinearEasing,
    barWidth: Float? = null,
    barSpacing: Float? = null,
) {
    var animating by remember { mutableStateOf(false) }
    val animation by animateFloatAsState(
        targetValue = if (animating) 1f else 0f,
        animationSpec =
            tween(
                durationMillis = animationDuration,
                easing = animationEasing,
            ),
    )

    // 棒グラフ
    Canvas(
        modifier = modifier.padding(20.dp),
        onDraw = {
            animating = true

            val bWidth = barWidth ?: (size.width / (data.size + 3)) // +3した等分を棒グラフの横幅にする
            val bSpacing = barSpacing ?: (bWidth * 0.2f) // 棒の横幅の20%をスペースにする

            // 棒と間隔の全体の幅
            val totalBarsWidth = data.size * bWidth + (data.size - 1) * bSpacing
            // キャンバスの中心
            val centerX = size.width / 2f
            // 棒グラフ全体の左端
            val startX = centerX - totalBarsWidth / 2f
            // 棒グラフ全体の右端
            val endX = centerX + totalBarsWidth / 2f

            val baseLine = size.height
            drawLine(
                color = Color.DarkGray,
                start = Offset(startX, baseLine),
                end = Offset(endX, baseLine),
                strokeWidth = 2f,
            )

            val paint =
                android.graphics.Paint().apply {
                    color = android.graphics.Color.GRAY
                    textAlign = android.graphics.Paint.Align.CENTER
                    textSize = 32f
                }

            // データ最大値
            val maxBarData = data.maxOf { it.size }
            for ((i, d) in data.withIndex()) {
                // 棒の左端からの位置
                val offsetX = startX + i * (bWidth + bSpacing)
                // 棒の高さ
                val barHeight = -size.height / maxBarData * d.size
                drawRect(
                    topLeft = Offset(offsetX, size.height),
                    color = d.backgroundColor,
                    size = Size(bWidth, barHeight * animation),
                )

                drawContext.canvas.nativeCanvas.drawText(
                    d.label,
                    offsetX + bWidth / 2,
                    baseLine + 40f,
                    paint,
                )
            }
        },
    )
}

data class BarData(
    val size: Float,
    val label: String,
    val labelColor: Color,
    val backgroundColor: Color = Color.Red,
)
