package com.validation.email.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface EmailValidationService {

    public ResponseEntity<Resource> emailValidate(String email);
}
