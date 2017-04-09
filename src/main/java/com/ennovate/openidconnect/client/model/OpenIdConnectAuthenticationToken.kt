package com.ennovate.openidconnect.client.model

import org.springframework.security.authentication.AbstractAuthenticationToken


class OpenIdConnectAuthenticationToken(val accessToken: OpenIdConnectAccessToken,
                                       var userId: String?,
                                       var nameOfUser: String?,
                                       var email: String?,
                                       var picture: String?,
                                       var locale: String?
                                       ) : AbstractAuthenticationToken(null) {

    constructor(accessToken: OpenIdConnectAccessToken) : this(accessToken, null, null, null, null, null)

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
