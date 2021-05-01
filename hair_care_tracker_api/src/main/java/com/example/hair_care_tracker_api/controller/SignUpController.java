package com.example.hair_care_tracker_api.controller;

import com.example.hair_care_tracker_api.entity.AppUser;
import com.example.hair_care_tracker_api.exception.NicknameTakenException;
import com.example.hair_care_tracker_api.exception.UserExistsException;
import com.example.hair_care_tracker_api.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * SignUp controller. Responsible for signing up the new user.
 */
@RestController
public class SignUpController {

    private final UserRepository userRepository;

    /**
     * Constructor of controller class. Used to inject the repository.
     *
     * @param userRepository handles the database operations.
     */
    public SignUpController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Endpoint to sign up new user. Checks if user is not in database and
     * saves him. After that returns the JWT token.
     *
     * @param newAppUser JSON object representing user
     * @return JWT token
     * @throws UserExistsException    HTTP 400 response code
     * @throws NicknameTakenException HTTP 409 response code
     */
    @PostMapping("/signup")
    String signUp(@RequestBody AppUser newAppUser) throws UserExistsException, NicknameTakenException {
        AppUser appUserByEmail = userRepository.findByEmail(newAppUser.getEmail());
        if (appUserByEmail != null) {
            throw new UserExistsException(); // 400 - bad request
        }
        AppUser appUserByNickname = userRepository.findByNickname(newAppUser.getNickname());
        if (appUserByNickname != null) {
            throw new NicknameTakenException(); // 409 - conflict
        }
        userRepository.save(newAppUser);
        return Jwts.builder()
                .setSubject(newAppUser.getEmail()) // We will use it to get things related only to that user.
                .claim("role", "user") // Role is only for assignment. We will not leverage it at this point.
                .signWith(SignatureAlgorithm.HS256, "oCIZHIEqSmBcO1O") // We probably should think about better secret.
                .compact(); // Create JWT token
    }

}
