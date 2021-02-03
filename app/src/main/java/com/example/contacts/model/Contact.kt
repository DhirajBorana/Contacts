package com.example.contacts.model

import java.lang.StringBuilder

data class Contact(
    var name: String,
    var numbers: MutableSet<Number>,
    var emails: MutableSet<Email> = mutableSetOf(),
    var addresses: MutableSet<Address> = mutableSetOf(),
    var organizations: MutableSet<Organization> = mutableSetOf()
) : Comparable<Contact> {
    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("Name: $name\n")
        stringBuilder.append("Number:\n")
        numbers.forEach { stringBuilder.append(it) }
        if (emails.isNotEmpty()) {
            stringBuilder.append("Email:\n")
            emails.forEach { stringBuilder.append(it) }
        }
        if (addresses.isNotEmpty()) {
            stringBuilder.append("Address:\n")
            addresses.forEach { stringBuilder.append(it) }
        }
        if (organizations.isNotEmpty()) {
            stringBuilder.append("Organization:\n")
            organizations.forEach { stringBuilder.append("$it\n") }
        }
        return stringBuilder.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (other is Contact) {
            if (other.name == this.name && other.numbers == this.numbers) return true
        }
        return false
    }

    override fun compareTo(other: Contact): Int {
        return this.name.compareTo(other.name)
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + numbers.hashCode()
        result = 31 * result + emails.hashCode()
        result = 31 * result + addresses.hashCode()
        result = 31 * result + organizations.hashCode()
        return result
    }
}

data class Number(
    var label: String,
    var number: Long
) {
    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("$label - $number\n")
        return stringBuilder.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (other is Number) {
            if (other.label == this.label && other.number == this.number) return true
        }
        return false
    }

    override fun hashCode(): Int {
        var result = label.hashCode()
        result = 31 * result + number.hashCode()
        return result
    }
}

data class Email(
    var label: String,
    var email: String
) {
    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("$label - $email\n")
        return stringBuilder.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (other is Email) {
            if (other.label == this.label && other.email == this.email) return true
        }
        return false
    }

    override fun hashCode(): Int {
        var result = label.hashCode()
        result = 31 * result + email.hashCode()
        return result
    }
}

data class Address(
    var label: String,
    var street: String,
    var city: String,
    var state: String,
    var pincode: Long
) {
    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("$label - $street, $city, $state, $pincode\n")
        return stringBuilder.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (other is Address) {
            if (other.label == this.label && other.street == this.street && other.city == this.city && other.state == this.state && other.pincode == this.pincode) return true
        }
        return false
    }

    override fun hashCode(): Int {
        var result = label.hashCode()
        result = 31 * result + street.hashCode()
        result = 31 * result + city.hashCode()
        result = 31 * result + state.hashCode()
        result = 31 * result + pincode.hashCode()
        return result
    }
}

data class Organization(
    var name: String
) {
    override fun toString(): String {
        return name
    }

    override fun equals(other: Any?): Boolean {
        if (other is Organization) {
            if (other.name == this.name) return true
        }
        return false
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}