package io.github.kurramkurram.angerlog.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.github.kurramkurram.angerlog.ui.theme.CustomTheme
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

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

    fun select(dayOfWeekType: DayOfWeekType): IDayOfWeek = DAY_OF_WEEK[dayOfWeekType] ?: throw IllegalArgumentException()

    fun select(dayOfWeek: Int): IDayOfWeek {
        DAY_OF_WEEK.forEach {
            if (it.key.ordinal == dayOfWeek) return it.value
        }
        throw IllegalArgumentException()
    }

    interface IDayOfWeek {
        fun getString(): String

        @Composable
        fun getColor(): Color
    }

    class Sunday : IDayOfWeek {
        override fun getString(): String = DayOfWeek.SUNDAY.getDisplayName(TextStyle.SHORT, Locale.JAPANESE)

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.sunday
    }

    class Monday : IDayOfWeek {
        override fun getString(): String = DayOfWeek.MONDAY.getDisplayName(TextStyle.SHORT, Locale.JAPANESE)

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.weekDays
    }

    class Tuesday : IDayOfWeek {
        override fun getString(): String = DayOfWeek.TUESDAY.getDisplayName(TextStyle.SHORT, Locale.JAPANESE)

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.weekDays
    }

    class Wednesday : IDayOfWeek {
        override fun getString(): String = DayOfWeek.WEDNESDAY.getDisplayName(TextStyle.SHORT, Locale.JAPANESE)

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.weekDays
    }

    class Thursday : IDayOfWeek {
        override fun getString(): String = DayOfWeek.THURSDAY.getDisplayName(TextStyle.SHORT, Locale.JAPANESE)

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.weekDays
    }

    class Friday : IDayOfWeek {
        override fun getString(): String = DayOfWeek.FRIDAY.getDisplayName(TextStyle.SHORT, Locale.JAPANESE)

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.weekDays
    }

    class Saturday : IDayOfWeek {
        override fun getString(): String = DayOfWeek.SATURDAY.getDisplayName(TextStyle.SHORT, Locale.JAPANESE)

        @Composable
        override fun getColor(): Color = CustomTheme.colorScheme.saturday
    }
}

enum class DayOfWeekType {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
}
