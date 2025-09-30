package com.booking.sup.dtos;

public class InstructorForUserDto {
  private Long id;
  private String name;
  private int pricePerHour;

  public InstructorForUserDto() {
  }

  public InstructorForUserDto(Long id, String name, int pricePerHour) {
    this.id = id;
    this.name = name;
    this.pricePerHour = pricePerHour;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPricePerHour() {
    return pricePerHour;
  }

  public void setPricePerHour(int pricePerHour) {
    this.pricePerHour = pricePerHour;
  }
}
