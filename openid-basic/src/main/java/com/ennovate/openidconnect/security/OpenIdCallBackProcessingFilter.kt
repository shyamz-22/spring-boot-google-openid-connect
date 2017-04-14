package com.ennovate.openidconnect.security

import com.ennovate.openidconnect.client.model.BasicFlowAuthenticationToken
import com.ennovate.openidconnect.client.model.GrantTypeObject.AUTHORIZATION_CODE
import com.ennovate.openidconnect.exception.OpenIdConnectException
import com.ennovate.openidconnect.service.AccessTokenServices
import extractAuthCode
import getErrorPayload
import getNonce
import getStateFromSession
import getStateParameter
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class OpenIdCallBackProcessingFilter(defaultUrl: String,
                                     val accessTokenServices: AccessTokenServices) : AbstractAuthenticationProcessingFilter(defaultUrl) {


    override fun attemptAuthentication(request: HttpServletRequest,
                                       response: HttpServletResponse): Authentication {


        validateState(request.getStateParameter(), request.getStateFromSession())
        val authCode = request.extractAuthCode()

        if (authCode.isNotEmpty()) {
            val openIdConnectAccessToken
                    = accessTokenServices.getAccessToken(AUTHORIZATION_CODE, authCode)

            val basicFlowAuthenticationToken = BasicFlowAuthenticationToken(
                    accessToken = openIdConnectAccessToken,
                    nonce = request.getNonce())

            return this.authenticationManager.authenticate(basicFlowAuthenticationToken)

        } else {
            handleAuthorizationError(request)
        }

        throw OpenIdConnectException("unable to parse authentication request")
    }


    private fun handleAuthorizationError(request: HttpServletRequest) {
        if (request.getErrorPayload() != null) {
            throw OpenIdConnectException("Authorize endpoint exception")
                    .apply { errorPayload = request.getErrorPayload() }
        }

    }

    private fun validateState(actualState: String?, expectedState: String?) {
        if ((actualState != expectedState)) {
            throw OpenIdConnectException("stale state value login again $actualState")
        }
    }
}



