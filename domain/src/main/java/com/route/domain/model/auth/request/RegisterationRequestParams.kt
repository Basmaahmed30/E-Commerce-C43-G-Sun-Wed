package com.route.domain.model.auth.request

data class RegistrationRequestParams(
	val password: String? = null,
	val phone: String? = null,
	val rePassword: String? = null,
	val name: String? = null,
	val email: String? = null
)

