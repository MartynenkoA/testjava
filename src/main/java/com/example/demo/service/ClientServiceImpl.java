package com.example.demo.service;

import com.example.demo.dao.ClientDao;
import com.example.demo.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientDao clientDao;

    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    @Transactional
    public Client addClient(Client client) {
        this.clientDao.addClient(client);
        return client;
    }

    @Override
    @Transactional
    public Client updateClient(Client client) {
        this.clientDao.updateClient(client);
        return client;
    }

    @Override
    @Transactional
    public void removeClient(Client client) {
        this.clientDao.removeClient(client);
    }

    @Override
    @Transactional
    public Client getClientById(long id) {
        return this.clientDao.getClientById(id);
    }

    @Override
    @Transactional
    public List<Client> listClients() {
        return this.clientDao.listClients();
    }
}
