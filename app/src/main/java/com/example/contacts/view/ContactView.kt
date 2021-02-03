package com.example.contacts.view

import com.example.contacts.presenter.ContactPresenter
import com.example.contacts.Contract
import com.example.contacts.model.*
import com.example.contacts.model.Number

class ContactView : Contract.View {

    private val presenter = ContactPresenter(this, ContactModel())

    override fun start() {
        println(
            "1. Create\n" + "2. Edit\n" + "3. Delete\n" + "4. Search\n" + "5. Display\n" + "6. Exit\n"
        )
        print("Input: ")
        val userInput = readLine()
        presenter.userInput(userInput)
    }

    override fun create() {
        val name = getName()
        val numbers = getNumberList()
        val emails = getEmailList()
        val addresses = getAddressList()
        val organizations = getOrganizationList()
        presenter.addContact(Contact(name, numbers, emails, addresses, organizations))
    }

    override fun getName(): String {
        print("Enter name: ")
        val input = readLine() ?: ""
        return presenter.checkName(input)
    }

    private fun getNumberList(): MutableSet<Number> {
        val numbers = mutableSetOf<Number>()
        do {
            println("\nNUMBER INFO...")
            val label = getLabel()
            val number = getNumber()
            numbers.add(Number(label, number))
        } while (wantToAddAnother())
        return numbers.toMutableSet()
    }

    override fun getLabel(): String {
        print("Enter label: ")
        val input = readLine() ?: ""
        return presenter.checkLabel(input)
    }

    override fun getNumber(): Long {
        print("Enter number: ")
        val input = readLine() ?: ""
        return presenter.checkNumber(input)
    }

    private fun getEmailList(): MutableSet<Email> {
        if (!wantToAddEmail()) return mutableSetOf()
        val emails = mutableSetOf<Email>()
        do {
            println("\nEMAIL INFO...")
            val label = getLabel()
            val email = getEmail()
            emails.add(Email(label, email))
        } while (wantToAddAnother())
        return emails.toMutableSet()
    }

    override fun wantToAddAnother(): Boolean {
        print("Want to add another? y/n: ")
        val input = readLine() ?: ""
        return presenter.checkYesNo(input)
    }

    override fun wantToAddEmail(): Boolean {
        print("Want to add email? y/n: ")
        val input = readLine() ?: ""
        return presenter.checkYesNo(input)
    }

    override fun getEmail(): String {
        print("Enter email: ")
        val input = readLine() ?: ""
        return presenter.checkName(input)
    }

    override fun wantToAddAddress(): Boolean {
        print("Want to add address? y/n: ")
        val input = readLine() ?: ""
        return presenter.checkYesNo(input)
    }

    private fun getAddressList(): MutableSet<Address> {
        if (!wantToAddAddress()) return mutableSetOf()
        val addresses = mutableSetOf<Address>()
        do {
            println("\nADDRESS INFO...")
            val label = getLabel()
            val street = getStreet()
            val city = getCity()
            val state = getState()
            val pincode = getPincode()
            addresses.add(Address(label, street, city, state, pincode))
        } while (wantToAddAnother())
        return addresses.toMutableSet()
    }

    override fun getStreet(): String {
        print("Enter street: ")
        val input = readLine() ?: ""
        return presenter.checkStreet(input)
    }

    override fun getCity(): String {
        print("Enter city: ")
        val input = readLine() ?: ""
        return presenter.checkCity(input)
    }

    override fun getState(): String {
        print("Enter state: ")
        val input = readLine() ?: ""
        return presenter.checkState(input)
    }

    override fun getPincode(): Long {
        print("Enter pincode: ")
        val input = readLine() ?: ""
        return presenter.checkPincode(input)
    }

    override fun wantToAddOrganization(): Boolean {
        print("Want to add organization? y/n: ")
        val input = readLine() ?: ""
        return presenter.checkYesNo(input)
    }

