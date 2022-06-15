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
public class Customer implements Serializable{
    private String name;
    private String status;
    private Location start, destination;
    private int custCapacity;
    private String arrTime;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getArrTime() {
        return arrTime;
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    public Customer(String name, Location start, Location destination, int custCapacity, String arrTime) {
        this.name = name;
        this.start = start;
        this.status ="Pending";
        this.destination = destination;
        this.custCapacity = custCapacity;
        this.arrTime = arrTime;
    }

    public int getCustCapacity() {
        return custCapacity;
    }

    public void setCustCapacity(int custCapacity) {
        this.custCapacity = custCapacity;
    }


    public String getStart() {
        return start.getLocation();
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public String getDestination() {
        return destination.getLocation();
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
