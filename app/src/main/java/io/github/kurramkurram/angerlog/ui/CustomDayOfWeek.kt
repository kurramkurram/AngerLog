package io.github.kurramkurram.angerlog.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.github.kurramkurram.angerlog.ui.theme.CustomTheme
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

/**
 * カスタムした曜日.
 */
class CustomDayOfWeek {
    companion object {
        private val DAY_OF_WEEK: Map<DayOfWeekType, IDayOfWeek> =
            mapOf(
                DayOfWeekType.SUNDAY to Sunday(),
                DayOfWeekType.MONDAY to Monday(),
                DayOfWeekType.TUESDAY to Tuesday(),
                DayOfWeekType.WEDNESDAY to Wednesday(),
                DayOfWeekType.THURSDAY to Thursday(),
                DayOfWeekType.FRIDAY to Friday(),
                DayOfWeekType.SATURDAY to Saturday(),
            )
    }

    /**
     * 曜日を取得する.
     *
     * @param dayOfWeekType 曜日タイプ
     * @return 曜日
     */
    fun select(dayOfWeekType: DayOfWeekType): IDayOfWeek =
        DAY_OF_WEEK[dayOfWeekType] ?: throw IllegalArgumentException()

    /**
     * 曜日を取得する.
     * [DayOfWeek]から取得できる曜日を[CustomDayOfWeek]に変換する
     * [DayOfWeek]は1オリジンで月曜開始となっているが、[CustomDayOfWeek]では0オリジンで日曜開始としたいため、変換する
     *
     * @param dayOfWeek 曜日
     * @return 曜日
     */
    fun select(dayOfWeek: Int): IDayOfWeek {
        if (dayOfWeek == DayOfWeek.SUNDAY.value) {
            return DAY_OF_WEEK[DayOfWeekType.SUNDAY]
                ?: throw IllegalStateException()
        }
        DAY_OF_WEEK.forEach {
            if (it.value.getValue() == dayOfWeek) return it.value
        }
        throw IllegalArgumentException()
    }

    /**
     * 曜日のデータ.
     */
    interface IDayOfWeek {
        /**
         * 曜日のインデックスを取得する.
         *
         * @return 曜日のインデックス
         */
        fun getValue(): Int

        /**
         * 曜日の文言を取得する.
         *
         * @return 曜日の文言
         */
        fun getString(): String

        /**
         * 曜日に割り当てた色を取得する.
         *
         * @return 曜日に割り当てた色
         */
        @Composable
        fun getColor(): Color
    }

    /**
     * 日曜のデータ.
     */
    class Sunday : IDayOfWeek {
        override fun getValue(): Int = 0

        override fun getString(): String =
            DayOfWeek.SUNDAY.getDisplayName(TextStyle.SHORT, Locale.JAPANESE)

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.sunday
    }

    /**
     * 月曜のデータ.
     */
    class Monday : IDayOfWeek {
        override fun getValue(): Int = 1

        override fun getString(): String =
            DayOfWeek.MONDAY.getDisplayName(TextStyle.SHORT, Locale.JAPANESE)

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.weekDays
    }

    /**
     * 火曜のデータ.
     */
    class Tuesday : IDayOfWeek {
        override fun getValue(): Int = 2

        override fun getString(): String =
            DayOfWeek.TUESDAY.getDisplayName(TextStyle.SHORT, Locale.JAPANESE)

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.weekDays
    }

    /**
     * 水曜のデータ.
     */
    class Wednesday : IDayOfWeek {
        override fun getValue(): Int = 3

        override fun getString(): String =
            DayOfWeek.WEDNESDAY.getDisplayName(TextStyle.SHORT, Locale.JAPANESE)

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.weekDays
    }

    /**
     * 木曜のデータ.
     */
    class Thursday : IDayOfWeek {
        override fun getValue(): Int = 4

        override fun getString(): String =
            DayOfWeek.THURSDAY.getDisplayName(TextStyle.SHORT, Locale.JAPANESE)

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.weekDays
    }

    /**
     * 金曜のデータ.
     */
    class Friday : IDayOfWeek {
        override fun getValue(): Int = 5

        override fun getString(): String =
            DayOfWeek.FRIDAY.getDisplayName(TextStyle.SHORT, Locale.JAPANESE)

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.weekDays
    }

    /**
     * 土曜のデータ.
     */
    class Saturday : IDayOfWeek {
        override fun getValue(): Int = 6

        override fun getString(): String =
            DayOfWeek.SATURDAY.getDisplayName(TextStyle.SHORT, Locale.JAPANESE)

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.saturday
    }
}

/**
 * 曜日のタイプ.
 */
enum class DayOfWeekType {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
}
