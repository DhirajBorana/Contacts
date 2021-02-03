package com.example.contacts

import com.example.contacts.model.*
import com.example.contacts.presenter.ContactPresenter
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ContactPresenterTest {
    private lateinit var view: Contract.View
    private lateinit var model: Contract.Model
    private lateinit var presenter: Contract.Presenter

    @Before
    fun setUp() {
        view = mock(Contract.View::class.java)
        model = mock(Contract.Model::class.java)
        presenter = ContactPresenter(view, model)
    }

    @Test
    fun testStart() {
        presenter.start()
        verify(view).start()
    }

    @Test
    fun testCheckUserInput() {
        presenter.userInput("1")
        verify(view).create()

        `when`(model.getSize()).thenReturn(0)
        presenter.userInput("2")
        verify(view).printNoContactsToEdit()

        `when`(model.getSize()).thenReturn(1)
        presenter.userInput("2")
        verify(view).edit()

        val contacts = listOf(Contact("tom", mutableSetOf(Number("work", 123456L))))
        `when`(model.getAllContacts()).thenReturn(contacts)

        `when`(model.getSize()).thenReturn(1)
        presenter.userInput("3")
        verify(view).delete()

        `when`(model.getSize()).thenReturn(0)
        presenter.userInput("3")
        verify(view).printNoContactsToDelete()

        `when`(model.getSize()).thenReturn(1)
        presenter.userInput("4")
        verify(view).search()

        `when`(model.getSize()).thenReturn(0)
        presenter.userInput("4")
        verify(view).printNoContactsToSearch()

        `when`(model.getSize()).thenReturn(1)
        presenter.userInput("5")
        verify(view, atLeastOnce()).display(contacts)

        `when`(model.getSize()).thenReturn(0)
        presenter.userInput("5")
        verify(view).printNoContactsToDisplay()

        presenter.userInput("6")
        verify(view).exit()

        presenter.userInput("")
        presenter.userInput("dgdf")
        verify(view, times(2)).printWrongInput()
        verify(view, atLeastOnce()).start()
    }

    @Test
    fun testCheckName() {
        `when`(view.getName()).thenReturn("John")
        Assert.assertEquals("John", presenter.checkName(""))
        Assert.assertEquals("Tom", presenter.checkName("Tom"))
    }

    @Test
    fun testCheckLabel() {
        `when`(view.getLabel()).thenReturn("Work")
        Assert.assertEquals("Work", presenter.checkLabel(""))
        Assert.assertEquals("Home", presenter.checkLabel("Home"))
    }

    @Test
    fun testCheckNumber() {
        `when`(view.getNumber()).thenReturn(123)
        Assert.assertEquals(123L, presenter.checkNumber("123e"))
        Assert.assertEquals(456789L, presenter.checkNumber("456789"))
    }

    @Test
    fun testCheckYesNo() {
        `when`(view.wantToAddAnother()).thenReturn(true)
        Assert.assertEquals(true, presenter.checkYesNo("sd"))
        Assert.assertEquals(true, presenter.checkYesNo("y"))
        Assert.assertEquals(true, presenter.checkYesNo("Y"))
        Assert.assertEquals(false, presenter.checkYesNo("n"))
        Assert.assertEquals(false, presenter.checkYesNo("N"))
    }

    @Test
    fun testCheckEmail() {
        `when`(view.getEmail()).thenReturn("example@email.com")
        Assert.assertEquals("example@email.com", presenter.checkEmail(""))
        Assert.assertEquals("example@email.com", presenter.checkEmail("example@email.com"))
    }

    @Test
    fun testCheckStreet() {
        `when`(view.getStreet()).thenReturn("street")
        Assert.assertEquals("street", presenter.checkStreet(""))
        Assert.assertEquals("street", presenter.checkStreet("street"))
    }

    @Test
    fun testCheckCity() {
        `when`(view.getCity()).thenReturn("city")
        Assert.assertEquals("city", presenter.checkCity(""))
        Assert.assertEquals("city", presenter.checkCity("city"))
    }

    @Test
    fun testCheckState() {
        `when`(view.getState()).thenReturn("state")
        Assert.assertEquals("state", presenter.checkState(""))
        Assert.assertEquals("state", presenter.checkState("state"))
    }

    @Test
    fun testCheckPincode() {
        `when`(view.getPincode()).thenReturn(100001)
        Assert.assertEquals(100001L, presenter.checkPincode("100qwq"))
        Assert.assertEquals(112001L, presenter.checkPincode("112001"))
    }

    @Test
    fun testCheckOrganization() {
        `when`(view.getOrganization()).thenReturn("organization")
        Assert.assertEquals("organization", presenter.checkOrganization(""))
        Assert.assertEquals("organization", presenter.checkOrganization("organization"))
    }

    @Test
    fun testAddContact() {
        val contact = Contact("tom", mutableSetOf(Number("work", 123456L)))
        presenter.addContact(contact)
        verify(model).addContact(contact)
    }

    @Test
    fun testDeleteContact() {
        `when`(model.getSize()).thenReturn(2)
        presenter.deleteContact("1")
        verify(model).getSize()
        verify(model).deleteContact(0)

        presenter.deleteContact("4")
        verify(view).printWrongInput()
        verify(view).delete()
    }

    @Test
    fun testSearchContact() {
        `when`(model.getAllContacts()).thenReturn(
            listOf(
                Contact(
                    "tom",
                    mutableSetOf(Number("work", 123456L))
                )
            )
        )
        presenter.searchContact("john")
        verify(model).getAllContacts()
        verify(view).printNotFound()

        presenter.searchContact("tom")
        verify(view).display(listOf(Contact("tom", mutableSetOf(Number("work", 123456L)))))
    }

    @Test
    fun testEditContact() {
        `when`(model.getSize()).thenReturn(2)
        presenter.editContact("1")
        verify(model).getSize()
        verify(model).getContact(0)

        presenter.editContact("4")
        verify(view).printWrongInput()
        verify(view).edit()
    }

    @Test
    fun testWhatToEdit() {
        val contact = Contact(
            "Tommy",
            mutableSetOf(Number("work", 123456L), Number("home", 9876543L)),
            mutableSetOf(Email("work", "tom@work")),
            mutableSetOf(Address("work", "street", "city", "state", 1001L)),
            mutableSetOf(Organization("Organization"))
        )

        presenter.whatToEdit(contact, "")
        verify(view).printWrongInput()
        verify(view).whatToEdit(contact)

        `when`(view.getName()).thenReturn("Tom")
        `when`(view.wantToContinue()).thenReturn(true)
        presenter.whatToEdit(contact, "1")
        verify(view).displayEditedContact(contact)
        verify(view).wantToContinue()
        verify(view, atLeastOnce()).whatToEdit(contact)

        `when`(view.editOptions()).thenReturn("E")
        presenter.whatToEdit(contact, "2")
        verify(view, atLeastOnce()).editNumbers(contact)

        `when`(view.editOptions()).thenReturn("D")
        presenter.whatToEdit(contact, "2")

        `when`(view.editOptions()).thenReturn("A")
        `when`(view.getLabel()).thenReturn("Custom")
        presenter.whatToEdit(contact, "2")

        `when`(view.editOptions()).thenReturn("", "E")
        presenter.whatToEdit(contact, "2")

        `when`(view.editOptions()).thenReturn("E")
        `when`(view.getEmail()).thenReturn("example@email")
        presenter.whatToEdit(contact, "3")
        verify(view, atLeastOnce()).editEmails(contact)

        `when`(view.editOptions()).thenReturn("D")
        presenter.whatToEdit(contact, "3")

        `when`(view.editOptions()).thenReturn("A")
        `when`(view.getLabel()).thenReturn("Custom")
        presenter.whatToEdit(contact, "3")

        `when`(view.editOptions()).thenReturn("", "E")
        presenter.whatToEdit(contact, "3")

        `when`(view.editOptions()).thenReturn("E")
        `when`(view.getStreet()).thenReturn("street")
        `when`(view.getCity()).thenReturn("city")
        `when`(view.getState()).thenReturn("state")
        `when`(view.getPincode()).thenReturn(10012)
        presenter.whatToEdit(contact, "4")
        verify(view, atLeastOnce()).editAddresses(contact)

        `when`(view.editOptions()).thenReturn("D")
        presenter.whatToEdit(contact, "4")

        `when`(view.editOptions()).thenReturn("A")
        `when`(view.getLabel()).thenReturn("Custom")
        presenter.whatToEdit(contact, "4")

        `when`(view.editOptions()).thenReturn("", "E")
        presenter.whatToEdit(contact, "4")

        `when`(view.editOptions()).thenReturn("E")
        `when`(view.getOrganization()).thenReturn("organization")
        presenter.whatToEdit(contact, "5")
        verify(view, atLeastOnce()).editOrganizations(contact)

        `when`(view.editOptions()).thenReturn("D")
        presenter.whatToEdit(contact, "5")

        `when`(view.editOptions()).thenReturn("A")
        presenter.whatToEdit(contact, "5")

        `when`(view.editOptions()).thenReturn("", "E")
        presenter.whatToEdit(contact, "5")
    }

    @Test
    fun testCheckEditNumber() {
        val contact = Contact(
            "Tommy",
            mutableSetOf(Number("work", 123456L), Number("home", 9876543L)),
            mutableSetOf(Email("work", "tom@work")),
            mutableSetOf(Address("work", "street", "city", "state", 1001L)),
            mutableSetOf(Organization("Organization"))
        )

        presenter.checkEditNumber(contact, "1")

        presenter.checkEditNumber(contact, "")
        verify(view).editNumbers(contact)
    }

    @Test
    fun testCheckEditEmail() {
        val contact = Contact(
            "Tommy",
            mutableSetOf(Number("work", 123456L), Number("home", 9876543L)),
            mutableSetOf(Email("work", "tom@work")),
            mutableSetOf(Address("work", "street", "city", "state", 1001L)),
            mutableSetOf(Organization("Organization"))
        )

        presenter.checkEditEmail(contact, "1")

        presenter.checkEditEmail(contact, "")
        verify(view).editEmails(contact)
    }

    @Test
    fun testCheckEditAddress() {
        val contact = Contact(
            "Tommy",
            mutableSetOf(Number("work", 123456L), Number("home", 9876543L)),
            mutableSetOf(Email("work", "tom@work")),
            mutableSetOf(Address("work", "street", "city", "state", 1001L)),
            mutableSetOf(Organization("Organization"))
        )

        presenter.checkEditAddress(contact, "1")

        presenter.checkEditAddress(contact, "")
        verify(view).editAddresses(contact)
    }

    @Test
    fun testCheckEditOrganization() {
        val contact = Contact(
            "Tommy",
            mutableSetOf(Number("work", 123456L), Number("home", 9876543L)),
            mutableSetOf(Email("work", "tom@work")),
            mutableSetOf(Address("work", "street", "city", "state", 1001L)),
            mutableSetOf(Organization("Organization"))
        )

        presenter.checkEditOrganization(contact, "1")

        presenter.checkEditOrganization(contact, "")
        verify(view).editOrganizations(contact)
    }
}