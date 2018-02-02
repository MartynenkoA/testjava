package com.example.demo.dao;

import com.example.demo.model.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientDaoImpl implements ClientDao {
    private static final Logger logger = LoggerFactory.getLogger(ClientDaoImpl.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addClient(Client client) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(client);
        logger.info("Client successfully saved. Client details: " + client);
    }

    @Override
    public void updateClient(Client client) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(client);
        logger.info("Client successfully update. Client details: " + client);
    }

    @Override
    public void removeClient(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Client client = (Client) session.load(Client.class, new Integer(id));
        if (client != null){
            session.delete(client);
        }
        logger.info("Client successfully removed. Client details: " + client);
    }

    @Override
    public Client getClientById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Client client = (Client) session.load(Client.class, new Integer(id));
        logger.info("Client successfully loaded. Client details: " + client);

        return client;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Client> listClients() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Client> clientList = session.createQuery("from Client").list();
        for (Client client: clientList){
            logger.info("Book list: " + client);
        }

        return listClients();
    }
}
