package jp.co.human.localnote

import androidx.room3.Dao
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import androidx.room3.Query

@Entity(tableName = "users")
data class User(
    @PrimaryKey val loginId: String,
    val password: String,
    val name: String
)

@Dao
interface UserDao{
    @Query("SELECT COUNT(*) FROM users")
    suspend fun count(): Int
}