package com.pizzu.phonebook.viewmodel

import androidx.lifecycle.*
import com.pizzu.phonebook.data.dao.ContactDao
import com.pizzu.phonebook.model.Contact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ContactViewModel(private val contactDao: ContactDao) : ViewModel() {

    val allContacts: LiveData<List<Contact>> = contactDao.getContacts().asLiveData()

    /**
     * Crea e restituisce un nuovo contatto dai parametri dati in input.
     * @param contactName il nome del contatto.
     * @param contactSurname il cognome del contatto.
     * @param contactGender il sesso del contatto.
     * @param contactTelephone il numero di telefono del contatto.
     * @param contactBirthday la data di nascita del contatto.
     * @param contactEmail l'email del contatto
     * @return il contatto creato.
     */
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

    /**
     * Crea un nuovo contatto dai parametri dati in input e lo inserisce nel db
     * @param contactName il nome del contatto.
     * @param contactSurname il cognome del contatto.
     * @param contactGender il sesso del contatto.
     * @param contactTelephone il numero di telefono del contatto.
     * @param contactBirthday la data di nascita del contatto.
     * @param contactEmail l'email del contatto
     */
    fun addNewContact(contactName: String, contactSurname: String, contactGender: Boolean, contactTelephone: String, contactEmail: String, contactBirthday: String) {
        val newContact = getNewContact(contactName, contactSurname, contactGender,contactTelephone,contactEmail, contactBirthday)
        insertContact(newContact)
    }

    /**
     * Inserisce nel db un contatto dato in input
     */
    private fun insertContact(newContact: Contact) {
        viewModelScope.launch {
            contactDao.insert(newContact)
        }
    }

    /**
     * Elimina nel db un contatto dato in input
     */
    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            contactDao.delete(contact)
        }
    }

    /**
     * Ottiene dal db un contatto il cui id è dato in input
     * @param id id del contatto da ottenere
     * @return LiveData del contatto
     */
    fun retrieveContact(id: Int): LiveData<Contact> {
        return contactDao.getContact(id).asLiveData()
    }

    /**
     * Crea un nuovo contatto dai parametri dati in input e lo salva sul db.
     * @param contactId id del contatto
     * @param contactName il nome del contatto.
     * @param contactSurname il cognome del contatto.
     * @param contactGender il sesso del contatto.
     * @param contactTelephone il numero di telefono del contatto.
     * @param contactBirthday la data di nascita del contatto.
     * @param contactEmail l'email del contatto
     */
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

    /**
     * Crea un nuovo contatto dai parametri dati in input.
     * @param contactId id del contatto
     * @param contactName il nome del contatto.
     * @param contactSurname il cognome del contatto.
     * @param contactGender il sesso del contatto.
     * @param contactTelephone il numero di telefono del contatto.
     * @param contactBirthday la data di nascita del contatto.
     * @param contactEmail l'email del contatto
     */
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

    /**
     * Aggiornare un contatto dato in input sul db
     * @param contact il contatto da aggiorare
     */
    private fun updateContact(contact: Contact) {
        viewModelScope.launch {
            contactDao.update(contact)
        }
    }

    /**
     * Controlla che il campo nome e telefono del contatto non siano nulli o vuoti
     * @param name il nome da controllare
     * @param telephone il numero di telefono dal controllare
     * @return un boolean in base a se i campi sono nulli o vuoti
     */
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