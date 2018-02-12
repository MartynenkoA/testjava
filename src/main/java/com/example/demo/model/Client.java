package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.hibernate.annotations.Cache;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Cache(region="common", usage = CacheConcurrencyStrategy.READ_WRITE)  // or @Cacheable(true) for JPA
@Getter
@Setter
@Table(name = "client")
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String Name;

    private String Address;

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

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + Name + '\'' +
                ", address='" + Address + '\'' +
                '}';
    }
}
