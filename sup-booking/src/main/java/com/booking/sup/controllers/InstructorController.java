package com.booking.sup.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.sup.dtos.InstructorForUserDto;
import com.booking.sup.mappers.InstructorMapper;
import com.booking.sup.services.InstructorService;

@RestController
public class InstructorController {
  private final InstructorMapper instructorMapper;

  private final InstructorService instructorService;

  @GetMapping("/api/instructors")
  public List<InstructorForUserDto> getAllInstructorsForUser() {
    return instructorService
        .getAllInstructors().stream().map(instructorMapper::toUserDto).toList();
  }

  public InstructorController(
      InstructorMapper instructorMapper,
      InstructorService instructorService) {
    this.instructorMapper = instructorMapper;
    this.instructorService = instructorService;
  }
}
