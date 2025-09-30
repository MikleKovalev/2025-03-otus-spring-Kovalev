package com.booking.sup.mappers;

import org.springframework.stereotype.Component;

import com.booking.sup.dtos.BookingDto;
import com.booking.sup.models.Booking;

@Component
public class BookingMapper {
  private final SupMapper supMapper;

  private final ClientMapper clientMapper;

  private final InstructorMapper instructorMapper;

  public BookingDto toDto(Booking booking) {
    return new BookingDto(
            booking.getId(),
            clientMapper.toDto(booking.getClient()),
            supMapper.toDto(booking.getSup()),
            instructorMapper.toUserDto(booking.getInstructor()),
            booking.getStartedAt(),
            booking.getFinishedAt(),
            booking.getRevenue());
  }

  public BookingMapper(
      SupMapper supMapper,
      ClientMapper clientMapper,
      InstructorMapper instructorMapper) {
    this.supMapper = supMapper;
    this.clientMapper = clientMapper;
    this.instructorMapper = instructorMapper;
  }
}
