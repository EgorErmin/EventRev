package com.example.egor.eventrew.model;

import com.example.egor.eventrew.model.Event;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class User implements Serializable {

    private Integer id;

    private String email;

    private String firstname;

    private String lastname;

    Set<Event> events = new HashSet<>();

    private String SESSIONID;

    public User(){

    }

    public String getSESSINID() {
        return SESSIONID;
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.email, this.SESSIONID);
    }

    public void setSESSINID(String SESSINID) {
        this.SESSIONID = SESSINID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

}