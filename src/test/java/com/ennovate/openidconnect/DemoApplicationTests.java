package com.ennovate.openidconnect;

import com.ennovate.openidconnect.client.OpenIdConnectClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    private OpenIdConnectClient openIdConnectClient;

    @Test
    public void contextLoads() {
    }

    @Test
    public void openIdConnectClientLoads() throws Exception {
        assertThat(openIdConnectClient)
                .hasFieldOrPropertyWithValue("clientId", "492969389947-p1j3ng540j33ikaqbl3j0odui7fgok86.apps.googleusercontent.com")
                .hasFieldOrPropertyWithValue("clientSecret", "VGIdACVaTPzPUGFqeYfIGvGz")
                .hasFieldOrPropertyWithValue("redirectUri", "http://localhost:8080/login")
                .hasFieldOrPropertyWithValue("responseType", "code")
                .hasFieldOrPropertyWithValue("clientAuthenticationScheme", "form")
                .hasFieldOrPropertyWithValue("tokenEndpoint", "https://www.googleapis.com/oauth2/v4/token")
                .hasFieldOrPropertyWithValue("authorizationEndpoint", "https://accounts.google.com/o/oauth2/v2/auth");
    }
}
