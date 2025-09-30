package com.booking.sup.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.booking.sup.models.Instructor;
import com.booking.sup.repositories.InstructorRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class InstructorService {
  private final InstructorRepository instructorRepository;

  public List<Instructor> getAllInstructors() {
    return instructorRepository.findAll();
  }

  public Instructor getById(Long id) {
    return instructorRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Instructor with id %d not found".formatted(id)));
  }

  public InstructorService(InstructorRepository instructorRepository) {
    this.instructorRepository = instructorRepository;
  }
}
