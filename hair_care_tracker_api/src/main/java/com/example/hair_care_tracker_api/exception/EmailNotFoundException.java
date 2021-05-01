package com.example.hair_care_tracker_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 400
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Email not found")
public class EmailNotFoundException extends RuntimeException {

}
