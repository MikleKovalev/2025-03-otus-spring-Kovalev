package com.booking.sup.controllers;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.booking.sup.dtos.BookingDto;
import com.booking.sup.dtos.CreateBookingDto;
import com.booking.sup.mappers.BookingMapper;
import com.booking.sup.repositories.BookingRepository;
import com.booking.sup.services.BookingService;

import jakarta.validation.Valid;

@RestController
@AllArgsConstructor
public class BookingController {
  private final BookingMapper bookingMapper;

  private final BookingService BookingService;

  private final BookingRepository rep;

  @PostMapping("/api/bookings")
  @CrossOrigin(origins = "*")
  @ResponseStatus(value = HttpStatus.CREATED)
  public void createBooking(@Valid @RequestBody CreateBookingDto dto) {
    BookingService.createBooking(
        dto.getClientName(),
        dto.getClientPhone(),
        dto.getSupTypeId(),
        dto.getInstructorId(),
        dto.getStartAsLocalDateTime(),
        dto.getFinishAsLocalDateTime());
  }

  @PostMapping("/api/admin/bookings/filter")
  public List<BookingDto> filterBookings() {
    return null;
  }

  @GetMapping("/api/bookings")
  public List<BookingDto> getAllBookings() {
    return rep.findAll().stream().map(bookingMapper::toDto).toList();
  }
}
