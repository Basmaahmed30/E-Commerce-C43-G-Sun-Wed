package com.route.e_commercec43gsunwed.screens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidateFieldsForRegistrationTest {


    // Test Case naming Convention : 1- Target Unit       2-  Context      3- Expected Result
    @Test
    fun `validateFieldsForRegistration() with empty fields (fullName,emailAddress, password) returns false`() {
        // Test case implementation : (Triple - A Rule )
        // 1- Arrange
        val name = ""
        val email = ""
        val password = ""
        // 2- Act
        val actual = validateFieldsForRegistration(name, email, password)
        // 3- Assert
//        assertFalse(actual)
        assertEquals(false, actual)
    }

    @Test
    fun `validateFieldsForRegistration() with short password returns false`() {
        // Test case implementation : (Triple - A Rule )
        // 1- Arrange
        val name = "Kareem"
        val email = "kareem@route.com"
        val password = "123"
        // 2- Act
        val actual = validateFieldsForRegistration(name, email, password)
        // 3- Assert
//        assertFalse(actual)
        assertEquals(false, actual)
    }

    @Test
    fun `validateFieldsForRegistration() with valid fields returns true`() {
        // Test case implementation : (Triple - A Rule )
        // 1- Arrange
        val name = "Kareem"
        val email = "kareem@route.com"
        val password = "123456"
        // 2- Act
        val actual = validateFieldsForRegistration(name, email, password)
        // 3- Assert
//        assertFalse(actual)
        assertEquals(true, actual)
    }

    @Test
    fun `validateFieldsForRegistration() with short name returns false`() {
        // Test case implementation : (Triple - A Rule )
        // 1- Arrange
        val name = "Aa" // length
        val email = "kareem@route.com"
        val password = "123456"
        // 2- Act
        val actual = validateFieldsForRegistration(name, email, password)
        // 3- Assert
//        assertFalse(actual)
        assertEquals(false, actual)
    }

}