package com.example.hair_care_tracker_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 400
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User exists in the database")
public class UserExistsException extends RuntimeException {
}