    private fun getOrganizationList(): MutableSet<Organization> {
        if (!wantToAddOrganization()) return mutableSetOf()
        val organizations = mutableSetOf<Organization>()
        do {
            println("\nORGANIZATION INFO...")
            val organization = getOrganization()
            organizations.add(Organization(organization))
        } while (wantToAddAnother())
        return organizations.toMutableSet()
    }

    override fun getOrganization(): String {
        print("Enter organization: ")
        val input = readLine() ?: ""
        return presenter.checkOrganization(input)
    }

    override fun printWrongInput() {
        println("WRONG INPUT TRY AGAIN.")
    }

    override fun display(contacts: List<Contact>) {
        println("\nCONTACTS")
        contacts.forEachIndexed { i, contact ->
            println("${i + 1}. $contact\n")
        }
    }

    override fun delete() {
        print("Enter the number you want to delete: ")
        val index = readLine() ?: ""
        presenter.deleteContact(index)
    }

    override fun printNoContactsToEdit() {
        println("NO CONTACT TO EDIT.")
    }

    override fun printNoContactsToDelete() {
        println("NO CONTACT TO DELETE.")
    }

    override fun printNoContactsToDisplay() {
        println("NO CONTACT TO DISPLAY.")
    }

    override fun printNoContactsToSearch() {
        println("NO CONTACT TO SEARCH.")
    }

    override fun printNotFound() {
        println("NO CONTACT FOUND.")
    }

    override fun search() {
        print("Search: ")
        val input = readLine() ?: ""
        presenter.searchContact(input)
    }

    override fun exit() {
        println("EXITING...")
    }

    override fun edit() {
        print("Enter the number you want to edit: ")
        val index = readLine() ?: ""
        presenter.editContact(index)
    }

    override fun whatToEdit(contact: Contact) {
        println(
            "1. Name\t" + "2. Number\t" + "3. Email\t" + "4. Address\t" + "5. Organization\n"
        )
        print("Input: ")
        val input = readLine() ?: ""
        presenter.whatToEdit(contact, input)
    }

    override fun editNumbers(contact: Contact): Int {
        contact.numbers.forEachIndexed { index, number ->
            println("${index + 1}. ${number.label}: ${number.number}")
        }
        print("Enter what number you want to edit: ")
        val input = readLine() ?: ""
        return presenter.checkEditNumber(contact, input)
    }

    override fun editEmails(contact: Contact): Int {
        contact.emails.forEachIndexed { index, email ->
            println("${index + 1}. ${email.label}: ${email.email}")
        }
        print("Enter what email you want to edit: ")
        val input = readLine() ?: ""
        return presenter.checkEditEmail(contact, input)
    }

    override fun editAddresses(contact: Contact): Int {
        contact.addresses.forEachIndexed { index, address ->
            println("${index + 1}. ${address.label}: ${address.street}, ${address.city}, ${address.state}, ${address.pincode}")
        }
        print("Enter what address you want to edit: ")
        val input = readLine() ?: ""
        return presenter.checkEditAddress(contact, input)
    }

    override fun editOrganizations(contact: Contact): Int {
        contact.organizations.forEachIndexed { index, organization ->
            println("${index + 1}. $organization")
        }
        print("Enter what organization you want to edit: ")
        val input = readLine() ?: ""
        return presenter.checkEditOrganization(contact, input)
    }

    override fun editOptions(canEdit: Boolean, canAdd: Boolean, canDelete: Boolean): String {
        if (canEdit) print("E. Edit\t")
        if (canAdd) print("A. Add\t")
        if (canDelete) print("D. Delete\t")
        print("\nEnter: ")
        return readLine() ?: ""
    }

    override fun displayEditedContact(contact: Contact) {
        println("EDITED CONTACT.")
        println("$contact \n")
    }

    override fun wantToContinue(): Boolean {
        print("Do you want to continue? y/n: ")
        val input = readLine() ?: ""
        return presenter.checkYesNo(input)
    }
}