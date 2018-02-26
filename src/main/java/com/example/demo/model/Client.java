package com.example.demo.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "client")
@TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class)
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String Name;

    private String Address;

    private UUID TenantId;

    @Type(type = "jsonb-node")
    @Column(columnDefinition = "jsonb")
    private JsonNode test;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }
    public JsonNode getTest() {
        return test;
    }

    public void setTest(JsonNode newdata) {
        this.test = newdata;
    }
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + Name + '\'' +
                ", address='" + Address + '\'' +
                '}';
    }
}
