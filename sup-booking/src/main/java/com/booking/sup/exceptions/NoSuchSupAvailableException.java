package com.booking.sup.exceptions;

public class NoSuchSupAvailableException extends RuntimeException {
  public NoSuchSupAvailableException(String message) {
    super(message);
  }
}
