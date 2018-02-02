package com.example.demo.service;

import com.example.demo.model.Client;

import java.util.List;

public interface ClientService {
    public Client addClient(Client client);
    public Client updateClient(Client client);
    public void removeClient(Client client);
    public Client getClientById(int id);
    public List<Client> listClients();
}

