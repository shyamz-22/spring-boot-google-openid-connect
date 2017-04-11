package com.ennovate.openidconnect.verifier.jwks

import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.JWSObject
import com.nimbusds.jose.crypto.RSASSAVerifier
import com.nimbusds.jwt.JWTClaimsSet
import getClaims
import org.springframework.stereotype.Service
import java.security.InvalidKeyException
import java.security.interfaces.RSAPublicKey

@Service
class JWSignatureVerifier(val rsaPublicKeyService: RSAPublicKeyService) {

    fun checkSignature(token: String?): Pair<Boolean, JWTClaimsSet?> {

        if (isInvalid(token)) return Pair(false, null)

        val jwsObject = JWSObject.parse(token)
        val keyID = jwsObject.header.keyID

        val publicKey = rsaPublicKeyService.getPublicKey(keyID)
                ?: throw InvalidKeyException("Could not find public key for: " + keyID)

        try {
            val rsaSsaVerifier = RSASSAVerifier(publicKey as RSAPublicKey)

            return Pair(jwsObject.verify(rsaSsaVerifier), jwsObject.getClaims())
        } catch (e: JOSEException) {
            return Pair(false, null)
        }
    }

    private fun isInvalid(token: String?): Boolean {
        if (token == null || token.isEmpty()) {
            return true
        }
        return false
    }

}