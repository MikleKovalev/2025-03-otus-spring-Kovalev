package com.booking.sup.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.booking.sup.models.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
  @EntityGraph(value = "Booking.full")
  List<Booking> findAll();

  @EntityGraph(value = "Booking.full")
  @Query("SELECT b FROM Booking b WHERE (b.startedAt < :finish AND b.finishedAt > :start)")
  // @Query("SELECT b FROM Booking b WHERE (:start <= b.startedAt AND :finish >=
  // b.finishedAt) OR (:start >= b.startedAt AND :start <= b.finishedAt) OR
  // (:finish >= b.startedAt AND :finish <= b.finishedAt) OR (:start >=
  // b.startedAt AND :finish <= b.finishedAt)")
  List<Booking> findByTimeInterval(
      @Param("start") LocalDateTime start,
      @Param("finish") LocalDateTime finish);

  @Query("SELECT SUM(b.revenue) FROM Booking b WHERE b.finishedAt < :date")
  Long countRevenueToTime(@Param("date") LocalDateTime date);

  @Query("SELECT AVG(b.revenue) from Booking b WHERE b.finishedAt < :date")
  Long countAverageRevenueToTime(@Param("date") LocalDateTime date);

  @Query("SELECT COUNT(b) FROM Booking b WHERE b.startedAt < :date AND b.finishedAt > :date")
  Long countActiveBookingsByTime(@Param("date") LocalDateTime date);

  long count();

  long countByFinishedAtAfter(LocalDateTime date);
}
