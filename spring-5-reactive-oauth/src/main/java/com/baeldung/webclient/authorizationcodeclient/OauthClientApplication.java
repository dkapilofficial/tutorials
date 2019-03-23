package com.baeldung.webclient.authorizationcodeclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * 
 * Note: This app is configured to use the authorization service and the resource service located in Baeldung/spring-security-oauth repo
 * 
 * As we usually do with other well-known auth providers (github/facebook/...) we have to log-in using user credentials (john/123) and client configurations handled by the auth server
 * 
 * @author rozagerardo
 *
 */
@PropertySource("classpath:webclient-auth-code-client-application.properties")
@SpringBootApplication
public class OauthClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthClientApplication.class, args);
    }

}
