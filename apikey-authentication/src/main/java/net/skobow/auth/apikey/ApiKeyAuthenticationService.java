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

package net.skobow.auth.apikey;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ApiKeyAuthenticationService {

    private final RequestApiKeyExtractor requestApiKeyExtractor;
    private final ApiKeyVerificationHandler apiKeyVerificationHandler;

    public ApiKeyAuthenticationService(final RequestApiKeyExtractor requestApiKeyExtractor,
                                       final ApiKeyVerificationHandler apiKeyVerificationHandler) {
        this.requestApiKeyExtractor = requestApiKeyExtractor;
        this.apiKeyVerificationHandler = apiKeyVerificationHandler;
    }

    public void authenticate(final HttpServletRequest request) throws ApiKeyAuthenticationException {
        final String apiKey = requestApiKeyExtractor.getApiKey(request)
                .orElseThrow(() -> new ApiKeyAuthenticationException("Api key not found"));

        try {
            apiKeyVerificationHandler.verify(apiKey);
        } catch (final ApiKeyVerificationException e) {
            throw new ApiKeyAuthenticationException(e.getMessage(), e);
        }
    }
}
