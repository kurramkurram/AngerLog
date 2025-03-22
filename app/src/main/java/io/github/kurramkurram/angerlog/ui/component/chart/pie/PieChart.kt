package io.github.kurramkurram.angerlog.ui.component.chart.pie

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * デフォルトのアニメーション時間.
 */
private const val DEFAULT_ANIMATION_DURATION = 1000

/**
 * 円グラフ.
 *
 * @param modifier [Modifier]
 * @param pieData 円グラフに表示するデータ
 * @param animationDuration アニメーションの時間
 * @param animationEasing アニメーションのイージング
 */
@Composable
fun AngerLogPieChart(
    modifier: Modifier = Modifier,
    pieData: PieData,
    animationDuration: Int = DEFAULT_ANIMATION_DURATION,
    animationEasing: Easing = LinearEasing,
) {
    val animationList = remember { List(pieData.getItemCount()) { Animatable(0f) } }
    val coroutineScope = rememberCoroutineScope()

    // アニメーションを順番にスタート
    LaunchedEffect(Unit) {
        for ((index, progress) in animationList.withIndex()) {
            val rate = pieData.getItem(index).rate / 100
            coroutineScope.launch {
                progress.animateTo(
                    targetValue = 360 * rate,
                    animationSpec =
                        tween(
                            durationMillis = (animationDuration * rate).toInt(),
                            easing = animationEasing,
                        ),
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
                    color = pieData.getItem(index).backgroundColor,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                )
                startAngle += sweepAngle
            }
        },
    )

    // 凡例
    Row(
        modifier =
            Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        pieData.getItems().forEach {
            Text(
                modifier =
                    Modifier
                        .clip(CircleShape)
                        .background(color = it.backgroundColor)
                        .padding(horizontal = 20.dp, vertical = 5.dp),
                text = it.label,
            )
        }
    }
}
