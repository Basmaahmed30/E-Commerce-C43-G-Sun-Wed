package com.route.data.mapper.auth

import com.route.data.model.auth.AuthResponseDM
import com.route.data.model.auth.AuthUserDM
import com.route.data.model.auth.request.LoginRequestParamsDM
import com.route.data.model.auth.request.RegistrationRequestParamsDM
import com.route.domain.model.auth.AuthResponse
import com.route.domain.model.auth.AuthUser
import com.route.domain.model.auth.request.LoginRequestParams
import com.route.domain.model.auth.request.RegistrationRequestParams

fun LoginRequestParams.toDataModel(): LoginRequestParamsDM {
    return LoginRequestParamsDM(
        password = password, email = email
    )
}

fun RegistrationRequestParams.toDataModel(): RegistrationRequestParamsDM {
    return RegistrationRequestParamsDM(
        password = password, email = email,
        phone = phone, rePassword = rePassword,
        name = name
    )
}

fun AuthResponseDM.toDomainEntity(): AuthResponse {
    return AuthResponse(message = message, authUser = authUserDM?.toDomainEntity(), token = token)
}

fun AuthUserDM.toDomainEntity(): AuthUser {
    return AuthUser(role = role, name = name, email = email)
}
