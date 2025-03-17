package io.github.kurramkurram.angerlog.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.github.kurramkurram.angerlog.ui.theme.CustomTheme

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

    fun select(levelType: AngerLevelType): IAngerLevel = ANGER_LEVEL[levelType] ?: throw IllegalArgumentException()

    fun select(level: Int): IAngerLevel {
        ANGER_LEVEL.forEach {
            if (it.value.getValue() == level) return it.value
        }
        throw IllegalArgumentException()
    }

    fun getLevel(angerLevelType: AngerLevelType): Int = ANGER_LEVEL[angerLevelType]?.getValue() ?: throw IllegalArgumentException()

    fun getMinLevel(): Int = ANGER_LEVEL[AngerLevelType.entries.first()]?.getValue() ?: throw IllegalStateException()

    fun getMaxLevel(): Int = ANGER_LEVEL[AngerLevelType.entries.last()]?.getValue() ?: throw IllegalStateException()

    fun getAngerLevelType(level: Int): AngerLevelType {
        if (level == 0) return AngerLevelType.LEVEL_1
        ANGER_LEVEL.forEach {
            if (it.value.getValue() == level) return it.key
        }
        throw IllegalArgumentException()
    }

    interface IAngerLevel {
        fun getValue(): Int

        @Composable
        fun getColor(): Color
    }

    class AngerLevel1 : IAngerLevel {
        override fun getValue(): Int = 1

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.angerLevel1
    }

    class AngerLevel2 : IAngerLevel {
        override fun getValue(): Int = 2

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.angerLevel2
    }

    class AngerLevel3 : IAngerLevel {
        override fun getValue(): Int = 3

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.angerLevel3
    }

    class AngerLevel4 : IAngerLevel {
        override fun getValue(): Int = 4

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.angerLevel4
    }

    class AngerLevel5 : IAngerLevel {
        override fun getValue(): Int = 5

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.angerLevel5
    }
}

enum class AngerLevelType {
    LEVEL_1,
    LEVEL_2,
    LEVEL_3,
    LEVEL_4,
    LEVEL_5,
}
