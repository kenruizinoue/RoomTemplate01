package com.kenruizinoue.roomtemplate01

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    // with Int? = null, you can avoid providing
    // userId when creating the user object
    val userId: Int? = null,
    val firstName: String,
    val lastName: String,
    val age: Int
)