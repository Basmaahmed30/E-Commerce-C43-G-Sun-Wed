package com.route.domain.model.auth

data class AuthResponse(
	val message: String? = null,
	val authUser: AuthUser? = null,
	val token: String? = null
)

data class AuthUser(
	val role: String? = null,
	val name: String? = null,
	val email: String? = null
)

