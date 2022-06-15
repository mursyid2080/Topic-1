/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.topic1;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Lenovo
 */
public class Timeus implements Serializable{
    int hour;
    int minute;
    Timeus time;
    public Timeus(){
        
    }
    public int getTimeInSec(){
        this.time=time;
        int totalTimeSec=(time.getHour()*60)+time.getMinute();
        return totalTimeSec;
    }
    public Timeus(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
    public String whatTime(){
        return String.format("%02d%02d",hour,minute);
    }
    public void setTime(Timeus time){
        this.time=time;
    }
}

