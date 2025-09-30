package com.booking.sup.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.Table;

@Entity
@Table(name = "tours")
@NamedEntityGraphs({
    @NamedEntityGraph(name = "tour-with-clients-and-instructors", attributeNodes = {
        @NamedAttributeNode("instructors"),
        @NamedAttributeNode("clients")
    }),
    @NamedEntityGraph(name = "tour-with-clients", attributeNodes = @NamedAttributeNode("clients"))
})
public class Tour {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private LocalDate eventDate;

  private LocalTime startTime;

  private int duration;

  private int maxMembers;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "tours_instructors", joinColumns = @JoinColumn(name = "tour_id"), inverseJoinColumns = @JoinColumn(name = "instructor_id"))
  private List<Instructor> instructors;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "tours_clients", joinColumns = @JoinColumn(name = "tour_id"), inverseJoinColumns = @JoinColumn(name = "client_id"))
  private List<Client> clients;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "tours_sups", joinColumns = @JoinColumn(name = "tour_id"), inverseJoinColumns = @JoinColumn(name = "sup_id"))
  private List<Sup> sups;

  public Tour() {
  }

  public Tour(long id, LocalDate eventDate, LocalTime startTime, int duration, int maxMembers) {
    this.id = id;
    this.eventDate = eventDate;
    this.startTime = startTime;
    this.duration = duration;
    this.maxMembers = maxMembers;
    // this.instructors = new ArrayList<>();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public LocalDate getEventDate() {
    return eventDate;
  }

  public void setEventDate(LocalDate eventDate) {
    this.eventDate = eventDate;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public List<Instructor> getInstructors() {
    return instructors;
  }

  public void setInstructors(List<Instructor> instructors) {
    this.instructors = instructors;
  }

  public List<Client> getClients() {
    return clients;
  }

  public void setClients(List<Client> clients) {
    this.clients = clients;
  }

  public List<Sup> getSups() {
    return sups;
  }

  public void setSups(List<Sup> sups) {
    this.sups = sups;
  }
}
