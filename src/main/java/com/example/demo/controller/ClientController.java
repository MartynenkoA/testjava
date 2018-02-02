package com.example.demo.controller;

import com.example.demo.ClientRepository;
import com.example.demo.model.Client;
import com.example.demo.service.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {
    @Autowired
    //private ClientServiceImpl clientService;
    private ClientRepository clientRepository;

    @RequestMapping("/getall")
    public List<Client> getAll(){
        return (List<Client>) clientRepository.findAll();
    }

    @RequestMapping("/getById")
    public Client getById(@RequestParam("id") int id){
        return clientRepository.findById(id);
    }

//    @RequestMapping("/getall")
//    public List<Client> getAll(){
//        return clientService.listClients();
//    }
//
//    @RequestMapping("/getById")
//    public Client getById(@RequestParam("id") int id){
//        return clientService.getClientById(id);
//    }
}
