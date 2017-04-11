package com.ennovate.openidconnect.web.controllers

import com.ennovate.openidconnect.client.model.OpenIdConnectAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WelcomeController {

    @GetMapping("/")
    fun user(authentication: Authentication,
             model: Model): String {
        val authenticatedUser = authentication as OpenIdConnectAuthenticationToken
        model.addAttribute("authenticatedUser", authenticatedUser)
        return "welcome"
    }
}