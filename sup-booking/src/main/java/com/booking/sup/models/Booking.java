package com.booking.sup.models;

import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bookings")
@NamedEntityGraphs({
    @NamedEntityGraph(name = "Booking.full", attributeNodes = {
        @NamedAttributeNode("client"),
        @NamedAttributeNode("sup"),
        @NamedAttributeNode("instructor")
    })
})
public class Booking {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long revenue;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id")
  private Client client;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sup_id")
  private Sup sup;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "instructor_id", nullable = true)
  private Instructor instructor;

  private LocalDateTime startedAt;

  private LocalDateTime finishedAt;

  public Booking() {
  }

  public Booking(
      Long id,
      Client client,
      Sup sup,
      Instructor instructor,
      LocalDateTime startedAt,
      LocalDateTime finishedAt) {
    this.id = id;
    this.client = client;
    this.sup = sup;
    this.instructor = instructor;
    this.startedAt = startedAt;
    this.finishedAt = finishedAt;
  }

  public Long countRevenue() {
    long pricePerHour = sup.getSupType().getPricePerHour();
    Duration duration = Duration.between(startedAt, finishedAt);
    long hours = duration.toHours();
    long minutes = duration.toMinutesPart();
    if (minutes > 0) {
      ++hours;
    }
    this.revenue = pricePerHour * hours;
    return this.revenue;
  }
}
