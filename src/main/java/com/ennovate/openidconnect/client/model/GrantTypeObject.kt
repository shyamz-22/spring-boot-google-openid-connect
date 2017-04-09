package com.ennovate.openidconnect.client.model


enum class GrantTypeObject(val grantType: String,
                           val grantName: String) {

    AUTHORIZATION_CODE("authorization_code", "code"),
    REFRESH_TOKEN("refresh_token", "refresh_token")

}