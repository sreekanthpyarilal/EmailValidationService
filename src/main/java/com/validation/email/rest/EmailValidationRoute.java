package com.validation.email.rest;

import com.validation.email.exception.DataNotFoundException;
import com.validation.email.exception.UnAuthorizedException;
import com.validation.email.service.EmailValidationService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@RestController
public class EmailValidationRoute {

    @Autowired
    private EmailValidationService emailValidationService;

    @GetMapping(value = "/emailValidation")
    public ResponseEntity<Resource> getEmailDetails(@NonNull final @RequestParam String emailId) {

        var startTime = Instant.now();
        ResponseEntity<Resource> emailValidationResponse = null;
        try {
            log.info("Email Validation Requested");
            emailValidationResponse = emailValidationService.emailValidate(emailId);
        } finally {
            var duration = Duration.between(startTime, Instant.now()).toMillis();
            if (emailValidationResponse != null && emailValidationResponse.getStatusCode().is2xxSuccessful()) {
                log.info("Email validation successful for email: {} in {} ms", emailId, duration);
            } else {
                log.error("Email validation failed for email: {} in {} ms", emailId, duration);
            }
        }
        return emailValidationResponse;
    }
}
