package io.github.kurramkurram.angerlog.ui.component.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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

@Composable
fun AngerLogLineChart(
    modifier: Modifier = Modifier,
    lineData: List<LineData>,
    minX: Float? = null,
    maxX: Float? = null,
    maxY: Int? = null,
    lineColor: Color = Color.LightGray,
    lineStroke: Stroke = Stroke(5f)
) {
    Canvas(
        modifier = modifier.padding(20.dp),
        onDraw = {
            drawBezierCurve(
                size = size,
                lineData = lineData,
                fixedMinPoint = minX,
                fixedMaxPoint = maxX,
                fixedMaxY = maxY,
                lineColor = lineColor,
                lineStroke = lineStroke
            )
        }
    )
}

private fun DrawScope.drawBezierCurve(
    size: Size,
    lineData: List<LineData>,
    fixedMinPoint: Float? = null,
    fixedMaxPoint: Float? = null,
    fixedMaxY: Int? = null,
    lineColor: Color,
    lineStroke: Stroke
) {
    val maxPoint = fixedMaxPoint ?: lineData.maxOf { it.y }
    val minPoint = fixedMinPoint ?: lineData.minOf { it.y }
    val total = maxPoint - minPoint
    val height = size.height
    val width = size.width
    val maxY = (fixedMaxY ?: lineData.size)
    val xSpacing = width / maxY
    var lastPoint: Offset? = null
    val path = Path()
    for ((index, data) in lineData.withIndex()) {
        val x = data.x * xSpacing
        val y = height - height * ((data.y - minPoint) / total)
        if (lastPoint != null) {
            path.lineTo(x, y)
        }
        lastPoint = Offset(x, y)
        if (index == 0) {
            path.moveTo(x, y)
        }
    }

    drawPath(
        path = path,
        color = lineColor,
        style = lineStroke
    )

    val baseLine = size.height
    drawLine(
        color = Color.DarkGray,
        start = Offset(0f, baseLine),
        end = Offset(size.width, baseLine),
        strokeWidth = 2f
    )

    val paint = android.graphics.Paint().apply {
        color = android.graphics.Color.GRAY
        textAlign = android.graphics.Paint.Align.CENTER
        textSize = 32f
    }
    for (i in 1..maxY step 5) {
        drawContext.canvas.nativeCanvas.drawText(
            i.toString(),
            i * xSpacing,
            baseLine + 40f,
            paint
        )
    }
}

private fun buildCurveLine(path: Path, startPoint: Offset, endPoint: Offset) {
    val firstControlPoint = Offset(
        x = startPoint.x + (endPoint.x - startPoint.x) / 2F,
        y = startPoint.y,
    )
    val secondControlPoint = Offset(
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

data class LineData(val x: Int, val y: Float)