package io.github.kurramkurram.angerlog.ui.component.chart.line

/**
 * 折れ線グラフのデータ.
 *
 * @param lineData 折れ線グラフの各データのリスト
 */
class LineData(private val lineData: List<LineItemDto>) {
    /**
     * 折れ線グラフのデータのY軸方向の最大値を返す.
     *
     * @return Y軸方向の最大値
     */
    fun getMaxY(): Float = lineData.maxOf { it.y }

    /**
     * 折れ線グラフのデータのY軸方向の最小値を返す.
     *
     * @return Y軸方向の最小値
     */
    fun getMinY(): Float = lineData.minOf { it.y }

    /**
     * データ数を返す.
     *
     * @return データ数
     */
    fun getItemCount(): Int = lineData.size

    /**
     * 折れ線グラフのデータを返す
     *
     * @return 折れ線グラフのデータ
     */
    fun getItems(): List<LineItemDto> = lineData
}
