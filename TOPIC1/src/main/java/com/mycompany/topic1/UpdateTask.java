/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.topic1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Lenovo
 */
public class UpdateTask {
    ArrayList<Customer> cust;
    ArrayList<Driver> driver;
    private Customer selectedCustomer;
    private Driver selectedDriver;
    private static final String API_KEY = "AIzaSyApU0_BL0WUids6ocsdYoxmcPZsm1M-CaA";
    public UpdateTask(Driver selectedD, Customer selectedC) {
        
        cust=new ArrayList<>();
        driver=new ArrayList<>();
        populateArrayList();
        for(int i=0; i<cust.size(); i++){
            if(selectedC.getName().equals(cust.get(i).getName()))
                this.selectedCustomer=cust.get(i);
        }
        for(int i=0; i<driver.size(); i++){
            if(selectedD.getName().equals(driver.get(i).getName()))
                this.selectedDriver=driver.get(i);
        }
        
        try {
//            int driverIndex= Driver.getSelectedIndex();
//            Driver selectedDriver=custChoose.get(driverIndex);
            
            selectedDriver.setStatus("Unavailable");
            selectedDriver.setCust(selectedCustomer.getName());
            selectedCustomer.setStatus("Waiting");
            saveDriverToFile();
            saveCustomerToFile();
            
            Timer timer= new Timer();
            int driverToCust=getTimeTakenSec(selectedDriver.getCurrLocation(),selectedCustomer.getStart());
            int totalDistance=driverToCust+getTimeTakenSec(selectedCustomer.getStart(),selectedCustomer.getDestination());
            TimerTask updateTask = new TimerTask(){
                int dtc=driverToCust;
                int totalD=totalDistance;
                @Override
                public void run() {
                    dtc--;
                    totalD--;
                    cust.clear();
                    driver.clear();
                    populateArrayList();
                    for(int i=0; i<cust.size(); i++){
                        if(selectedC.getName().equals(cust.get(i).getName()))
                            selectedCustomer=cust.get(i);
                    }
                    for(int i=0; i<driver.size(); i++){
                        if(selectedD.getName().equals(driver.get(i).getName()))
                            selectedDriver=driver.get(i);
                    }
//                    JOptionPane.showMessageDialog(null, "OK");
                    if(dtc==0){
                        selectedCustomer.setStatus("Picked");  
                        selectedDriver.setCurrLocation(new Location(selectedCustomer.getStart()));
                        saveDriverToFile();
                        saveCustomerToFile();
                    }
                    if(totalD==0){
                        selectedCustomer.setStatus("Reached");                       
                        selectedDriver.setStatus("Available");
                        selectedDriver.setCust("");
                        selectedDriver.setCurrLocation(new Location(selectedCustomer.getDestination()));
                        saveDriverToFile();
                        saveCustomerToFile();
                        timer.cancel();
                    }
                    saveDriverToFile();
                    saveCustomerToFile();
                    
                }
            };
            timer.scheduleAtFixedRate(updateTask,0,1000);//start schedule
            
            JOptionPane.showMessageDialog(null, "Successfully picked a driver");
//            this.dispose();
            //method for set the customer for driver-link selectedDriver with last index of customerlist
        } catch (Exception ex) {
            Logger.getLogger(PickDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public int getTimeTakenSec(String source, String destination)throws Exception{
        //request distance matrix
        var url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + source.replace(" ", "") + "&destinations=" + destination.replace(" ", "") + "&key=" + API_KEY;
        var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        var client = HttpClient.newBuilder().build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        
        JSONParser jp = new JSONParser();
        //get distance and time from the json file
        JSONObject jo = (JSONObject) jp.parse(response); //contains all information in the json file
        JSONArray ja = (JSONArray) jo.get("rows");  //get information from rows which contain the distance and duration
        jo = (JSONObject) ja.get(0);    //get contains information from elements only
        ja = (JSONArray) jo.get("elements");    //store information inside elements
        jo = (JSONObject) ja.get(0);    //store the new JSONArray
        JSONObject jdistance = (JSONObject) jo.get("distance");
        JSONObject jduration = (JSONObject) jo.get("duration");
        long distance = (long) jdistance.get("value");  //get value of distance in meter
        long duration = (long) jduration.get("value");  //get value of duration in seconds
        
        long[] value = {distance, duration};
        
        Integer newdistance = (int)(long)value[0];
        Integer newduration = (int)(long)value[1];
        int newvalue = newduration/60;
        
        return newvalue;
    }
    public void populateArrayList(){
        try{
            FileInputStream file = new FileInputStream("Customer.dat");
            ObjectInputStream inputFile = new ObjectInputStream(file);
            
            boolean endOfFile=false;
            while(!endOfFile){
                try{
                    cust.add((Customer)inputFile.readObject());
                }catch(IOException e){
                    endOfFile=true;
                }catch(Exception f){
                    JOptionPane.showMessageDialog(null,f.getMessage());
                }
            }
            inputFile.close();
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        
        try{
            FileInputStream file2 = new FileInputStream("Driver.dat");
            ObjectInputStream inputFile2 = new ObjectInputStream(file2);
            
            boolean endOfFile=false;
            while(!endOfFile){
                try{
                    driver.add((Driver)inputFile2.readObject());
                }catch(IOException e){
                    endOfFile=true;
                }catch(Exception f){
                    JOptionPane.showMessageDialog(null,f.getMessage());
                }
            }
            inputFile2.close();
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
    public void saveDriverToFile(){
        try{
            FileOutputStream file = new FileOutputStream("Driver.dat");
            ObjectOutputStream outputFile = new ObjectOutputStream(file);
            
            for(int i=0; i<driver.size();i++){
                outputFile.writeObject(driver.get(i));
            }
            outputFile.close();
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void saveCustomerToFile(){
        try{
            FileOutputStream file = new FileOutputStream("Customer.dat");
            ObjectOutputStream outputFile = new ObjectOutputStream(file);
            
            for(int i=0; i<cust.size();i++){
                outputFile.writeObject(cust.get(i));
            }
            outputFile.close();
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
