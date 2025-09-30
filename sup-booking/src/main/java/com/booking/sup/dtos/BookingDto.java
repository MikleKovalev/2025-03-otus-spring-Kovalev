package com.booking.sup.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingDto {
  private Long id;

  private ClientDto client;

  private SupDto sup;

  private InstructorForUserDto instructor;

  private LocalDateTime startedAt;

  private LocalDateTime finishedAt;

  private Long revenue;
}
