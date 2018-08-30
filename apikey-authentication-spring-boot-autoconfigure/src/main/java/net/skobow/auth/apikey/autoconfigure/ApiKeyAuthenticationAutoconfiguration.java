/*
 * MIT License
 *
 * Copyright (c) 2018 Sven Kobow
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package net.skobow.auth.apikey.autoconfigure;

import net.skobow.auth.apikey.ApiKeyAuthenticationService;
import net.skobow.auth.apikey.ApiKeyVerificationHandler;
import net.skobow.auth.apikey.RandomApiKeyVerificationHandler;
import net.skobow.auth.apikey.RequestApiKeyExtractor;
import net.skobow.auth.apikey.StaticApiKeyVerificationHandler;
import net.skobow.auth.apikey.webmvc.ApiKeyAuthenticationInterceptorProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class ApiKeyAuthenticationAutoconfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ApiKeyAuthenticationService apiKeyAuthenticationService(final RequestApiKeyExtractor requestApiKeyExtractor,
                                                                   final ApiKeyVerificationHandler apiKeyVerificationHandler) {
        return new ApiKeyAuthenticationService(requestApiKeyExtractor, apiKeyVerificationHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestApiKeyExtractor defaultApiKeyProvider() {
        return new RequestApiKeyExtractor() {
        };
    }

    @Bean
    @ConditionalOnProperty(name = "web.authentication.apikey")
    public ApiKeyVerificationHandler apiKeyVerificationHandler(@Value("${web.authentication.apikey}") final String apiKey) {
        return new StaticApiKeyVerificationHandler(apiKey);
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiKeyVerificationHandler randomApiKeyVerificationHandler() {
        return new RandomApiKeyVerificationHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiKeyAuthenticationInterceptorProperties apiKeyAuthenticationInterceptorProperties(
            @Qualifier("apiKeyAuthenticationIncludePatterns") final List<String> includePatterns,
            @Qualifier("apiKeyAuthenticationExcludePatterns") final List<String> excludePatterns) {
        return new ApiKeyAuthenticationInterceptorProperties(includePatterns, excludePatterns);
    }

    @Bean
    @ConditionalOnMissingBean(name = "apiKeyAuthenticationIncludePatterns")
    public List<String> apiKeyAuthenticationIncludePatterns() {
        return Collections.singletonList("/**");
    }

    @Bean
    @ConditionalOnMissingBean(name = "apiKeyAuthenticationExcludePatterns")
    public List<String> apiKeyAuthenticationExcludePatterns() {
        return Collections.singletonList("/error");
    }
}
