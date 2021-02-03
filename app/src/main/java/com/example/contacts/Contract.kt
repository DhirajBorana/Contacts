package com.example.contacts

import com.example.contacts.model.Contact

interface Contract {

    interface View {
        fun start()
        fun create()
        fun getName(): String
        fun getLabel(): String
        fun getNumber(): Long
        fun wantToAddAnother(): Boolean
        fun wantToAddEmail(): Boolean
        fun getEmail(): String
        fun wantToAddAddress(): Boolean
        fun getStreet(): String
        fun getCity(): String
        fun getState(): String
        fun getPincode(): Long
        fun wantToAddOrganization(): Boolean
        fun getOrganization(): String
        fun printWrongInput()
        fun display(contacts: List<Contact>)
        fun delete()
        fun printNoContactsToEdit()
        fun printNoContactsToDelete()
        fun printNoContactsToDisplay()
        fun printNoContactsToSearch()
        fun printNotFound()
        fun search()
        fun exit()
        fun edit()
        fun whatToEdit(contact: Contact)
        fun editNumbers(contact: Contact): Int
        fun editEmails(contact: Contact): Int
        fun editAddresses(contact: Contact): Int
        fun editOrganizations(contact: Contact): Int
        fun editOptions (canEdit: Boolean = true, canAdd: Boolean = true, canDelete: Boolean = true): String
        fun displayEditedContact(contact: Contact)
        fun wantToContinue(): Boolean
    }

    interface Presenter {
        fun start()
        fun userInput(input: String?)
        fun checkName(input: String): String
        fun checkLabel(input: String): String
        fun checkNumber(input: String): Long
        fun checkYesNo(input: String): Boolean
        fun checkEmail(input: String): String
        fun checkStreet(input: String): String
        fun checkCity(input: String): String
        fun checkState(input: String): String
        fun checkPincode(input: String): Long
        fun checkOrganization(input: String): String
        fun addContact(contact: Contact)
        fun deleteContact(input: String)
        fun searchContact(input: String)
        fun editContact(input: String)

        fun whatToEdit(contact: Contact, input: String)
        fun editName(contact: Contact)
        fun editNumbers(contact: Contact)
        fun checkEditNumber(contact: Contact, input: String): Int

        fun editEmails(contact: Contact)
        fun checkEditEmail(contact: Contact, input: String): Int

        fun editAddresses(contact: Contact)
        fun checkEditAddress(contact: Contact, input: String): Int

        fun editOrganizations(contact: Contact)
        fun checkEditOrganization(contact: Contact, input: String): Int
    }

    interface Model {
        fun addContact(contact: Contact)
        fun getAllContacts(): List<Contact>
        fun getSize(): Int
        fun deleteContact(index: Int)
        fun getContact(index: Int): Contact
    }
}