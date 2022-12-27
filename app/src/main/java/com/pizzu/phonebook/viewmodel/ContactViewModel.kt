package com.pizzu.phonebook.viewmodel

import androidx.lifecycle.*
import com.pizzu.phonebook.data.dao.ContactDao
import com.pizzu.phonebook.model.Contact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ContactViewModel(private val contactDao: ContactDao) : ViewModel() {

    val allContacts: LiveData<List<Contact>> = contactDao.getContacts().asLiveData()

    private fun getNewContact(contactName: String, contactSurname: String, contactGender: Boolean, contactTelephone: String, contactEmail: String, contactBirthday: String): Contact {
        return Contact(
            name = contactName,
            surname = contactSurname,
            gender = contactGender,
            telephoneNumber = contactTelephone,
            email = contactEmail,
            birthday = contactBirthday
        )
    }

    fun addNewContact(contactName: String, contactSurname: String, contactGender: Boolean, contactTelephone: String, contactEmail: String, contactBirthday: String) {
        val newContact = getNewContact(contactName, contactSurname, contactGender,contactTelephone,contactEmail, contactBirthday)
        insertContact(newContact)
    }

    private fun insertContact(newContact: Contact) {
        viewModelScope.launch {
            contactDao.insert(newContact)
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            contactDao.delete(contact)
        }
    }

    fun retrieveContact(id: Int): LiveData<Contact> {
        return contactDao.getContact(id).asLiveData()
    }

    fun updateContact(
        contactId: Int,
        contactName: String,
        contactSurname: String,
        contactGender: Boolean,
        contactTelephone: String,
        contactEmail: String,
        contactBirthday: String
    ) {
        val updated = getUpdatedContact(contactId, contactName, contactSurname, contactGender,contactTelephone,contactEmail, contactBirthday)
        updateContact(updated)
    }

    private fun getUpdatedContact(
        contactId: Int,
        contactName: String,
        contactSurname: String,
        contactGender: Boolean,
        contactTelephone: String,
        contactEmail: String,
        contactBirthday: String
    ): Contact {
        return Contact(
            id = contactId,
            name = contactName,
            surname = contactSurname,
            gender = contactGender,
            telephoneNumber = contactTelephone,
            email = contactEmail,
            birthday = contactBirthday
        )
    }

    private fun updateContact(contact: Contact) {
        viewModelScope.launch {
            contactDao.update(contact)
        }
    }

    fun isEntryValid(name: String, telephone: String): Boolean {
        return !(name.isBlank() || telephone.isBlank())
    }


}

class ContactViewModelFactory(private val contactDao: ContactDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactViewModel(contactDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}