package com.booking.sup.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.sup.models.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
  Optional<Client> findByPhoneNumber(String phoneNumber);
}
