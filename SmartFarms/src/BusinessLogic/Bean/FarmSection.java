/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Bean;

import java.util.Date;

/**
 *
 * @author nehal
 */
public class FarmSection {
     public static final int areaSizeInSquareFeet = 100;
     private int areaIndex;
     private static int count = 0;
     private Crop crop;
     
     private SectionStatusHistory sectionStatusHistory;

     public FarmSection(){
         
        // crop = new Crop();
         areaIndex = count;
         count++;
         sectionStatusHistory = new SectionStatusHistory();

     }

    public int getAreaIndex() {
        return areaIndex;
    }

    public void setAreaIndex(int areaIndex) {
        this.areaIndex = areaIndex;
    }

    public Crop getCrop() {
        return crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

   

//    public String getAlertStatus() {
//        return alertStatus;
//    }
//
//    public void setAlertStatus(String alertStatus) {
//        this.alertStatus = alertStatus;
//    }

    public SectionStatusHistory getSectionStatusHistory() {
        return sectionStatusHistory;
    }

    public void setSectionStatusHistory(SectionStatusHistory sectionStatusHistory) {
        this.sectionStatusHistory = sectionStatusHistory;
    }

   
     
    
    @Override
    public String toString(){
        
        return String.valueOf(areaIndex);
    } 
    
}
