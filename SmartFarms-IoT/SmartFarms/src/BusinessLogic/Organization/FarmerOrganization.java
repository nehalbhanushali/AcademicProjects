/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Organization;

import BusinessLogic.Bean.Crop;
import BusinessLogic.Bean.CropCatalog;
import BusinessLogic.Employee.Farmer;
import BusinessLogic.Employee.Gadget;
import BusinessLogic.Employee.GadgetCatalog;
import BusinessLogic.Role.FarmerRole;
import BusinessLogic.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author nehal
 */
public class FarmerOrganization extends Organization{

    private CropCatalog sharedCropCatalog;
    private GadgetCatalog lentGadgetCatalog;
    
    
    
   public FarmerOrganization() {
        super(Type.FARMER.getValue());
        sharedCropCatalog = new CropCatalog();
        lentGadgetCatalog = new GadgetCatalog();
        
    }

    public CropCatalog getSharedCropCatalog() {
        return sharedCropCatalog;
    }

    public void setSharedCropCatalog(CropCatalog sharedCropCatalog) {
        this.sharedCropCatalog = sharedCropCatalog;
    }

    public GadgetCatalog getLentGadgetCatalog() {
        return lentGadgetCatalog;
    }

    public void setLentGadgetCatalog(GadgetCatalog lentGadgetCatalog) {
        this.lentGadgetCatalog = lentGadgetCatalog;
    }
   
   public Gadget lendGadget(Gadget gadget){ //## to do
       
       gadget.setOnRent(true);
       lentGadgetCatalog.getGadgetList().add(gadget);
       return gadget;
   }
   
   public Crop shareCrop(Farmer sharingFarmer, Crop sharedSeed){ //## to do
       
       Crop borrowedCrop = new Crop();
       borrowedCrop.setCropName(sharedSeed.getCropName());
       borrowedCrop.setCropType(sharedSeed.getCropType());
       borrowedCrop.setDevelopmentTimeInDays(sharedSeed.getDevelopmentTimeInDays());
       borrowedCrop.setMoistureThreshold(sharedSeed.getMoistureThreshold());
       borrowedCrop.setSupplier(sharedSeed.getSupplier());
       borrowedCrop.setStage("UnSown"); // seed
       borrowedCrop.setSharingFarmer(sharingFarmer);
       
       
       sharedCropCatalog.addCrop(borrowedCrop);
       
       return borrowedCrop;
   }
   
   
   public Gadget returnGadget(Gadget gadgetToReturn ){
       
      gadgetToReturn.setOnRent(true);
      gadgetToReturn.setCurrentOwner(gadgetToReturn.getLender());
      gadgetToReturn.setBorrower(null);
      
       
      return gadgetToReturn ;
   }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new FarmerRole());
        return roles;
    }
    
}
