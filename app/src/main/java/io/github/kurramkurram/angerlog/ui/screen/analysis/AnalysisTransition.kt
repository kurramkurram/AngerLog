package io.github.kurramkurram.angerlog.ui.screen.analysis

import io.github.kurramkurram.angerlog.ui.component.chart.line.LineData
import io.github.kurramkurram.angerlog.ui.component.chart.line.LineItemDto

/**
 * 分析画面の日別の怒りの変遷を管理する.
 */
class AnalysisTransition {
    private val dataOfDay = mutableMapOf<Int, MutableList<Int>>()

    /**
     * 日別に怒りの強さ保持する.
     *
     * @param analysisItemOfDayDto 分析画面の表示に使うデータ
     */
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

    /**
     * 折れ線グラフのデータを生成する.
     *
     * @return 折れ線グラフのデータ
     */
    fun createLineData(): LineData {
        val lineData = mutableListOf<LineItemDto>()
        dataOfDay.map { lineData.add(LineItemDto(it.key, it.value.average().toFloat())) }

        return LineData(lineData = lineData)
    }
}
