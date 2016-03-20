/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Employee;

import java.util.ArrayList;



/**
 *
 * @author nehal
 */
public class Gadget extends Employee{
    
    private String name;
    private static int count = 0;
    private int id;
    private GadgetType typeOfGadget;
    private Supplier supplier;
    private boolean onRent;
    private boolean owned;
    private Farmer lender;
    private Farmer currentOwner;
    private Farmer borrower;
   
    private String description;
  
    
    public Gadget(){
     count++;
     id=count;
          
    }

    public GadgetType getTypeOfGadget() {
        return typeOfGadget;
    }

    public void setTypeOfGadget(GadgetType typeOfGadget) {
        this.typeOfGadget = typeOfGadget;
    }
    
    
    
    public enum GadgetType{
         SMARTSCARECROW("Smart ScareCrow"),
         WEATHERMONITOR("Weather Monitor"),
         SMARTIRRIGATOR("Smart Irrigator");
         
        
        
//         private int numberOfUsers;
//         private int rating ;
         
         private String status;
         
         private ArrayList<Farmer> gadGetLikers;
         private ArrayList<Farmer> numOfUsers;
       
        private String value;
        private GadgetType(String value){
            this.value = value;
            gadGetLikers = new ArrayList<>();
            numOfUsers = new ArrayList<>();
        }

        public String getValue() {
            return value;
        }

        public ArrayList<Farmer> getNumOfUsers() {
            return numOfUsers;
        }

//        public int getNumberOfUsers() {
//            return numberOfUsers;
//        }
//
//        public void setNumberOfUsers(int numberOfUsers) {
//            this.numberOfUsers = numberOfUsers;
//        }
//
//        public int getRating() {
//            return rating;
//        }
//
//        public void setRating(int rating) {
//            this.rating = rating;
//        }
        public void setNumOfUsers(ArrayList<Farmer> numOfUsers) {
            this.numOfUsers = numOfUsers;
        }

        public ArrayList<Farmer> getGadGetLikers() {
            return gadGetLikers;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

       
        
        
        
        

        @Override
        public String toString() {
            return value;
        }
    }

   


    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

 


  

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Farmer getLender() {
        return lender;
    }

    public void setLender(Farmer lender) {
        this.lender = lender;
    }

    public Farmer getBorrower() {
        return borrower;
    }

    public void setBorrower(Farmer borrower) {
        this.borrower = borrower;
    }

    public Farmer getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(Farmer currentOwner) {
        this.currentOwner = currentOwner;
    }

    
    
    public boolean isOnRent() {
        return onRent;
    }

    public void setOnRent(boolean onRent) {
        this.onRent = onRent;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

   
   
  
    
    

    @Override
    public String toString() {
        return name;
    }
    
    
    
}
