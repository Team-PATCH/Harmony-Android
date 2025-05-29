package com.teampatch.core.authentication

import com.teampatch.core.authentication.model.Token

abstract class SocialLoginService {

    abstract suspend fun login(): Token
}