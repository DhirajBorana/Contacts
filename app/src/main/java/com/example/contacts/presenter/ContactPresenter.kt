package com.example.contacts.presenter

import com.example.contacts.Contract.*
import com.example.contacts.model.*
import com.example.contacts.model.Number

class ContactPresenter(_view: View, _model: Model) : Presenter {

    private val view = _view
    private val model = _model

    override fun start() {
        view.start()
    }

    override fun userInput(input: String?) {
        when (input) {
            "1" -> view.create()
            "2" -> {
                if (isEmpty()) view.printNoContactsToEdit()
                else {
                    view.display(model.getAllContacts())
                    view.edit()
                }
            }
            "3" -> {
                if (isEmpty()) view.printNoContactsToDelete()
                else {
                    view.display(model.getAllContacts())
                    view.delete()
                }
            }
            "4" -> {
                if (isEmpty()) view.printNoContactsToSearch()
                else view.search()
            }
            "5" -> {
                if (isEmpty()) view.printNoContactsToDisplay()
                else view.display(model.getAllContacts())
            }
            "6" -> view.exit()
            else -> {
                view.printWrongInput()
            }
        }
        if (input != "6") view.start()
    }

    override fun checkName(input: String): String {
        if (input.isNotEmpty() && input.isNotBlank()) return input
        else {
            view.printWrongInput()
            return view.getName()
        }
    }

    override fun checkLabel(input: String): String {
        if (input.isNotEmpty() && input.isNotBlank()) return input
        else {
            view.printWrongInput()
            return view.getLabel()
        }
    }

    override fun checkNumber(input: String): Long {
        if (isNumber(input)) return input.toLong()
        else {
            view.printWrongInput()
            return view.getNumber()
        }
    }

    override fun checkYesNo(input: String): Boolean {
        if (input == "y" || input == "Y") return true
        else if (input == "n" || input == "N") return false
        else {
            view.printWrongInput()
            return view.wantToAddAnother()
        }
    }

    override fun checkEmail(input: String): String {
        if (input.isNotEmpty() && input.isNotBlank()) return input
        else {
            view.printWrongInput()
            return view.getEmail()
        }
    }

    override fun checkStreet(input: String): String {
        if (input.isNotEmpty() && input.isNotBlank()) return input
        else {
            view.printWrongInput()
            return view.getStreet()
        }
    }

    override fun checkCity(input: String): String {
        if (input.isNotEmpty() && input.isNotBlank()) return input
        else {
            view.printWrongInput()
            return view.getCity()
        }
    }

    override fun checkState(input: String): String {
        if (input.isNotEmpty() && input.isNotBlank()) return input
        else {
            view.printWrongInput()
            return view.getState()
        }
    }

    override fun checkPincode(input: String): Long {
        if (isNumber(input)) return input.toLong()
        else {
            view.printWrongInput()
            return view.getPincode()
        }
    }

    override fun checkOrganization(input: String): String {
        if (input.isNotEmpty() && input.isNotBlank()) return input
        else {
            view.printWrongInput()
            return view.getOrganization()
        }
    }

    override fun addContact(contact: Contact) {
        model.addContact(contact)
    }

    override fun deleteContact(input: String) {
        if (isNumber(input) && input.toInt() in 1..model.getSize()) {
            model.deleteContact(input.toInt() - 1)
        } else {
            view.printWrongInput()
            view.delete()
        }
    }

    override fun searchContact(input: String) {
        val searchedResult = model.getAllContacts().filter { it.toString().contains(input) }
        if (searchedResult.isEmpty()) view.printNotFound()
        else view.display(searchedResult)
    }

    override fun editContact(input: String) {
        if (isNumber(input) && input.toInt() in 1..model.getSize()) {
            view.whatToEdit(model.getContact(input.toInt() - 1))
        } else {
            view.printWrongInput()
            view.edit()
        }
    }

    override fun whatToEdit(contact: Contact, input: String) {
        when (input) {
            "1" -> editName(contact)
            "2" -> editNumbers(contact)
            "3" -> editEmails(contact)
            "4" -> editAddresses(contact)
            "5" -> editOrganizations(contact)
            else -> {
                view.printWrongInput()
                view.whatToEdit(contact)
            }
        }
    }

    override fun editName(contact: Contact) {
        val newName = view.getName()
        contact.name = newName
        view.displayEditedContact(contact)
        wantToEditMore(contact)
    }

    override fun editNumbers(contact: Contact) {
        val canDelete = contact.numbers.size > 1
        val option = view.editOptions(canDelete = canDelete)
        if (option == "E" || option == "e") {
            val index = view.editNumbers(contact)
            editNumber(contact, index)
        } else if ((option == "D" || option == "d") && canDelete) {
            val index = view.editNumbers(contact)
            deleteNumber(contact, index)
        } else if (option == "A" || option == "a") {
            addNumber(contact)
        } else {
            view.printWrongInput()
            editNumbers(contact)
        }
    }

    override fun checkEditNumber(contact: Contact, input: String): Int {
        if (isNumber(input) && input.toInt() in 1..contact.numbers.size) {
            return input.toInt() - 1
        } else {
            view.printWrongInput()
            return view.editNumbers(contact)
        }
    }

    private fun editNumber(contact: Contact, index: Int) {
        val newNumber = view.getNumber()
        contact.numbers.elementAt(index).number = newNumber
        view.displayEditedContact(contact)
        wantToEditMore(contact)
    }

    private fun addNumber(contact: Contact) {
        val newLabel = view.getLabel()
        val newNumber = view.getNumber()
        contact.numbers.add(Number(newLabel, newNumber))
        view.displayEditedContact(contact)
        wantToEditMore(contact)
    }

