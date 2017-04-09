package com.ennovate.openidconnect.exception

import org.springframework.security.core.AuthenticationException


class OpenIdConnectException(error: String?) : AuthenticationException(error) {
    var errorPayload: ErrorPayload? = null
}

