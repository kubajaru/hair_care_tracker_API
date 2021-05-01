package com.example.hair_care_tracker_api.repository;

import com.example.hair_care_tracker_api.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<AppUser, Long> {
    @Query("SELECT u from AppUser u where u.email = :email")
    AppUser findByEmail(@Param("email") String email);

    @Query("SELECT u from AppUser u where u.nickname = :nickname")
    AppUser findByNickname(@Param("nickname") String nickname);
}
