package io.github.kurramkurram.angerlog.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import io.github.kurramkurram.angerlog.data.dao.NewsStateDao
import io.github.kurramkurram.angerlog.model.NewsState
import io.github.kurramkurram.angerlog.util.DateConverter

/**
 * お知らせ既読状態データベース.
 */
@Database(entities = [NewsState::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class NewsStateDatabase : RoomDatabase() {
    abstract fun newsStateDao(): NewsStateDao

    companion object {

        /**
         * Database名.
         */
        private const val DB_NAME = "NewsState.db"

        @Volatile
        private var instance: NewsStateDatabase? = null

        /**
         * インスタンスを取得する.
         *
         * @param context [Context]
         * @return [NewsStateDatabase]
         */
        fun getDatabases(context: Context): NewsStateDatabase {
            val tmpInstance = instance
            if (tmpInstance != null) return tmpInstance
            synchronized(this) {
                val i =
                    Room.databaseBuilder(
                        context.applicationContext,
                        NewsStateDatabase::class.java,
                        DB_NAME,
                    ).apply {
                        allowMainThreadQueries()
                        setJournalMode(JournalMode.TRUNCATE)
                    }.build()
                instance = i
                return i
            }
        }
    }
}