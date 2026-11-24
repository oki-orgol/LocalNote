package jp.co.human.localnote.database.provider

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import jp.co.human.localnote.database.AppDatabase

object  DbProvider {
    @Volatile private var instance: AppDatabase? = null
    fun get(context: Context): AppDatabase {
        return instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder<AppDatabase>(
                context.applicationContext, "local-note.db"
            ).setDriver(BundledSQLiteDriver())
                .build().also { instance = it }
        }
    }
}