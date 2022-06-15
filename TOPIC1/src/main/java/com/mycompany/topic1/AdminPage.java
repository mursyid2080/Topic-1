/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.topic1;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Timer;
//import java.util.TimerTask;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.Timer;

/**
 *
 * @author Lenovo
 */
public class AdminPage extends javax.swing.JFrame {
    
    ArrayList<Customer> cust;
    ArrayList<Driver> driver;
    
    /**
     * Creates new form AdminPage
     */
    public AdminPage() {
        initComponents();
        cust=new ArrayList<>();
        driver=new ArrayList<>();
        

        
        
        populateArrayList();//add to timertask? cannot use timertask; not clearing fast enough

        addRowToTable();
        
            

    }

   
    public void clearArrayList(){
        cust.clear();
        driver.clear();
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
//            JOptionPane.showMessageDialog(null,e.getMessage());
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
//            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
    
    public void addRowToTable(){
        DefaultTableModel modelCustomer=(DefaultTableModel)jTable1.getModel();
        DefaultTableModel modelDriver=(DefaultTableModel)jTable2.getModel();
        Object rowCustomer=new Object[6];
        Object rowDriver=new Object[5];
        
        for(int i=0; i<cust.size(); i++){
            Vector<String> custName = new Vector<>(Arrays.asList(cust.get(i).getName()));
            Vector<String> custStatus = new Vector<>(Arrays.asList(cust.get(i).getStatus()));
            Vector<String> EAT = new Vector<>(Arrays.asList(cust.get(i).getArrTime()));
            Vector<Integer> custCapacity = new Vector<>(Arrays.asList(cust.get(i).getCustCapacity()));           
            Vector<String> custPickup = new Vector<>(Arrays.asList(cust.get(i).getStart()));
            Vector<String> custDest = new Vector<>(Arrays.asList(cust.get(i).getDestination()));
            
            Vector<Object> custRow = new Vector<Object>();
            
            custRow.addElement(custName.get(0));
            custRow.addElement(custStatus.get(0));
            custRow.addElement(EAT.get(0));
            custRow.addElement(custCapacity.get(0));
            custRow.addElement(custPickup.get(0));
            custRow.addElement(custDest.get(0));
            
            modelCustomer.addRow(custRow);  
        }
        for(int i=0; i<driver.size(); i++){
            Vector<String> driverName = new Vector<>(Arrays.asList(driver.get(i).getName()));
            Vector<String> driverStatus = new Vector<>(Arrays.asList(driver.get(i).getStatus()));
            Vector<Integer> driverCapacity = new Vector<>(Arrays.asList(driver.get(i).getCapacity()));           
            Vector<String> driverLoc = new Vector<>(Arrays.asList(driver.get(i).getCurrLocation()));
            Vector<String> cust = new Vector<>(Arrays.asList(driver.get(i).getCust()));
            
            Vector<Object> driverRow = new Vector<Object>();
            
            driverRow.addElement(driverName.get(0));
            driverRow.addElement(driverStatus.get(0));
            driverRow.addElement(driverCapacity.get(0));
            driverRow.addElement(driverLoc.get(0));
            driverRow.addElement(cust.get(0));

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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Status", "EAT", "Capacity", "Pickup Location", "Destination"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Status", "Capacity", "Location", "Customer"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jLabel1.setText("Driver");

        jLabel2.setText("Customer");

        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(273, 273, 273)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(262, 262, 262)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel2))
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        new AdminPage().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
