package com.booking.sup.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sup_types")
public class SupType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private int pricePerHour;

  public SupType(long id, String name, int pricePerHour) {
    this.id = id;
    this.name = name;
    this.pricePerHour = pricePerHour;
  }

  public SupType() {
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
