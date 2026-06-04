package com.route.e_commercec43gsunwed.screens

/**
 *
 * 1- full name cannot be empty and doesn't exceed 30 char and it should be more than 3 chars
 * 2- email Address should not be empty and should be written in Email REGEX
 *                  kareem@route.com
 * 3- password that should be more than 6 chars or digits or both
 *
 * // TDD Test-Driven Development       1- Test Cases    2- implement the function
 * // Behaviour-Driven Development      1- Implement The function   2- test Cases
 */

fun validateFieldsForRegistration(
    name: String?,
    emailAddress: String?,
    password: String?
): Boolean {
    if (name?.isEmpty() == true || name?.isBlank() == true)
        return false

    if (emailAddress?.isEmpty() == true || emailAddress?.isBlank() == true)
        return false

    if (password?.isEmpty() == true || password?.isBlank() == true)
        return false
    if (name?.length !in 3..30)
        return false
    emailAddress?.isValidEmail()?.let {
        if (!it)
            return false
    }
    if ((password?.length ?: 0) < 6)
        return false
    return true
}

fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
    return this.matches(emailRegex)
}

/**
 * What to do Next :-
 *      Basics + OOP
 *      Design Patterns (Creational Design Patterns  )
 *      UI Architecture Patterns ( MVVM + MVI )
 *      Version Control Systems (VCS ) :- Git & Github
 *
 *      Algorithms + Data Structures
 *      SOLID Design Principles
 *
 *      XML (View-Based) :- Recycler View + 4 Android Main Components (Activity - Service - Broadcast Receiver - Content Provider )
 *                          View Binding + Data Binding + Navigation Component + Fragments
 *
 *       Compose :-         Compose States  + Recomposition + Navigation Component
 *      Unit Testing
 *      CI / CD
 *   (Continuous Integration  / Continuous Deployment )
 *
 *   Firebase  :- Cloud Messaging
 *
 *                Crashlytics
 *                Cloud Storage ()
 *                // Realtime database
 *
 *                Cloud Firestore
 *                Authentication
 *   Clean Architecture
 *   API Calls (Retrofit) + Interceptor
 *   Google Maps (Markers)
 *
 *
 *   Compose Multiplatform (Networking : Ktor )
 *               Android       Compose Multiplatform
 *               Scaffold ->     Scaffold
 *               Column   ->     Column
 *               Room     ->     Room
 *
 *
 *   Cart + Wishlist + Account  -> E-Commerce
 *
 *
 *
 *
 */
