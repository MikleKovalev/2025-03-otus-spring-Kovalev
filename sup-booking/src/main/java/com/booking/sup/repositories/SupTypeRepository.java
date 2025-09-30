package com.booking.sup.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.sup.models.SupType;

public interface SupTypeRepository extends JpaRepository<SupType, Long> {
}
