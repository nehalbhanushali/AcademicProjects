/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Employee;

import BusinessLogic.Bean.TrainingCatalog;

/**
 *
 * @author nehal
 */
public class Expert extends Employee{
    
    private TrainingCatalog experTrainingCatalog;
    
    public Expert(){
        
        experTrainingCatalog = new TrainingCatalog();
    }

    public TrainingCatalog getExperTrainingCatalog() {
        return experTrainingCatalog;
    }

    public void setExperTrainingCatalog(TrainingCatalog experTrainingCatalog) {
        this.experTrainingCatalog = experTrainingCatalog;
    }
    
    
}
