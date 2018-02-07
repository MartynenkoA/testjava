package com.example.demo.model;

public class UserKey {
    private String Client;

    public UserKey(String clientId) {
        Client = "client:" + clientId;
    }

    public String getClientName(){
        return Client + ":name";
    }
    public String getAddressById(int id){
        return Client + ":address" + ":" + id;
    }
}
