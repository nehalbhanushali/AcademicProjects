/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Bean;

import BusinessLogic.Employee.Farmer;
import BusinessLogic.Employee.Supplier;
import java.util.ArrayList;

/**
 *
 * @author nehal
 */
public class Crop {
    
    private String cropName;
    private String cropID;
    private String stage;
    private int developmentTimeInDays;
    private int moistureThreshold;
    private String cropType;
    private Supplier supplier;
    private Farmer sharingFarmer;
    private Farmer borrower;
    private ArrayList<Farmer> seedLikers;
     private ArrayList<Farmer> seedUsers;
    
    
    
    public Crop(){
        
        seedLikers = new ArrayList<>();
        seedUsers = new ArrayList<>();
    }
    

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCropID() {
        return cropID;
    }

    public void setCropID(String cropID) {
        this.cropID = cropID;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public int getDevelopmentTimeInDays() {
        return developmentTimeInDays;
    }

    public void setDevelopmentTimeInDays(int developmentTimeInDays) {
        this.developmentTimeInDays = developmentTimeInDays;
    }

    public int getMoistureThreshold() {
        return moistureThreshold;
    }

    public void setMoistureThreshold(int moistureThreshold) {
        this.moistureThreshold = moistureThreshold;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Farmer getSharingFarmer() {
        return sharingFarmer;
    }

    public void setSharingFarmer(Farmer sharingFarmer) {
        this.sharingFarmer = sharingFarmer;
    }

   

    public Farmer getBorrower() {
        return borrower;
    }

    public void setBorrower(Farmer borrower) {
        this.borrower = borrower;
    }

    public ArrayList<Farmer> getSeedLikers() {
        return seedLikers;
    }

    public void setSeedLikers(ArrayList<Farmer> seedLikers) {
        this.seedLikers = seedLikers;
    }

    public ArrayList<Farmer> getSeedUsers() {
        return seedUsers;
    }

    public void setSeedUsers(ArrayList<Farmer> seedUsers) {
        this.seedUsers = seedUsers;
    }
    
    
    
   
    
    
    @Override
    public String toString(){
        
        return cropName;
    }
    
    
    
    
}
