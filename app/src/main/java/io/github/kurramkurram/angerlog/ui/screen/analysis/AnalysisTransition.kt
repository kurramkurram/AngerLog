package io.github.kurramkurram.angerlog.ui.screen.analysis

import io.github.kurramkurram.angerlog.ui.component.chart.line.LineData
import io.github.kurramkurram.angerlog.ui.component.chart.line.LineItemDto

class AnalysisTransition {
    private val dataOfDay = mutableMapOf<Int, MutableList<Int>>()

    fun update(analysisItemOfDayDto: AnalysisItemOfDayDto) {
        val day = analysisItemOfDayDto.day
        val level = analysisItemOfDayDto.level
        val list = dataOfDay[day]
        if (list == null) {
            dataOfDay[day] = mutableListOf(level)
        } else {
            list.add(level)
        }
    }

    fun getLineData(): LineData {
        val lineData = mutableListOf<LineItemDto>()
        dataOfDay.map { lineData.add(LineItemDto(it.key, it.value.average().toFloat())) }

        return LineData(lineData = lineData)
    }
}
