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

    @Query("SELECT * FROM users WHERE loginId = :id LIMIT 1")
    suspend fun findById(id: String): UserEntity?

    @Query("UPDATE users SET name = :name WHERE loginId = :id")
    suspend fun updateName(id: String, name: String): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userEntity: UserEntity): Long


}