package com.ennovate.openidconnect

import com.ennovate.openidconnect.client.OpenIdConnectClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class OpenIdConnectClientTest {

    @Autowired
    private lateinit var mockMVC: MockMvc

    @Autowired
    private lateinit var openIdConnectClient: OpenIdConnectClient

    @Test
    fun `redirects to OP when trying to access protected resource`() {

        val result: MvcResult = mockMVC.perform(get("/user"))
                .andExpect(status().isFound)
                .andReturn()

        assertThat(result.response.redirectedUrl).startsWith(openIdConnectClient.authorizationEndpoint)

    }
}