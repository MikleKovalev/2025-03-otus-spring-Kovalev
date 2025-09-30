package com.booking.sup.dtos;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookingDto {
  Long id;

  @NotBlank(message = "Name is required")
  @Size(min = 1, max = 100, message = "Name should be with size from 1 to 100")
  private String clientName;

  @NotBlank(message = "Phone number is required")
  @Size(min = 11, max = 11, message = "Phone number must contain 11 digits")
  @Pattern(regexp = "^[0-9]+", message = "Phone number must contain only digits")
  private String clientPhone;

  private Long supTypeId;

  private Long instructorId;

  @NotNull(message = "You should set booking start time")
  private Long start;

  @NotNull(message = "You should set booking finish time")
  private Long finish;

  public CreateBookingDto() {
  }

  public CreateBookingDto(String clientName, String clientPhone, Long start, Long finish) {
    this.clientName = clientName;
    this.clientPhone = clientPhone;
    this.start = start;
    this.finish = finish;
  }

  public CreateBookingDto(
      String clientName,
      String clientPhone,
      Long supTypeId,
      Long instructorId,
      Long start,
      Long finish) {
    this.clientName = clientName;
    this.clientPhone = clientPhone;
    this.supTypeId = supTypeId;
    this.instructorId = instructorId;
    this.start = start;
    this.finish = finish;
  }

  public LocalDateTime getStartAsLocalDateTime() {
    return start != null ? LocalDateTime.ofInstant(Instant.ofEpochSecond(start / 1000), ZoneId.systemDefault()) : null;
  }

  public LocalDateTime getFinishAsLocalDateTime() {
    return finish != null ? LocalDateTime.ofInstant(Instant.ofEpochSecond(finish / 1000), ZoneId.systemDefault()) : null;
  }
}
