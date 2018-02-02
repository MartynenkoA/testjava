package com.example.demo.service;

import com.example.demo.dao.ClientDao;
import com.example.demo.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void addClient(Client client) {
        this.clientDao.addClient(client);
    }

    @Override
    @Transactional
    public void updateClient(Client client) {
        this.clientDao.updateClient(client);
    }

    @Override
    @Transactional
    public void removeClient(int id) {
        this.clientDao.removeClient(id);
    }

    @Override
    @Transactional
    public Client getClientById(int id) {
        return this.clientDao.getClientById(id);
    }

    @Override
    @Transactional
    public List<Client> listClients() {
        return this.clientDao.listClients();
    }
}
