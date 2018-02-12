package com.example.demo.dao;

import com.example.demo.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Slf4j
@Transactional
public class ClientDaoImpl implements ClientDao {
    private static final Logger logger = LoggerFactory.getLogger(ClientDaoImpl.class);

    @PersistenceContext
    public EntityManager entityManager;

    @Override
    public Client addClient(Client client) {
        entityManager.persist(client);
        logger.info("Client successfully saved. Client details: " + client);
        return client;
    }

    @Override
    public Client updateClient(Client client) {
        entityManager.merge(client);
        logger.info("Client successfully update. Client details: " + client);
        return client;
    }

    @Override
    public void removeClient(Client client) {
        entityManager.remove(client);
        logger.info("Client successfully removed. Client details: " + client);
    }

    @Override
    public Client getClientById(long id) {
        Client client = null;
        try{
            client = entityManager
                    .createQuery("from Client where id = :id", Client.class)
                    .setParameter("id", id).getSingleResult();
        }catch(Exception e){
            logger.info("Client not found. Details" + e.getMessage());
        }

        logger.info("Client successfully loaded. Client details: " + client);

        return client;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Client> listClients() {
        List<Client> clients = entityManager.createQuery("select cl from Client cl").getResultList();

        for (Client client: clients){
            logger.info("Book list: " + client);
        }

        return clients;
    }
}
