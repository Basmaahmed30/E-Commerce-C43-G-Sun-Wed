package com.route.data.model.auth

import com.google.gson.annotations.SerializedName

data class AuthResponseDM(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val authUserDM: AuthUserDM? = null,

	@field:SerializedName("token")
	val token: String? = null
)

data class AuthUserDM(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
