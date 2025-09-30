package com.booking.sup.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "instructors")
public class Instructor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String fullName;

  private String phone;

  private int pricePerHour;

  public Instructor() {
  }

  public Instructor(Long id, String fullName, String phone, int pricePerHour) {
    this.id = id;
    this.fullName = fullName;
    this.phone = phone;
    this.pricePerHour = pricePerHour;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public int getPricePerHour() {
    return pricePerHour;
  }

  public void setPricePerHour(int pricePerHour) {
    this.pricePerHour = pricePerHour;
  }
}
