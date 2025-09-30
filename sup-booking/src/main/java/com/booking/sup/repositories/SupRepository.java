package com.booking.sup.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.booking.sup.models.Sup;
import com.booking.sup.models.SupType;

public interface SupRepository extends JpaRepository<Sup, Long> {
  List<Sup> findBySupType(SupType supType);

  List<Sup> findBySupTypeId(Long supTypeId);

  List<Sup> findByIdNotIn(List<Long> ids);
}
