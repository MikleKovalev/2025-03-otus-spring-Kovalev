package com.booking.sup.mappers;

import org.springframework.stereotype.Component;

import com.booking.sup.dtos.SupTypeDto;
import com.booking.sup.models.SupType;

@Component
public class SupTypeMapper {

  public SupTypeDto toDto(SupType supType) {
    return new SupTypeDto(supType.getId(), supType.getName(), supType.getPricePerHour());
  }

  public SupType toEntity(SupTypeDto dto) {
    return new SupType(dto.getId(), dto.getName(), dto.getPricePerHour());
  }
}
