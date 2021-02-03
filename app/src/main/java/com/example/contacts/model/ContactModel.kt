package com.example.contacts.model

import com.example.contacts.Contract
import java.util.*

class ContactModel : Contract.Model {

    private val contacts = TreeSet<Contact>()

    override fun addContact(contact: Contact) {
        contacts.add(contact)
    }

    override fun getAllContacts(): List<Contact> {
        return contacts.toList()
    }

    override fun getSize() = contacts.size

    override fun deleteContact(index: Int) {
        val element = contacts.elementAt(index)
        contacts.remove(element)
    }

    override fun getContact(index: Int) = contacts.elementAt(index)
}