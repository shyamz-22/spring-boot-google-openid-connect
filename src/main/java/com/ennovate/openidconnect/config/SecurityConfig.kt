package com.ennovate.openidconnect.config

import com.ennovate.openidconnect.client.OpenIdConnectClient
import com.ennovate.openidconnect.security.OpenIdAuthenticationEntryPoint
import com.ennovate.openidconnect.security.OpenIdAuthenticationManager
import com.ennovate.openidconnect.security.OpenIdCallBackProcessingFilter
import com.ennovate.openidconnect.service.AccessTokenServices
import com.ennovate.openidconnect.verifier.jwks.FetchKeys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var openIdAuthenticationEntryPoint: OpenIdAuthenticationEntryPoint

    @Autowired
    private lateinit var accessTokenServices: AccessTokenServices

    @Autowired
    private lateinit var openIdAuthenticationManager: OpenIdAuthenticationManager


    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .addFilterBefore(openIdCallBackProcessingFilter(), UsernamePasswordAuthenticationFilter::class.java)
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(openIdAuthenticationEntryPoint)
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
    }

    @Bean
    fun openIdCallBackProcessingFilter(): AbstractAuthenticationProcessingFilter {
        val openIdCallBackProcessingFilter = OpenIdCallBackProcessingFilter("/login", accessTokenServices)
        openIdCallBackProcessingFilter.setAuthenticationManager(openIdAuthenticationManager)

        return openIdCallBackProcessingFilter
    }

    @Bean
    fun fetchKeys(openIdConnectClient: OpenIdConnectClient) = FetchKeys(openIdConnectClient.jwksUri!!)
}