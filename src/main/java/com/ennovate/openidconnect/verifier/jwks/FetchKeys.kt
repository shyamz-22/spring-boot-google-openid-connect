package com.ennovate.openidconnect.verifier.jwks

import com.ennovate.openidconnect.exception.OpenIdConnectException
import com.nimbusds.jose.jwk.JWKSet
import java.net.URL


class FetchKeys(val jwksUrl: String) {

    fun getPublicKeys(): JWKSet {
        try {
            val connectTimeout = 100
            val readTimeout = 100
            val sizeLimit = 10000
            return JWKSet.load(URL(jwksUrl), connectTimeout, readTimeout, sizeLimit)
        } catch (e: Exception) {
            throw OpenIdConnectException("unable to fetch keys to validate tokens")
        }
    }

}