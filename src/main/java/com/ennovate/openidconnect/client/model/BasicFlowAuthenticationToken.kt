package com.ennovate.openidconnect.client.model

import org.springframework.security.authentication.AbstractAuthenticationToken

class BasicFlowAuthenticationToken(val accessToken: OpenIdConnectAccessToken,
                                   val nonce: String?) : AbstractAuthenticationToken(null) {

    override fun getCredentials(): Any {
        return accessToken
    }

    override fun getPrincipal(): Any {
        return accessToken.idToken
    }

}