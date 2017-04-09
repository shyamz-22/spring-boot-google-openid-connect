package com.ennovate.openidconnect.service

import addQueryParamIfPresent
import com.ennovate.openidconnect.client.OpenIdConnectClient
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder
import java.net.URLEncoder


@Service
class AuthenticationRequestBuilder(val openIdConnectClient: OpenIdConnectClient) {

    fun build(state: String, nonce: String): String? {

        val basicFlowRequestUri = (UriComponentsBuilder
                .fromUriString(openIdConnectClient.authorizationEndpoint)
                .queryParam("client_id", openIdConnectClient.clientId)
                .queryParam("redirect_uri", openIdConnectClient.redirectUri)
                .queryParam("scope", openIdConnectClient.scope)
                .queryParam("response_type", openIdConnectClient.responseType)
                .queryParam("access_type", "offline")
                .queryParam("state", state)
                .addQueryParamIfPresent("nonce", nonce))
                .addQueryParamIfPresent("prompt", openIdConnectClient.prompt).toUriString()

        return basicFlowRequestUri
    }
}