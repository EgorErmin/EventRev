package com.example.egor.eventrew.model;

import java.io.Serializable;
import java.util.*;


public class Event implements Serializable {
    private Integer id;

    private String name;

    private Date date;

    private String place;

    private String information;

    private Set<User> users = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nameEvent) {
        this.name = nameEvent;
    }

    public Date getData() {
        return date;
    }

    public void setData(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}