package com.example.hair_care_tracker_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 409
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Nickname already exists.")
public class NicknameTakenException extends RuntimeException {
}
