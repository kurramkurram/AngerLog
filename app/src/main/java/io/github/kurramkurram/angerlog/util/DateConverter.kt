package io.github.kurramkurram.angerlog.util

import android.annotation.SuppressLint
import androidx.room.TypeConverter
import io.github.kurramkurram.angerlog.model.Time
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class DateConverter {
    @TypeConverter
    fun timestampToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    companion object {
        fun dateToCalendar(date: Date): Calendar {
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar
        }

        fun dateTimeToDate(
            date: Date,
            time: Time,
        ): Date {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.set(Calendar.HOUR_OF_DAY, time.hour)
            calendar.set(Calendar.MINUTE, time.minute)
            return calendar.time
        }

        fun yearMonthDayToUnixTime(
            year: Int,
            month: Int,
            day: Int,
        ): Long {
            val c = Calendar.getInstance()
            c.set(year, month, day)
            return c.timeInMillis
        }

        fun Date.dateToString(): String {
            @SuppressLint("SimpleDateFormat")
            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm")
            return sdf.format(this)
        }
    }
}
