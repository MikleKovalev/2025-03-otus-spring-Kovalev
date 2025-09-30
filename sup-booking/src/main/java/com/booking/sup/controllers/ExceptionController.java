package com.booking.sup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.booking.sup.exceptions.BookingIsNotAvailableException;
import com.booking.sup.exceptions.EntityNotFoundException;

@ControllerAdvice
public class ExceptionController {
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(BookingIsNotAvailableException.class)
  public ResponseEntity<String> handleBookingIsNotAvailableException(BookingIsNotAvailableException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }
}
