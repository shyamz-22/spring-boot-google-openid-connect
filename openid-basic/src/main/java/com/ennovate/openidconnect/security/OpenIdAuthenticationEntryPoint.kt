package com.ennovate.openidconnect.security

import com.ennovate.openidconnect.service.AuthenticationRequestBuilder
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.math.BigInteger
import java.security.SecureRandom
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class OpenIdAuthenticationEntryPoint(val openIdAuthenticationRequestBuilder: AuthenticationRequestBuilder) : AuthenticationEntryPoint {

    companion object {
        const val STATE_ATTR = "state"
        const val NONCE_ATTR = "nonce"
    }

    override fun commence(request: HttpServletRequest,
                          response: HttpServletResponse,
                          authException: AuthenticationException?) {

        val nonce = random()
        val state = random()

        request.session.setAttribute(STATE_ATTR, state)
        request.session.setAttribute(NONCE_ATTR, nonce)

        response.sendRedirect(openIdAuthenticationRequestBuilder.build(state, nonce))

    }


    private fun random() = BigInteger(50, SecureRandom()).toString(16)
}
