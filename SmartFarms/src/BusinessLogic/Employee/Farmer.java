/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Employee;

import BusinessLogic.Bean.CropCatalog;
import BusinessLogic.Bean.Farm;
import BusinessLogic.Bean.TrainingCatalog;

/**
 *
 * @author nehal
 */
public class Farmer extends Employee {
    
    private GadgetCatalog myGadgetCatalog;
    
    private CropCatalog myCropCatalog;
    
    private TrainingCatalog myAttendedTrainingCatalog;
    
    private Farm farm;
    
    public Farmer(){
        myCropCatalog = new CropCatalog();
        myGadgetCatalog = new GadgetCatalog();
        myAttendedTrainingCatalog = new TrainingCatalog();
        farm = new Farm();
    }
    
     public GadgetCatalog getMyGadgetCatalog() {
        return myGadgetCatalog;
    }

    public void setMyGadgetCatalog(GadgetCatalog myGadgetCatalog) {
        this.myGadgetCatalog = myGadgetCatalog;
    }

    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public CropCatalog getMyCropCatalog() {
        return myCropCatalog;
    }

    public void setMyCropCatalog(CropCatalog myCropCatalog) {
        this.myCropCatalog = myCropCatalog;
    }

    public TrainingCatalog getMyAttendedTrainingCatalog() {
        return myAttendedTrainingCatalog;
    }

    public void setMyAttendedTrainingCatalog(TrainingCatalog myAttendedTrainingCatalog) {
        this.myAttendedTrainingCatalog = myAttendedTrainingCatalog;
    }
    
    
    
    
    
}
