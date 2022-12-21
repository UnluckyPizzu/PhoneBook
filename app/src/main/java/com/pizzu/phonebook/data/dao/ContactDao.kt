package com.pizzu.phonebook.data.dao

import androidx.room.*
import com.pizzu.phonebook.model.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * from contact ORDER BY name ASC")
    fun getContact(): Flow<List<Contact>>

    @Insert
    suspend fun insert(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)
}