package com.booking.sup.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.booking.sup.models.Client;
import com.booking.sup.repositories.ClientRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {
  private final ClientRepository clientRepository;

  public List<Client> getAllClients() {
    return clientRepository.findAll();
  }

  public Client getByPhoneNumber(String phoneNumber) {
    return clientRepository.findByPhoneNumber(phoneNumber)
        .orElseThrow(() -> new EntityNotFoundException("Client with phone number %s not found".formatted(phoneNumber)));
  }

  public Client insert(String name, String phone) {
    var client = new Client(0, name, phone);
    return clientRepository.save(client);
  }

  public Client update(long id, String name, String phone) {
    var client = new Client(id, name, phone);
    return clientRepository.save(client);
  }

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }
}
