package com.booking.sup.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.sup.dtos.SupDto;
import com.booking.sup.dtos.SupTypeDto;
import com.booking.sup.mappers.SupMapper;
import com.booking.sup.mappers.SupTypeMapper;
import com.booking.sup.models.Sup;
import com.booking.sup.models.SupType;
import com.booking.sup.repositories.SupRepository;
import com.booking.sup.repositories.SupTypeRepository;
import com.booking.sup.services.SupService;
import com.booking.sup.services.SupTypeService;

@RestController
public class SupController {
  private final SupMapper supMapper;

  private final SupTypeMapper supTypeMapper;

  private final SupService supService;

  private final SupTypeService supTypeService;

  @GetMapping("/api/sup-types")
  public List<SupTypeDto> getSups() {
    return supTypeService.getAllSupTypes().stream().map(supTypeMapper::toDto).toList();
  }

  public SupController(
      SupMapper supMapper,
      SupTypeMapper supTypeMapper,
      SupService supService,
      SupTypeService supTypeService) {
    this.supMapper = supMapper;
    this.supTypeMapper = supTypeMapper;
    this.supService = supService;
    this.supTypeService = supTypeService;
  }
}
