package com.agvber.core.authentication

import com.agvber.core.authentication.model.Token

abstract class SocialLoginService {

    abstract suspend fun login(): Token
}