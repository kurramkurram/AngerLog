package io.github.kurramkurram.angerlog.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.github.kurramkurram.angerlog.ui.theme.CustomTheme

/**
 * 怒りの強さ.
 */
class AngerLevel {
    companion object {
        private val ANGER_LEVEL: Map<AngerLevelType, IAngerLevel> =
            mapOf(
                AngerLevelType.LEVEL_1 to AngerLevel1(),
                AngerLevelType.LEVEL_2 to AngerLevel2(),
                AngerLevelType.LEVEL_3 to AngerLevel3(),
                AngerLevelType.LEVEL_4 to AngerLevel4(),
                AngerLevelType.LEVEL_5 to AngerLevel5(),
            )
    }

    /**
     * 怒りの強さを取得する.
     *
     * @param levelType 怒りの強さタイプ
     * @return 怒りの強さ
     */
    fun select(levelType: AngerLevelType): IAngerLevel =
        ANGER_LEVEL[levelType] ?: throw IllegalArgumentException()


    /**
     * 怒りの強さデータを取得する.
     *
     * @param level 怒りの強さ
     * @return 怒りの強さ
     */
    fun select(level: Int): IAngerLevel {
        ANGER_LEVEL.forEach {
            if (it.value.getValue() == level) return it.value
        }
        throw IllegalArgumentException()
    }

    /**
     * 怒りの強さを取得する.
     *
     * @param angerLevelType 怒りの強さタイプ
     * @return 怒りの強さ
     */
    fun getLevel(angerLevelType: AngerLevelType): Int =
        ANGER_LEVEL[angerLevelType]?.getValue() ?: throw IllegalArgumentException()

    /**
     * 定義されている怒りの強さの最小値を返す.
     *
     * @return 怒りの強さの最小値
     */
    fun getMinLevel(): Int =
        ANGER_LEVEL[AngerLevelType.entries.first()]?.getValue() ?: throw IllegalStateException()

    /**
     * 定義されている怒りの強さの最大値を返す.
     *
     * @return 怒りの強さの最大値
     */
    fun getMaxLevel(): Int =
        ANGER_LEVEL[AngerLevelType.entries.last()]?.getValue() ?: throw IllegalStateException()

    /**
     * 怒りの強さタイプを返す.
     *
     * @return 怒りの強さタイプ
     */
    fun getAngerLevelType(level: Int): AngerLevelType {
        if (level == 0) return AngerLevelType.LEVEL_1
        ANGER_LEVEL.forEach {
            if (it.value.getValue() == level) return it.key
        }
        throw IllegalArgumentException()
    }

    /**
     * 怒りの強さのデータ.
     */
    interface IAngerLevel {
        /**
         * 強さを取得する.
         *
         * @return 強さ
         */
        fun getValue(): Int

        /**
         * 怒りに割り当てた色を取得する.
         *
         * @return 怒りに割り当てた色
         */
        @Composable
        fun getColor(): Color
    }

    /**
     * 怒りの強さ1のデータ.
     */
    class AngerLevel1 : IAngerLevel {
        override fun getValue(): Int = 1

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.angerLevel1
    }

    /**
     * 怒りの強さ2のデータ.
     */
    class AngerLevel2 : IAngerLevel {
        override fun getValue(): Int = 2

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.angerLevel2
    }

    /**
     * 怒りの強さ3のデータ.
     */
    class AngerLevel3 : IAngerLevel {
        override fun getValue(): Int = 3

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.angerLevel3
    }

    /**
     * 怒りの強さ4のデータ.
     */
    class AngerLevel4 : IAngerLevel {
        override fun getValue(): Int = 4

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.angerLevel4
    }

    /**
     * 怒りの強さ5のデータ.
     */
    class AngerLevel5 : IAngerLevel {
        override fun getValue(): Int = 5

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.angerLevel5
    }
}

/**
 * 怒りの強さタイプ.
 */
enum class AngerLevelType {
    LEVEL_1,
    LEVEL_2,
    LEVEL_3,
    LEVEL_4,
    LEVEL_5,
}
