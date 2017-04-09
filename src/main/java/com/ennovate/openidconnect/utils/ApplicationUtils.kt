import com.ennovate.openidconnect.security.OpenIdAuthenticationEntryPoint
import com.ennovate.openidconnect.exception.ErrorPayload
import com.nimbusds.jose.JWSObject
import com.nimbusds.jwt.JWTClaimsSet
import org.springframework.web.util.UriComponentsBuilder
import javax.servlet.http.HttpServletRequest

fun HttpServletRequest.getStateFromSession(): String? {
    return session.getAttribute(OpenIdAuthenticationEntryPoint.STATE_ATTR)?.toString()
}

fun HttpServletRequest.getStateParameter(): String? {
    return getParameter(OpenIdAuthenticationEntryPoint.STATE_ATTR)?.toString()
}

fun HttpServletRequest.getAuthCodeParameter(): String? {
    return getParameter("code")?.toString()
}

fun HttpServletRequest.getErrorPayload(): ErrorPayload? {
    val error = getParameter("error")
    val errorDescription = getParameter("error_description")

    if (error == null) return null
    return (ErrorPayload(error, errorDescription))
}


fun HttpServletRequest.getNonce(): String? {
    return session.getAttribute(OpenIdAuthenticationEntryPoint.NONCE_ATTR)?.toString()
}

fun HttpServletRequest.extractAuthCode() = getAuthCodeParameter() ?: ""


fun UriComponentsBuilder.addQueryParamIfPresent(name: String, value: String?): UriComponentsBuilder {
    if (value != null) {
        queryParam(name, value)
    }

    return this
}

fun JWSObject.getClaims() : JWTClaimsSet
        = JWTClaimsSet.parse(payload.toJSONObject().toJSONString())

