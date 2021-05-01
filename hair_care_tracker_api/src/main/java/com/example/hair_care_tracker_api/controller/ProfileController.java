package com.example.hair_care_tracker_api.controller;

import com.example.hair_care_tracker_api.entity.AppUser;
import com.example.hair_care_tracker_api.entity.Profile;
import com.example.hair_care_tracker_api.repository.ProfileRepository;
import com.example.hair_care_tracker_api.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Profile controller. Responsible for updating and returning user hair profile.
 */
@RestController
public class ProfileController {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    /**
     * Constructor of Profile controller. Used to inject the repositories to work with database.
     *
     * @param userRepository    handles users
     * @param profileRepository handles profiles
     */
    public ProfileController(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    /**
     * Method returns the profile of user from the token provided with the request.
     *
     * @param httpServletRequest object to get the email decoded in the UserFilter
     * @return JSON representation of profile
     */
    @GetMapping("/profile")
    Profile getUserProfile(HttpServletRequest httpServletRequest) {
        Profile profile = profileRepository.findByEmail((String) httpServletRequest.getAttribute("email"));
        if (profile != null) {
            profile.setUser(null);
            return profile;
        } else {
            return null;
        }
    }

    /**
     * Method to update the profile of user from the token provided with the request.
     *
     * @param newProfile         JSON representation of Profile
     * @param httpServletRequest object go get the email decoded in the UserFilter
     * @throws ServletException if user is not found - 500 HTTP status code
     */
    @PutMapping("/profile")
    void updateUserProfile(@RequestBody Profile newProfile, HttpServletRequest httpServletRequest) throws ServletException {
        Profile profile = profileRepository.findByEmail((String) httpServletRequest.getAttribute("email"));
        AppUser appUser = userRepository.findByEmail((String) httpServletRequest.getAttribute("email"));

        if (profile != null) {
            profileRepository.findById(profile.getProfileId())
                    .map(foundProfile -> {
                        foundProfile.setAge(newProfile.getAge());
                        foundProfile.setCurlType(newProfile.getCurlType());
                        foundProfile.setDenseness(newProfile.getDenseness());
                        foundProfile.setProblems(newProfile.getProblems());
                        foundProfile.setUser(appUser);
                        foundProfile.setLength(newProfile.getLength());
                        foundProfile.setPorosity(newProfile.getPorosity());
                        foundProfile.setState(newProfile.getState());
                        foundProfile.setThickness(newProfile.getThickness());
                        return profileRepository.save(foundProfile);
                    });
        } else {
            if (appUser != null) {
                newProfile.setUser(appUser);
                profileRepository.save(newProfile);
            } else {
                throw new ServletException();
            }
        }
    }
}
