package io.github.kurramkurram.angerlog.ui.component.chart.line

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp

/**
 * 標準の折れ線グラフの太さ.
 */
private const val DEFAULT_LINE_STROKE = 5f

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
 * ラベル表示の間隔.
 */
private const val DEFAULT_LABEL_DISPLAY_SPAN = 5

/**
 * 折れ線グラフの半径.
 */
private const val DOT_RADIUS = 6f

/**
 * 折れ線グラフ.
 *
 * @param modifier [Modifier]
 * @param lineData 折れ線グラフに表示するデータ
 * @param maxX X軸方向の最大値
 * @param minY Y軸方向の最小値
 * @param minY Y軸方向の最大値
 * @param labelSpan ラベルの間隔
 * @param lineColor 折れ線グラフの色
 * @param lineStroke 折れ線グラフの太さ
 */
@Composable
fun AngerLogLineChart(
    modifier: Modifier = Modifier,
    lineData: LineData,
    maxX: Int? = null,
    minY: Float? = null,
    maxY: Float? = null,
    labelSpan: Int = DEFAULT_LABEL_DISPLAY_SPAN,
    lineColor: Color = Color.LightGray,
    lineStroke: Stroke = Stroke(DEFAULT_LINE_STROKE),
) {
    Canvas(
        modifier = modifier.padding(20.dp),
        onDraw = {
            drawBezierCurve(
                size = size,
                lineData = lineData,
                fixedMinPoint = minY,
                fixedMaxPoint = maxY,
                fixedMaxX = maxX,
                labelSpan = labelSpan,
                lineColor = lineColor,
                lineStroke = lineStroke,
            )
        },
    )
}

/**
 * 折れ線グラフを描画する.
 *
 * @param size 表示領域の大きさ
 * @param lineData 折れ線グラフに表示するデータ
 * @param fixedMaxX X軸方向の最大値
 * @param fixedMinPoint Y軸方向の最小値
 * @param fixedMaxPoint Y軸方向の最大値
 * @param labelSpan ラベルの間隔
 * @param lineColor 折れ線グラフの色
 * @param lineStroke 折れ線グラフの太さ
 */
private fun DrawScope.drawBezierCurve(
    size: Size,
    lineData: LineData,
    fixedMaxX: Int? = null,
    fixedMinPoint: Float? = null,
    fixedMaxPoint: Float? = null,
    labelSpan: Int,
    lineColor: Color,
    lineStroke: Stroke,
) {
    val maxPoint = fixedMaxPoint ?: lineData.getMaxY()
    val minPoint = fixedMinPoint ?: lineData.getMinY()
    val total = maxPoint - minPoint
    val height = size.height
    val width = size.width
    val maxX = (fixedMaxX ?: lineData.getItemCount())
    val xSpacing = width / maxX
    var lastPoint: Offset? = null
    val path = Path()
    for ((index, data) in lineData.getItems().withIndex()) {
        val x = data.x * xSpacing
        val y = height - height * ((data.y - minPoint) / total)
        if (lastPoint != null) {
            path.lineTo(x, y)
        }
        lastPoint = Offset(x, y)

        drawCircle(
            color = lineColor,
            center = lastPoint,
            radius = DOT_RADIUS,
        )

        if (index == 0) {
            path.moveTo(x, y)
        }
    }

    drawPath(
        path = path,
        color = lineColor,
        style = lineStroke,
    )

    val baseLine = size.height
    drawLine(
        color = Color.DarkGray,
        start = Offset(0f, baseLine),
        end = Offset(size.width, baseLine),
        strokeWidth = BASE_LINE_STROKE,
    )

    val paint =
        android.graphics.Paint().apply {
            color = android.graphics.Color.GRAY
            textAlign = android.graphics.Paint.Align.CENTER
            textSize = LABEL_TEXT_SIZE
        }
    for (i in 1..maxX step labelSpan) {
        drawContext.canvas.nativeCanvas.drawText(
            i.toString(),
            i * xSpacing,
            baseLine + LABEL_BOTTOM_OFFSET,
            paint,
        )
    }
}

private fun buildCurveLine(
    path: Path,
    startPoint: Offset,
    endPoint: Offset,
) {
    val firstControlPoint =
        Offset(
            x = startPoint.x + (endPoint.x - startPoint.x) / 2F,
            y = startPoint.y,
        )
    val secondControlPoint =
        Offset(
            x = startPoint.x + (endPoint.x - startPoint.x) / 2F,
            y = endPoint.y,
        )
    path.cubicTo(
        x1 = firstControlPoint.x,
        y1 = firstControlPoint.y,
        x2 = secondControlPoint.x,
        y2 = secondControlPoint.y,
        x3 = endPoint.x,
        y3 = endPoint.y,
    )
}
