package com.ennovate.openidconnect.verifier.jwks

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import org.springframework.stereotype.Service
import java.security.PublicKey
import java.util.*


@Service
class RSAPublicKeyService(val fetchKeys: FetchKeys) {
    private val identityKitPublicKeyMap = HashMap<String, PublicKey>()

    fun getPublicKey(keyId: String): PublicKey? {
        if (!identityKitPublicKeyMap.containsKey(keyId)) {
            populateCache(fetchKeys.getPublicKeys())
        }
        return identityKitPublicKeyMap[keyId]
    }

    private fun populateCache(publicKeys: JWKSet) {
        publicKeys.keys.forEach { jwk ->
            run {
                val rsaKey = jwk as RSAKey
                identityKitPublicKeyMap.put(rsaKey.keyID, rsaKey.toPublicKey())
            }
        }
    }
}