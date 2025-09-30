package com.booking.sup.mappers;

import org.springframework.stereotype.Component;

import com.booking.sup.dtos.InstructorForUserDto;
import com.booking.sup.models.Instructor;

@Component
public class InstructorMapper {
  public InstructorForUserDto toUserDto(Instructor instructor) {
    return new InstructorForUserDto(instructor.getId(), instructor.getFullName(), instructor.getPricePerHour());
  }
}
