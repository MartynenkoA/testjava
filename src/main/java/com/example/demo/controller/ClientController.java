package com.example.demo.controller;

import com.example.demo.ClientRepository;
import com.example.demo.model.Client;
import com.example.demo.service.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientServiceImpl clientService;
    //private ClientRepository clientRepository;

//    @RequestMapping("/getall")
//    public List<Client> getAll(){
//        return (List<Client>) clientRepository.findAll();
//    }
//
//    @RequestMapping("/getById")
//    public Client getById(@RequestParam("id") int id){
//        return clientRepository.findById(id);
//    }
//
//    @RequestMapping("/save")
//    public void getById(@RequestBody Client client ){
//        clientRepository.save(client);
//    }

    @RequestMapping("/getall")
    public List<Client> getAll(){
        return clientService.listClients();
    }

    @RequestMapping("/getById")
    public Client getById(@RequestParam("id") int id){
        return clientService.getClientById(id);
    }

    @RequestMapping("/remove/{id}")
    public String remove(@PathVariable("id") int id){
        Client client = clientService.getClientById(id);
        clientService.removeClient(client);
        return "Client successfully deleted.";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces={MediaType.APPLICATION_JSON_VALUE})
    public Client add(@RequestBody Client client){
        return clientService.addClient(client);
    }

    @RequestMapping(value="/update", method=RequestMethod.POST,consumes={MediaType.APPLICATION_JSON_VALUE}, produces={MediaType.APPLICATION_JSON_VALUE})
    public String update(@RequestBody Client client){
        if (clientService.getClientById(client.getId()) == null){
            return "There is no such client.";
        }
        if(client.getId()>0){
            client = clientService.updateClient(client);
        }else{
            //throw error here
        }
        return "Client successfully updated.";
    }
}
