package io.github.kurramkurram.angerlog.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.kurramkurram.angerlog.data.dao.AngerLogDao
import io.github.kurramkurram.angerlog.model.AngerLog
import io.github.kurramkurram.angerlog.util.DateConverter

@Database(entities = [AngerLog::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AngerLogDatabase : RoomDatabase() {

    abstract fun angerLogDao(): AngerLogDao

    companion object {
        private const val DB_NAME = "AngerLog.db"

        @Volatile
        private var INSTANCE: AngerLogDatabase? = null

        /**
         * インスタンスを取得する.
         *
         * @param context [Context]
         * @return [AngerLogDatabase]
         */
        fun getDatabases(context: Context): AngerLogDatabase {
            val tmpInstance = INSTANCE
            if (tmpInstance != null) return tmpInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AngerLogDatabase::class.java,
                    DB_NAME
                ).apply {
                    allowMainThreadQueries()
                    setJournalMode(JournalMode.TRUNCATE)
                }.build()
                INSTANCE = instance
                return instance
            }
        }
    }
}