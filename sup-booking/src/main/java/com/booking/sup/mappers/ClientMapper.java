package com.booking.sup.mappers;

import org.springframework.stereotype.Component;

import com.booking.sup.dtos.ClientDto;
import com.booking.sup.models.Client;

@Component
public class ClientMapper {
  public ClientDto toDto(Client client) {
    return new ClientDto(client.getId(), client.getName(), client.getPhoneNumber());
  }
}
