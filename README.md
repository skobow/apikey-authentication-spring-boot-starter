# API Key Authentication Spring Boot Starter

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.skobow/apikey-authentication-spring-boot-starter/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/net.skobow/apikey-authentication-spring-boot-starter)

## Description

This Spring Boot starter provides easy to use and though configurable API Key authentication for your Spring Boot project. 

## Installation

To install simple add the dependency to you project build system, e.g. Gradle or Maven.

**Gradle**

    implementation 'net.skobow:apikey-authentication-spring-boot-starter:0.6.0'
    
 **Maven**
 
    <dependency>
      <groupId>net.skobow</groupId>
      <artifactId>apikey-authentication-spring-boot-starter</artifactId>
      <version>0.5.1</version>
    </dependency>
    
 ## Usage
 
 Just add the `@EnableApiKeyAuthentication` annotation to you Spring Boot Application class and provide `web.authentication.apikey` property to enable static API key authentication. This will add an Spring `HandlerInterceptor` that will check the `X-Api-Key` request header for the configured static API key.
 If no or not the correct key is provided the request will fail and send 401 as return code.
 
 If no value for an API key is provided a random key is generated and logged to command line. This configuration is only suitable for testing scenarios as it does not provide security as the API key may appear in logs and is therefore considered as insecure!
 
 ## Customization
 
 ### Adding custom includes or excludes
 
 If you want to configure paths to be included or excluded you can provide lists with patterns in you Spring configuration.
 
    @Bean("apiKeyAuthenticationIncludePatterns") 
    public List<String> apiKeyAuthenticationIncludePatterns() {
        ...
    }
    
 or 
 
    @Bean("apiKeyAuthenticationExcludePatterns")
    public List<String> apiKeyAuthenticationExcludePatterns) {
        ...
    }
    
 Normally you may want to exclude at least your `/error` endpoint otherwise no errors will be returned to the user.
 
 ### Using custom HTTP header fields
 
 If you want to use a different HTTP header field you can simply provide your own implementation of the `RequestApiKeyExtractor` interface as a Spring bean.
 
 ### Customizing API key verification
 
 By default static API key verification for all requests is used. If your needs demand for a different verification schema you can provide your own implementation of the `ApiKeyVerificationHandler` interface as a Spring bean. This instance will be called during the request and lets you do your specific API key verification.
 
