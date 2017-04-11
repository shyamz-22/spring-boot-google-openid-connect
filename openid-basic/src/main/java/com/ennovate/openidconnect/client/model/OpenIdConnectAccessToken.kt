package com.ennovate.openidconnect.client.model

import com.fasterxml.jackson.annotation.JsonProperty

class OpenIdConnectAccessToken(
        @JsonProperty("access_token") val accessToken: String,
        @JsonProperty("refresh_token") val refreshToken: String?,
        @JsonProperty("id_token") val idToken: String,
        @JsonProperty("expires_in") val expiresIn: Int,
        @JsonProperty("token_type") val tokenType: String?)
