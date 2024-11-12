package com.validation.email.service;

import com.validation.email.exception.DataNotFoundException;
import com.validation.email.exception.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class EmailValidationServiceImpl implements  EmailValidationService {
    @Autowired
    private final RestTemplate restTemplate;

    @Value("${this.email.apiUrl}")
    private String baseUrl;

    @Value("${this.email.apiKey}")
    private String key;


    public EmailValidationServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<Resource> emailValidate(final String email) {
        ResponseEntity<Resource> emailResponse = null;
        try {
            log.info("Invoking email validation api");
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .queryParam("api_key", key)
                    .queryParam("email", email).toUriString();
            emailResponse = restTemplate.exchange(url, HttpMethod.GET, null, Resource.class);
            if (emailResponse.getBody() != null) {
                return emailResponse;
            } else {
                throw new DataNotFoundException("Data not found");
            }
        } catch (HttpClientErrorException e) {
            if (e.getMessage().contains("Unauthorized")) {
                throw new UnAuthorizedException(e.getMessage());
            }
            log.error("Http client error occurred while validating email {}", email);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while validating email {}", email);
            throw e;
        }
    }
}
