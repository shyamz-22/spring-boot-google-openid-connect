package com.ennovate.openidconnect.security

import com.ennovate.openidconnect.client.model.BasicFlowAuthenticationToken
import com.ennovate.openidconnect.client.model.OpenIdConnectAccessToken
import com.ennovate.openidconnect.client.model.OpenIdConnectAuthenticationToken
import com.ennovate.openidconnect.verifier.IdTokenVerifier
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service


@Service
class OpenIdAuthenticationManager(val idTokenVerifier: IdTokenVerifier) : AuthenticationManager {

    override fun authenticate(authentication: Authentication): Authentication {

        val openIdAuthenticationToken: OpenIdConnectAuthenticationToken?

        if (!authentication.isAuthenticated) {
            val pendingToken = authentication as BasicFlowAuthenticationToken
            openIdAuthenticationToken = OpenIdConnectAuthenticationToken(pendingToken.accessToken)

        } else {
            openIdAuthenticationToken = authentication as OpenIdConnectAuthenticationToken
        }

        val validatedIdTokenClaims = idTokenVerifier.validateIdToken(openIdAuthenticationToken.accessToken.idToken)

        openIdAuthenticationToken.apply {
            userId = validatedIdTokenClaims.getStringClaim("sub")
            nameOfUser = validatedIdTokenClaims.getStringClaim("name")
            email = validatedIdTokenClaims.getStringClaim("email")
            picture = validatedIdTokenClaims.getStringClaim("picture")
            locale = validatedIdTokenClaims.getStringClaim("locale")
        }


        return openIdAuthenticationToken.apply {
            isAuthenticated = true
        }

    }
}