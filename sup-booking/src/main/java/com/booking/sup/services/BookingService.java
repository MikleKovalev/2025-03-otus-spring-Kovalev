package com.booking.sup.services;

import java.time.LocalDateTime;
import java.util.List;

import com.booking.sup.models.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.booking.sup.exceptions.EntityNotFoundException;
import com.booking.sup.exceptions.NoSuchSupAvailableException;
import com.booking.sup.repositories.BookingRepository;
import com.booking.sup.repositories.SupRepository;

@Service
@AllArgsConstructor
public class BookingService {
  private final SupService supService;

  private final BookingRepository bookingRepository;

  private final InstructorService instructorService;

  private final ClientService clientService;

  public List<Sup> getFreeSupsByTimeInterval(LocalDateTime start, LocalDateTime finish) {
    List<Booking> bookings = bookingRepository.findByTimeInterval(start, finish);
    List<Long> occupiedSupIds = bookings
            .stream()
            .map(booking -> booking.getSup().getId())
            .toList();
    List<Sup> freeSups = supService.getByIdNotIn(occupiedSupIds);
    if (freeSups.isEmpty()) {
      throw new NoSuchSupAvailableException("All sups are occupied.");
    }
    return freeSups;
  }

  public Booking createBooking(
      String clientName,
      String clientPhone,
      Long supTypeId,
      Long instructorId,
      LocalDateTime start,
      LocalDateTime finish) {
    List<Sup> freeSups = getFreeSupsByTimeInterval(start, finish);
    Client client;
    try {
      client = clientService.getByPhoneNumber(clientPhone);
    } catch (EntityNotFoundException ex) {
      client = clientService.insert(clientName, clientPhone);
    }
    Instructor instructor = null;
    if (instructorId != null) {
      instructor = instructorService.getById(instructorId);
    }
    Sup supForBooking;
    if (supTypeId != null) {
      supForBooking = freeSups.stream()
          .filter(sup -> sup.getSupType().getId().equals(supTypeId))
          .findFirst()
          .orElseThrow(() -> new NoSuchSupAvailableException(
              "Извините, все сапы фирмы выбраной вами фирмы будут заняты. Пожалуйста, выберите другого производителя."));
    } else {
      supForBooking = freeSups.getFirst();
    }
    Booking booking = new Booking(
        0L,
        client,
        supForBooking,
        instructor,
        start,
        finish);
    booking.countRevenue();
    return bookingRepository.save(booking);
  }

  public long getTotalBookingsCount() {
    return bookingRepository.count();
  }

  public long getActiveBookingsCount() {
    return bookingRepository.countByFinishedAtAfter(LocalDateTime.now());
  }

  public long getTotalRevenue() {
    return bookingRepository.countRevenueToTime(LocalDateTime.now());
  }

  public double getAverageCheck() {
    return bookingRepository.countAverageRevenueToTime(LocalDateTime.now());
  }
}
