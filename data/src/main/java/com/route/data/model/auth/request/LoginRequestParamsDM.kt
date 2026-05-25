package com.route.data.model.auth.request

import com.google.gson.annotations.SerializedName

data class LoginRequestParamsDM(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
