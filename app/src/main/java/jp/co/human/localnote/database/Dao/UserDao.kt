package jp.co.human.localnote.database.Dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import jp.co.human.localnote.database.entity.UserEntity

@Dao
interface UserDao{
    @Query("SELECT COUNT(*) FROM users")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userEntity: UserEntity): Long
}