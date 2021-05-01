package com.example.hair_care_tracker_api.controller;

import com.example.hair_care_tracker_api.entity.AppUser;
import com.example.hair_care_tracker_api.entity.UserCredentials;
import com.example.hair_care_tracker_api.exception.EmailNotFoundException;
import com.example.hair_care_tracker_api.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Login controller. Responsible for log in the user.
 */
@RestController
public class LogInController {
    private final UserRepository userRepository;

    /**
     * Constructor of controller class. Used to inject the repository.
     *
     * @param userRepository handles the database operations.
     */
    public LogInController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Method to log in user. If email is in database and hashed passwords are matching it returns the JWT token.
     * If the password is not matching it return the empty string.
     *
     * @param userCredentials JSON object with email and password
     * @return JWT token
     * @throws EmailNotFoundException HTTP 400 response code
     */
    @PostMapping("/login")
    String logIn(@RequestBody UserCredentials userCredentials) throws EmailNotFoundException {
        AppUser appUser = userRepository.findByEmail(userCredentials.getEmail());
        if (appUser == null) {
            throw new EmailNotFoundException(); // bad request - 400 status code
        }
        if (appUser.getPassword().equals(userCredentials.getPassword())) {
            return Jwts.builder()
                    .setSubject(appUser.getEmail()) // We will use it to get things related only to that user.
                    .claim("role", "user") // Role is only for assignment. We will not leverage it at this point.
                    .signWith(SignatureAlgorithm.HS256, "oCIZHIEqSmBcO1O") // We probably should think about better secret.
                    .compact(); // Create JWT token
        }
        return null; // In Flutter we get empty string.
    }
}
