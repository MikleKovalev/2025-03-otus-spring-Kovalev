package com.booking.sup.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sups")
public class Sup {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sup_type_id")
  private SupType supType;

  public Sup(long id, SupType supType) {
    this.id = id;
    this.supType = supType;
  }

  public Sup() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return supType.getName();
  }

  public int getPricePerHour() {
    return supType.getPricePerHour();
  }

  public SupType getSupType() {
    return supType;
  }

  public void setSupType(SupType supType) {
    this.supType = supType;
  }
}
