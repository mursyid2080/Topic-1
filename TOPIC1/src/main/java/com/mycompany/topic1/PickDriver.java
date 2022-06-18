/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
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
import javax.swing.JOptionPane;
import java.text.*;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Lenovo
 */
public class PickDriver extends javax.swing.JFrame {
    
    ArrayList<Customer> cust;
    ArrayList<Driver> driver;
    ArrayList<Driver> custChoose;
    Timeus tellTime;
    int hour;
    int minutes;
    
    
    private static final String API_KEY = "AIzaSyApU0_BL0WUids6ocsdYoxmcPZsm1M-CaA";
    /**
     * Creates new form PickDriver
     */
    public PickDriver() {
        initComponents();
        
        cust=new ArrayList<>();
        driver=new ArrayList<>();
        custChoose=new ArrayList<>();
        tellTime= new Timeus();
        populateArrayList();

        getTime();
        custSelection();
        
        String[] driverlist=new String[custChoose.size()];
        for(int i=0; i<custChoose.size(); i++){
            driverlist[i]=custChoose.get(i).getName();
        }
        Driver.setModel(new javax.swing.DefaultComboBoxModel<>(driverlist));

        int totalCustTime=hour*60+minutes;
        addRowToTable();
    }
    public void populateArrayList()
    {
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
    public void getTime()
    {
        try{
            FileInputStream file3 = new FileInputStream("Clock.dat");
            ObjectInputStream inputFile3 = new ObjectInputStream(file3);
            
            boolean endOfFile=false;
            while(!endOfFile){
                try{
                    tellTime.setTime((Timeus)inputFile3.readObject());
                }catch(IOException e){
                    endOfFile=true;
                }catch(Exception f){
                    JOptionPane.showMessageDialog(null,f.getMessage());
                }
            }
            inputFile3.close();
            
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
    public void custSelection(){
        if(!cust.get(cust.size()-1).getStatus().equals("Pending")){
            JOptionPane.showMessageDialog(null, "The last customer already picked a driver");
        }
        else{
            for(int i=0; i<driver.size(); i++){
                try {
                    hour=Integer.parseInt(cust.get(cust.size()-1).getArrTime());
                    hour=hour/100;

                    minutes=Integer.parseInt(cust.get(cust.size()-1).getArrTime());
                    minutes=minutes%100;

                    int totalCustTime=hour*60+minutes;
                    
                    int EAT=tellTime.getTimeInSec()
                            +getTimeTakenSec(driver.get(i).getCurrLocation(),cust.get(cust.size()-1).getStart())
                            +getTimeTakenSec(cust.get(cust.size()-1).getStart(),cust.get(cust.size()-1).getDestination());
                    
                    int h=EAT/60;
                    int m=EAT-h*60;
                    
                    String EATstr = String.format("%02d%02d", h, m);
                    
                    if(totalCustTime+5>EAT && driver.get(i).getCapacity()>=cust.get(cust.size()-1).getCustCapacity() && driver.get(i).getStatus().equalsIgnoreCase("Available")){
                        driver.get(i).setEAT(EATstr);
                        custChoose.add(driver.get(i));
                    }
                    
                } catch (Exception ex) {
                    Logger.getLogger(PickDriver.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
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
     
    public void addRowToTable(){

        DefaultTableModel modelDriver=(DefaultTableModel)jTable1.getModel();

        Object rowDriver=new Object[4];
        
        for(int i=0; i<custChoose.size(); i++){
            Vector<String> driverName = new Vector<>(Arrays.asList(custChoose.get(i).getName()));
            Vector<Integer> driverCapacity = new Vector<>(Arrays.asList(custChoose.get(i).getCapacity()));           
            Vector<String> EAT = new Vector<>(Arrays.asList(custChoose.get(i).getEAT()));
            Vector<Double> rating = new Vector<>(Arrays.asList(custChoose.get(i).getRating()));
            
            Vector<Object> driverRow = new Vector<Object>();
            
            driverRow.addElement(driverName.get(0));
            driverRow.addElement(driverCapacity.get(0));
            driverRow.addElement(EAT.get(0));
            driverRow.addElement(rating.get(0));

            modelDriver.addRow(driverRow);
        }
        
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Driver = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Driver Name", "Capacity", "EAT", "Rating"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        Driver.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        Driver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DriverActionPerformed(evt);
            }
        });

        jButton1.setText("Select Driver");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Driver, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1))
                        .addGap(150, 150, 150))
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton2)
                .addGap(41, 41, 41)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Driver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DriverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DriverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_DriverActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        int driverIndex= Driver.getSelectedIndex();
        Driver selectedDriver=custChoose.get(driverIndex);
        Customer selectedCustomer=cust.get(cust.size()-1);
        UpdateTask ut= new UpdateTask(selectedDriver,selectedCustomer);
        
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
        new PickDriver().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed
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
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PickDriver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PickDriver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PickDriver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PickDriver.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PickDriver().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Driver;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
