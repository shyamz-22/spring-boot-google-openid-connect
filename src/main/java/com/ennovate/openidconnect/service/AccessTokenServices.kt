package com.ennovate.openidconnect.service

import com.ennovate.openidconnect.client.OpenIdConnectClient
import com.ennovate.openidconnect.client.model.GrantTypeObject
import com.ennovate.openidconnect.client.model.OpenIdConnectAccessToken
import com.ennovate.openidconnect.exception.ErrorPayload
import com.ennovate.openidconnect.exception.OpenIdConnectException
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException


@Service
class AccessTokenServices(val openIdConnectClient: OpenIdConnectClient) {

    companion object {
        private val log = LoggerFactory.getLogger(AccessTokenServices::class.java)
    }

    fun getAccessToken(grantType: GrantTypeObject, grantValue: String): OpenIdConnectAccessToken {

        val accessTokenRequestUri = UriComponentsBuilder.fromUriString(openIdConnectClient.tokenEndpoint)
                .build().toUriString()

        val request = HttpEntity(body(openIdConnectClient,
                grantName = grantType.grantName,
                grantValue = grantValue,
                grantType = grantType.grantType), headers())

        try {
            return RestTemplate().postForEntity(accessTokenRequestUri, request, OpenIdConnectAccessToken::class.java).body
        } catch (ex: HttpClientErrorException) {
            throw OpenIdConnectException(ex.statusCode.toString())
                    .apply { errorPayload = getErrorPayLoad(ex.responseBodyAsString) }
        }
    }

    fun getErrorPayLoad(responseBodyAsString: String): ErrorPayload? {
        try {
            val objectMapper = ObjectMapper()
            return objectMapper.readValue(responseBodyAsString, ErrorPayload::class.java)
        } catch (e: IOException) {
            log.info("unable to deserialize response :", responseBodyAsString)
        }
        return null
    }

    private fun body(openIdConnectClient: OpenIdConnectClient, grantName: String, grantValue: String, grantType: String): LinkedMultiValueMap<String, String> {
        val map = LinkedMultiValueMap<String, String>()

        map.add("client_id", openIdConnectClient.clientId)
        map.add("client_secret", openIdConnectClient.clientSecret)
        map.add("redirect_uri", openIdConnectClient.redirectUri)
        map.add("grant_type", grantType)
        map.add(grantName, grantValue)

        return map
    }

    private fun headers(): HttpHeaders {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        return headers
    }
}