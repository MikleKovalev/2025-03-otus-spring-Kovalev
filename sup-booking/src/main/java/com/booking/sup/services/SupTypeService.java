package com.booking.sup.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.booking.sup.models.Sup;
import com.booking.sup.models.SupType;
import com.booking.sup.repositories.SupRepository;
import com.booking.sup.repositories.SupTypeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SupTypeService {
  private static final int DEFAULT_SUP_TYPES_COUNT = 8;

  private final SupRepository supRepository;

  private final SupTypeRepository supTypeRepository;

  public List<SupType> getAllSupTypes() {
    return supTypeRepository.findAll();
  }

  public SupType getById(Long id) {
    return supTypeRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Sup type with id %d not found".formatted(id)));
  }

  public SupTypeService(SupRepository supRepository, SupTypeRepository supTypeRepository) {
    this.supRepository = supRepository;
    this.supTypeRepository = supTypeRepository;
  }
}
