/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Employee;

import BusinessLogic.Bean.CropCatalog;

/**
 *
 * @author nehal
 */
public class Supplier extends Employee{
    
     private GadgetCatalog masterGadgetCatalog;
   private CropCatalog masterCropCatalog;
   
   
   public Supplier(){
       
       masterCropCatalog = new CropCatalog();
       masterGadgetCatalog = new GadgetCatalog();
   }

    public GadgetCatalog getMasterGadgetCatalog() {
        return masterGadgetCatalog;
    }

    public void setMasterGadgetCatalog(GadgetCatalog masterGadgetCatalog) {
        this.masterGadgetCatalog = masterGadgetCatalog;
    }

    public CropCatalog getMasterCropCatalog() {
        return masterCropCatalog;
    }

    public void setMasterCropCatalog(CropCatalog masterCropCatalog) {
        this.masterCropCatalog = masterCropCatalog;
    }
   
   
   
   
   
    
}
