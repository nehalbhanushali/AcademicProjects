/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Bean;


import java.util.ArrayList;

/**
 *
 * @author nehal
 */
public class TrainingCatalog {
    
    private ArrayList<Training> trainingList;
    
    public TrainingCatalog(){
        
        trainingList = new ArrayList<>();
    }

    public ArrayList<Training> getTrainingList() {
        return trainingList;
    }

    public void setTrainingList(ArrayList<Training> trainingList) {
        this.trainingList = trainingList;
    }
    
    public Training addTraining(){
        
       Training t = new Training();
        
        trainingList.add(t);
        return t;
    }
}
