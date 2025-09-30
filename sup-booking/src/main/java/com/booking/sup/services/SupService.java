package com.booking.sup.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.booking.sup.models.Sup;
import com.booking.sup.models.SupType;
import com.booking.sup.repositories.SupRepository;

@Service
public class SupService {

  private final SupRepository supRepository;

  public List<Sup> getBySupType(SupType supType) {
    return supRepository.findBySupType(supType);
  }

  public List<Sup> getBySupTypeId(Long supTypeId) {
    return supRepository.findBySupTypeId(supTypeId);
  }

  public List<Sup> getByIdNotIn(List<Long> supIds) {
    if (supIds.isEmpty()) {
      return supRepository.findAll();
    }
    return supRepository.findByIdNotIn(supIds);
  }

  public SupService(SupRepository supRepository) {
    this.supRepository = supRepository;
  }

  public List<Sup> getAllSups() {
    return supRepository.findAll();
  }
}
