package jp.co.human.localnote.database

import androidx.room3.Database
import androidx.room3.RoomDatabase
import jp.co.human.localnote.database.entity.UserEntity
import jp.co.human.localnote.database.Dao.UserDao

@Database(entities = [UserEntity::class], version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
}