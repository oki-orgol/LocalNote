package jp.co.human.localnote.database.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val loginId: String,
    val password: String,
    val name: String
)