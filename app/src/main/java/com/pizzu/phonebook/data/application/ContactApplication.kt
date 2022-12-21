package com.pizzu.phonebook.data.application

import android.app.Application
import com.pizzu.phonebook.data.room.ContactRoomDatabase


class ContactApplication : Application() {
    val database : ContactRoomDatabase by lazy {ContactRoomDatabase.getDatabase(this)}
}