package io.github.kurramkurram.angerlog.ui.component.chart.bar

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
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

/**
 * デフォルトのアニメーション時間.
 */
private const val DEFAULT_ANIMATION_DURATION = 1000

/**
 * 棒グラフの幅を表示幅から決める際に計算する、データ数に追加する件数.
 * 大きい値を指定することで棒グラフの幅が小さくなる
 */
private const val BAR_WIDTH_OFFSET = 3

/**
 * 棒グラフ間のスペースを決める棒グラフの幅に対する割合.
 */
private const val BAR_SPACING_RATE = 0.2f

/**
 * 棒グラフの基準となる線の太さ.
 */
private const val BASE_LINE_STROKE = 2f

/**
 * ラベルのテキストサイズ.
 */
private const val LABEL_TEXT_SIZE = 32f

/**
 * ラベルの棒グラフの方向の位置.
 */
private const val LABEL_BOTTOM_OFFSET = 40f

/**
 * 棒グラフ.
 *
 * @param modifier [Modifier]
 * @param data 棒グラフに表示するデータ
 * @param animationDuration アニメーションの時間
 * @param animationEasing アニメーションのイージング
 * @param barWidth 棒グラフの幅、指定なしの場合は「表示幅を棒グラフの数 + 3で割った幅」で表示する
 * @param barSpacing 棒グラフ間のスペース、指定なしの場合は棒グラフ幅の20%を確保する
 */
@Composable
fun AngerLogBarChart(
    modifier: Modifier = Modifier,
    data: BarData,
    animationDuration: Int = DEFAULT_ANIMATION_DURATION,
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

            val bWidth =
                barWidth
                    ?: (size.width / (data.getItemCount() + BAR_WIDTH_OFFSET)) // +3した等分を棒グラフの横幅にする
            val bSpacing = barSpacing ?: (bWidth * BAR_SPACING_RATE) // 棒の横幅の20%をスペースにする

            // 棒と間隔の全体の幅
            val totalBarsWidth = data.getItemCount() * bWidth + (data.getItemCount() - 1) * bSpacing
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
                strokeWidth = BASE_LINE_STROKE,
            )

            val paint =
                android.graphics.Paint().apply {
                    color = android.graphics.Color.GRAY
                    textAlign = android.graphics.Paint.Align.CENTER
                    textSize = LABEL_TEXT_SIZE
                }

            // データ最大値
            val maxBarData = data.getMaxSize()
            for ((i, d) in data.getItems().withIndex()) {
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
                    baseLine + LABEL_BOTTOM_OFFSET,
                    paint,
                )
            }
        },
    )
}
