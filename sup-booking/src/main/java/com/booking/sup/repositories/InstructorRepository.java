package com.booking.sup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.sup.models.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}