    private fun deleteNumber(contact: Contact, index: Int) {
        val number = contact.numbers.elementAt(index)
        contact.numbers.remove(number)
        view.displayEditedContact(contact)
        wantToEditMore(contact)
    }

    override fun editEmails(contact: Contact) {
        val canEdit = contact.emails.size != 0
        val option = view.editOptions(canEdit = canEdit, canDelete = canEdit)
        if ((option == "E" || option == "e") && canEdit) {
            val index = view.editEmails(contact)
            editEmail(contact, index)
        } else if ((option == "D" || option == "d") && canEdit) {
            val index = view.editEmails(contact)
            deleteEmail(contact, index)
        } else if (option == "A" || option == "a") {
            addEmail(contact)
        } else {
            view.printWrongInput()
            editEmails(contact)
        }
    }

    override fun checkEditEmail(contact: Contact, input: String): Int {
        if (isNumber(input) && input.toInt() in 1..contact.emails.size) {
            return input.toInt() - 1
        } else {
            view.printWrongInput()
            return view.editEmails(contact)
        }
    }

    private fun editEmail(contact: Contact, index: Int) {
        val newEmail = view.getEmail()
        contact.emails.elementAt(index).email = newEmail
        view.displayEditedContact(contact)
        wantToEditMore(contact)
    }

    private fun addEmail(contact: Contact) {
        val newLabel = view.getLabel()
        val newEmail = view.getEmail()
        contact.emails.add(Email(newLabel, newEmail))
        view.displayEditedContact(contact)
        wantToEditMore(contact)
    }

    private fun deleteEmail(contact: Contact, index: Int) {
        val email = contact.emails.elementAt(index)
        contact.emails.remove(email)
        view.displayEditedContact(contact)
        wantToEditMore(contact)
    }

    override fun editAddresses(contact: Contact) {
        val canEdit = contact.addresses.size != 0
        val option = view.editOptions(canEdit = canEdit, canDelete = canEdit)
        if ((option == "E" || option == "e") && canEdit) {
            val index = view.editAddresses(contact)
            editAddress(contact, index)
        } else if ((option == "D" || option == "d") && canEdit) {
            val index = view.editAddresses(contact)
            deleteAddress(contact, index)
        } else if (option == "A" || option == "a") {
            addAddress(contact)
        } else {
            view.printWrongInput()
            editAddresses(contact)
        }
    }

    override fun checkEditAddress(contact: Contact, input: String): Int {
        if (isNumber(input) && input.toInt() in 1..contact.addresses.size) {
            return input.toInt() - 1
        } else {
            view.printWrongInput()
            return view.editAddresses(contact)
        }
    }

    private fun editAddress(contact: Contact, index: Int) {
        val newStreet = view.getStreet()
        val newCity = view.getCity()
        val newState = view.getState()
        val newPincode = view.getPincode()
        contact.addresses.elementAt(index).apply {
            street = newStreet
            city = newCity
            state = newState
            pincode = newPincode
        }
        view.displayEditedContact(contact)
        wantToEditMore(contact)
    }

    private fun addAddress(contact: Contact) {
        val newLabel = view.getLabel()
        val newStreet = view.getStreet()
        val newCity = view.getCity()
        val newState = view.getState()
        val newPincode = view.getPincode()
        contact.addresses.add(Address(newLabel, newStreet, newCity, newState, newPincode))
        view.displayEditedContact(contact)
        wantToEditMore(contact)
    }

    private fun deleteAddress(contact: Contact, index: Int) {
        val address = contact.addresses.elementAt(index)
        contact.addresses.remove(address)
        view.displayEditedContact(contact)
        wantToEditMore(contact)
    }

    override fun editOrganizations(contact: Contact) {
        val canEdit = contact.organizations.size != 0
        val option = view.editOptions(canEdit = canEdit, canDelete = canEdit)
        if ((option == "E" || option == "e") && canEdit) {
            val index = view.editOrganizations(contact)
            editOrganization(contact, index)
        } else if ((option == "D" || option == "d") && canEdit) {
            val index = view.editOrganizations(contact)
            deleteOrganization(contact, index)
        } else if (option == "A" || option == "a") {
            addOrganization(contact)
        } else {
            view.printWrongInput()
            editOrganizations(contact)
        }
    }

    override fun checkEditOrganization(contact: Contact, input: String): Int {
        if (isNumber(input) && input.toInt() in 1..contact.organizations.size) {
            return input.toInt() - 1
        } else {
            view.printWrongInput()
            return view.editOrganizations(contact)
        }
    }

    private fun editOrganization(contact: Contact, index: Int) {
        val newOrganization = view.getOrganization()
        contact.organizations.elementAt(index).name = newOrganization
        view.displayEditedContact(contact)
        wantToEditMore(contact)
    }

    private fun addOrganization(contact: Contact) {
        val newOrganization = view.getOrganization()
        contact.organizations.add(Organization(newOrganization))
        view.displayEditedContact(contact)
        wantToEditMore(contact)
    }

    private fun deleteOrganization(contact: Contact, index: Int) {
        val organization = contact.organizations.elementAt(index)
        contact.organizations.remove(organization)
        view.displayEditedContact(contact)
        wantToEditMore(contact)
    }

    private fun wantToEditMore(contact: Contact) {
        if (view.wantToContinue()) {
            view.whatToEdit(contact)
        }
    }

    private fun isNumber(input: String): Boolean {
        return try {
            input.toLong()
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun isEmpty(): Boolean {
        return model.getSize() == 0
    }
}