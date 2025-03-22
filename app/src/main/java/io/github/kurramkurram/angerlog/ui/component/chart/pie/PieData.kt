package io.github.kurramkurram.angerlog.ui.component.chart.pie

/**
 * 円グラフのデータ.
 *
 * @param pieItems 円グラフの各データのリスト
 */
data class PieData(private val pieItems: List<PieItemDto>) {
    /**
     * データ数を返す.
     *
     * @return データ数
     */
    fun getItemCount(): Int = pieItems.size

    /**
     * 円グラフのデータを返す.
     *
     * @return 円グラフのデータ
     */
    fun getItems(): List<PieItemDto> = pieItems

    /**
     * 円グラフの各データを返す.
     *
     * @param index 取得したい円グラフのデータのindex
     * @return 円グラフのデータ
     * @exception IllegalArgumentException indexが円グラフのデータの件数外の場合にthrowする
     */
    fun getItem(index: Int): PieItemDto {
        if (index < 0 || index >= getItemCount()) throw IllegalArgumentException()
        return pieItems[index]
    }
}
