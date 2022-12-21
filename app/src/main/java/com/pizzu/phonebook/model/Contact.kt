package com.pizzu.phonebook.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @NonNull @ColumnInfo(name = "telephone_number")
    val telephoneNumber: String,
    @NonNull @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "surname")
    val surname: String?,
    @ColumnInfo(name = "email")
    val email: String?,
    @ColumnInfo(name = "gender")
    val gender: Boolean?,
    @ColumnInfo(name = "birthday")
    val birthday: Int?
)