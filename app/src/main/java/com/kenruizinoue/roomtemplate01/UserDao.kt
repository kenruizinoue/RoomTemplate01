package com.kenruizinoue.roomtemplate01

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)
    @Query("SELECT * FROM User")
    fun getUsers(): List<User>
}