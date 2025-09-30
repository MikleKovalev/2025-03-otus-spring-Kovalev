package com.booking.sup.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SupTypeDto {
  private Long id;

  private String name;

  private int pricePerHour;

  public SupTypeDto() {
  }

  public SupTypeDto(Long id, String name, int pricePerHour) {
    this.id = id;
    this.name = name;
    this.pricePerHour = pricePerHour;
  }
}
