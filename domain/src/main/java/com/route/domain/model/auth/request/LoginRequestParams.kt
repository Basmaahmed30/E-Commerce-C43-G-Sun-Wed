package com.route.domain.model.auth.request

data class LoginRequestParams(
	val password: String? = null,
	val email: String? = null
)

