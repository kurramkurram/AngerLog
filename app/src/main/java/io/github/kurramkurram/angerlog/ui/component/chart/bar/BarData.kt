package io.github.kurramkurram.angerlog.ui.component.chart.bar

/**
 * 棒グラフのデータ.
 *
 * @param items 棒グラフの各データのリスト
 */
class BarData(private val items: List<BarItemDto>) {
    /**
     * データ数を返す.
     *
     * @return データ数
     */
    fun getItemCount(): Int = items.size

    /**
     * 棒グラフのデータを返す.
     *
     * @return 棒グラフのデータ
     */
    fun getItems(): List<BarItemDto> = items

    /**
     * 各データの最大値を返す.
     *
     * @return データの最大値
     */
    fun getMaxSize(): Float = items.maxOf { it.size }
}
