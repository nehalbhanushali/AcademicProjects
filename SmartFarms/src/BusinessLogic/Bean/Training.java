/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Bean;

import BusinessLogic.Employee.Farmer;
import java.util.ArrayList;

/**
 *
 * @author nehal
 */
public class Training {
    
    private static int count = 0;
    private static int trainingID;
    private ArrayList<Farmer> listOfEnrolledFarmers;
    private String topic;
    private String referenceDocumentPath;
    private int numberOfDownloads;
    
    
    
    public Training(){
        
        listOfEnrolledFarmers = new ArrayList<>();
        count++;
        trainingID= count;
    }

    public static int getTrainingID() {
        return trainingID;
    }

    public static void setTrainingID(int trainingID) {
        Training.trainingID = trainingID;
    }

    public ArrayList<Farmer> getListOfEnrolledFarmers() {
        return listOfEnrolledFarmers;
    }

    public void setListOfEnrolledFarmers(ArrayList<Farmer> listOfEnrolledFarmers) {
        this.listOfEnrolledFarmers = listOfEnrolledFarmers;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    } 

    public String getReferenceDocumentPath() {
        return referenceDocumentPath;
    }

    public void setReferenceDocumentPath(String referenceDocumentPath) {
        this.referenceDocumentPath = referenceDocumentPath;
    }

    public int getNumberOfDownloads() {
        return numberOfDownloads;
    }

    public void setNumberOfDownloads(int numberOfDownloads) {
        this.numberOfDownloads = numberOfDownloads;
    }
    
    
    
    @Override 
    public String toString(){
        return topic;
    }
    
 
}
