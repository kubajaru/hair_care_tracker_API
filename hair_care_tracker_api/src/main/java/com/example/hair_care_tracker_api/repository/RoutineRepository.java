package com.example.hair_care_tracker_api.repository;

import com.example.hair_care_tracker_api.entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
    @Query("select r from Routine r where r.appUser.email = :email")
    List<Routine> findAllByUser(@Param("email") String email);

    @Query("select r from Routine r where r.date = :date and r.appUser.email = :email")
    Routine findByDateAndUser(@Param("date") String date, @Param("email") String email);
}
