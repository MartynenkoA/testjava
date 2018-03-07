package com.example.demo.controller;

import com.example.demo.ClientRepository;
import com.example.demo.JedisFactory;
import com.example.demo.model.Client;
import com.example.demo.model.UserKey;
import com.example.demo.service.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    private HttpServletRequest request;

    private UserKey userKey;
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

//    @RequestMapping(value="/getRedisClientName")
//    public String getRedisClientName(){
//        String userId = getUserId();
//        userKey = new UserKey(userId);
//        String result = "";
//        try (Jedis jedis = JedisFactory.getInstance().getJedisPool().getResource()){
////            jedis.set("client:1:name", "client1");
////            jedis.set("client:2:name", "client2");
////            jedis.set("client:1:address:1", "client_1_address_1");
////            jedis.set("client:2:address:1", "client_2_address_1");
////            jedis.set("client:2:address:2", "client_2_address_2");
//            result = jedis.get(userKey.getClientName());
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
//
//    @RequestMapping(value="/getRedisClientAddressById")
//    public String getRedisClientAddressById(@RequestParam("id") int id){
//        String userId = getUserId();
//        userKey = new UserKey(userId);
//        String result = "";
//        try (Jedis jedis = JedisFactory.getInstance().getJedisPool().getResource()){
//            result = jedis.get(userKey.getAddressById(id));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }

    private String getUserId() {
        return request.getHeader("userId");
    }
}
