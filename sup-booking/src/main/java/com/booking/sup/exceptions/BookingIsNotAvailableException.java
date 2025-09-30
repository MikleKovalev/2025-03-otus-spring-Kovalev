package com.booking.sup.exceptions;

public class BookingIsNotAvailableException extends RuntimeException {
  public BookingIsNotAvailableException(String message) {
    super(message);
  }
}
