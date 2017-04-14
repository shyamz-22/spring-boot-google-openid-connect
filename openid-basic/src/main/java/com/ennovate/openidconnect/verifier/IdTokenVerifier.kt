package com.ennovate.openidconnect.verifier

import com.ennovate.openidconnect.client.OpenIdConnectClient
import com.ennovate.openidconnect.exception.OpenIdConnectException
import com.ennovate.openidconnect.verifier.jwks.JWSignatureVerifier
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.util.DateUtils
import org.springframework.stereotype.Service
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.text.ParseException
import java.time.Duration
import java.time.Instant
import java.util.*


@Service
class IdTokenVerifier(private val jwSignatureVerifier: JWSignatureVerifier,
                      private val openIdConnectClient: OpenIdConnectClient) {

    fun validateIdToken(idToken: String, nonce: String?): JWTClaimsSet {

        val jwtClaims = validateAndGet(idToken)

        validateAudience(jwtClaims)
        validateIssuer(jwtClaims)
        validateTokenExpiration(jwtClaims)
        validateTokenIssuedTime(jwtClaims)
        validateNonce(jwtClaims, nonce)

        return jwtClaims
    }

    private fun validateAndGet(idToken: String): JWTClaimsSet {
        val verificationResult: Pair<Boolean, JWTClaimsSet?>
        try {
            verificationResult = jwSignatureVerifier.checkSignature(idToken)
            if (!verificationResult.first) {
                throw OpenIdConnectException("Signature validation failed")
            }

        } catch (e: ParseException) {
            throw OpenIdConnectException("Invalid JWT token")
        } catch (e: InvalidKeySpecException) {
            throw OpenIdConnectException("Unsupported or invalid key algorithm")
        } catch (e: NoSuchAlgorithmException) {
            throw OpenIdConnectException("Unsupported or invalid key algorithm")
        }
        return verificationResult.second!!
    }

    private fun validateAudience(jwtClaimsSet: JWTClaimsSet) {
        if (jwtClaimsSet.audience[0] != openIdConnectClient.clientId) {
            throw OpenIdConnectException("token audience mismatch")
        }
    }

    private fun validateIssuer(jwtClaimsSet: JWTClaimsSet) {
        if (!openIdConnectClient.authorizationEndpoint!!.contains(jwtClaimsSet.issuer)) {
            throw OpenIdConnectException("token issuer mismatch")
        }
    }

    private fun validateTokenExpiration(jwtClaimsSet: JWTClaimsSet) {
        if (DateUtils.isAfter(Date(), jwtClaimsSet.expirationTime, 60)) {
            throw OpenIdConnectException("token expired")
        }
    }

    private fun validateTokenIssuedTime(jwtClaimsSet: JWTClaimsSet) {
        val duration = Duration.between(Instant.ofEpochMilli(jwtClaimsSet.issueTime.time), Instant.now())
        if (duration.abs().seconds > 300) {
            throw OpenIdConnectException("issued too far away from the current time")
        }
    }

    private fun validateNonce(jwtClaims: JWTClaimsSet, nonce: String?) {
        if(jwtClaims.getClaim("nonce") != nonce) {
            throw OpenIdConnectException(
                    "Mismatch in nonce value expected: '$nonce' actual: '${jwtClaims.getClaim("nonce")}'")
        }
    }

}
