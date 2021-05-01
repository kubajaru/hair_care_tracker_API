package com.example.hair_care_tracker_api.repository;

import com.example.hair_care_tracker_api.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("SELECT p from Profile p where p.appUser.email = :email")
    Profile findByEmail(@Param("email") String email);
}
