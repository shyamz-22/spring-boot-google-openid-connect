package com.ennovate.openidconnect.client

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "openidconnect.client")
class OpenIdConnectClient(var clientId: String? = null,
                          var clientSecret: String? = null,
                          var redirectUri: String? = null,

                          var responseType: String? = null,
                          var scope: String? = null,
                          var clientAuthenticationScheme: String? = null,
                          var prompt: String? = null,
                          var grantType: String? = null,

                          var tokenEndpoint: String? = null,
                          var authorizationEndpoint: String? = null,

                          var jwksUri: String? = null,
                          var revocationEndpoint: String? = null,
                          var userinfoEndpoint: String? = null)