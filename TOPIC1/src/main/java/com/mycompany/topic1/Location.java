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
public class Location implements Serializable {
    String point;

    public String getLocation() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
        
    }

    public Location(String point) {
        this.point = point;
    }
}
