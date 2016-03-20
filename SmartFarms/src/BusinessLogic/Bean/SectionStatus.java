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
public class SectionStatus {
    
      private boolean isSown; // use switch
     private boolean isPesticideApplied; // use switch
     private int moistureLevel; // use range slider
//     private String alertStatus;
     private Date date;
     private boolean birdOrAnimalPresent;
     private ClimaticFactors cf;
     
      public boolean isIsSown() {
        return isSown;
    }

    public void setIsSown(boolean isSown) {
        this.isSown = isSown;
    }

    public boolean isIsPesticideApplied() {
        return isPesticideApplied;
    }

    public void setIsPesticideApplied(boolean isPesticideApplied) {
        this.isPesticideApplied = isPesticideApplied;
    }

    public int getMoistureLevel() {
        return moistureLevel;
    }

    public void setMoistureLevel(int moistureLevel) {
        this.moistureLevel = moistureLevel;
    }
    
     public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isBirdOrAnimalPresent() {
        return birdOrAnimalPresent;
    }

    public void setBirdOrAnimalPresent(boolean birdOrAnimalPresent) {
        this.birdOrAnimalPresent = birdOrAnimalPresent;
    }

    public ClimaticFactors getCf() {
        return cf;
    }

    public void setCf(ClimaticFactors cf) {
        this.cf = cf;
    }
    
    
    
}
