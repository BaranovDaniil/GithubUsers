package com.example.githubusers

import androidx.room.*

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "login") val login: String?,
    @ColumnInfo(name="avatar_url") val avatarUrl: String?
)

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
}

@Database(entities = [User::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}