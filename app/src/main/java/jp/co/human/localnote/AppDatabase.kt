package jp.co.human.localnote

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(entities = [User::class], version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
}