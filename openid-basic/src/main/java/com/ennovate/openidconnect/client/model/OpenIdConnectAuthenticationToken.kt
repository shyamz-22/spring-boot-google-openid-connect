package com.ennovate.openidconnect.client.model

import org.springframework.security.authentication.AbstractAuthenticationToken


class OpenIdConnectAuthenticationToken(val accessToken: OpenIdConnectAccessToken,
                                       var userId: String? = null,
                                       var nameOfUser: String? = null,
                                       var email: String? = null,
                                       var picture: String? = null,
                                       var locale: String? = null,
                                       var nonce: String? = null
) : AbstractAuthenticationToken(null) {

    constructor(accessToken: OpenIdConnectAccessToken, nonce: String?) : this(accessToken,
            null,
            null,
            null,
            null,
            null,
            nonce)

    override fun getCredentials(): Any {
        return accessToken.accessToken
    }

    override fun getPrincipal(): Any? {
        return nameOfUser
    }

    override fun getName(): String? {
        return nameOfUser
    }
}
