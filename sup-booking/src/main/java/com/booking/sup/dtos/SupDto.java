package com.booking.sup.dtos;

public class SupDto {
  private Long id;

  private SupTypeDto supType;

  public SupDto() {
  }

  public SupDto(Long id, SupTypeDto supType) {
    this.id = id;
    this.supType = supType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SupTypeDto getSupType() {
    return supType;
  }

  public void setSupType(SupTypeDto supType) {
    this.supType = supType;
  }
}
