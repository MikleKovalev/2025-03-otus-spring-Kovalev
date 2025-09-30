package com.booking.sup.mappers;

import org.springframework.stereotype.Component;

import com.booking.sup.dtos.SupDto;
import com.booking.sup.models.Sup;

@Component
public class SupMapper {

  private final SupTypeMapper supTypeMapper;

  public SupDto toDto(Sup sup) {
    var supTypeDto = supTypeMapper.toDto(sup.getSupType());
    return new SupDto(sup.getId(), supTypeDto);
  }

  public Sup toEntity(SupDto dto) {
    return null;
  }

  public SupMapper(SupTypeMapper supTypeMapper) {
    this.supTypeMapper = supTypeMapper;
  }
}
