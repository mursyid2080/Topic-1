/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.topic1;

import java.io.Serializable;

/**
 *
 * @author Lenovo
 */
public class Driver implements Serializable {
    
    private String name;
    private String cust;
    private String status;
    private int capacity;
    private double rating;
    private Location currLocation;
    private String EAT;

    public Driver(String name, int capacity, Location currLocation) {
        this.name = name;
        this.capacity = capacity;
        this.rating = 4.5;
        this.currLocation = currLocation;
        this.status="Available";
        this.cust="";
        this.EAT="";
    }

    public String getEAT() {
        return EAT;
    }

    public void setEAT(String EAT) {
        this.EAT = EAT;
    }

    public String getCust() {
        return cust;
    }

    public void setCust(String cust) {
        this.cust = cust;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCurrLocation() {
        return currLocation.getLocation();
    }

    public void setCurrLocation(Location currLocation) {
        this.currLocation = currLocation;
    }
    
}
