/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Bean;




import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author nehal
 */
public class Farm {

    private int farmID;
    
    private String soilType;

    private ArrayList<FarmSection> farmSectionList;
    
    private boolean isHarmFulPestPresent;// random boolean
    
    private String usage;

   private ClimaticFactorsHistory farmClimaticFactorHistory;
    
    public Farm(){
        
     this.usage = "Cultivated";
     farmSectionList = new ArrayList<>();
     farmClimaticFactorHistory = new ClimaticFactorsHistory();
     
    }

  public SectionStatus sowCrop(Crop c, FarmSection f){
        
    
          int size =  f.getSectionStatusHistory().getSectionStatusList().size();
          SectionStatus latestStatus = f.getSectionStatusHistory().getSectionStatusList().get(size-1);
              
             SectionStatus ss = new SectionStatus();
             ss.setIsSown(true);
           //  f.setIsSown(true);
             f.setCrop(c);
             ss.setBirdOrAnimalPresent(latestStatus.isBirdOrAnimalPresent());
             ss.setIsPesticideApplied(latestStatus.isIsPesticideApplied());
             ss.setDate(new Date());
             ss.setMoistureLevel(latestStatus.getMoistureLevel());
             f.getSectionStatusHistory().getSectionStatusList().add(ss);
             
     
      return ss;
         
     }
  
  public void irrigateFarm(){
      
      for(FarmSection f: farmSectionList){
         int size =  f.getSectionStatusHistory().getSectionStatusList().size();
          SectionStatus latestStatus = f.getSectionStatusHistory().getSectionStatusList().get(size-1);
          
          if(latestStatus.isIsSown()){
              
              
            if(latestStatus.getMoistureLevel()< f.getCrop().getMoistureThreshold()){
                
             SectionStatus newStatus = new SectionStatus();
                
             newStatus.setMoistureLevel(f.getCrop().getMoistureThreshold());
             
             newStatus.setIsSown(latestStatus.isIsSown());
             newStatus.setIsPesticideApplied(latestStatus.isIsPesticideApplied());
             newStatus.setDate(new Date());
             newStatus.setCf(latestStatus.getCf());
             f.getSectionStatusHistory().getSectionStatusList().add(newStatus);
             
            }
            
            
          } 
          
      }
      
  }
 
  
  public void harvestCrop(){
       for(FarmSection f: farmSectionList){
           f.setCrop(null);
        //   f.setIsSown(false); //set another status
           
       }
      
  }

  public SectionStatus addSection(){// ## check climate and add
      FarmSection fs = new FarmSection();
      Random r = new Random();
      SectionStatus firstStatus = new SectionStatus();
      
      firstStatus.setMoistureLevel(20);
      firstStatus.setIsSown(false);
      firstStatus.setIsPesticideApplied(false);
      firstStatus.setDate(new Date());
      firstStatus.setBirdOrAnimalPresent(r.nextBoolean());
      farmSectionList.add(fs);
      fs.getSectionStatusHistory().getSectionStatusList().add(firstStatus);
     
      return firstStatus;
      
  }
    public ArrayList<FarmSection> getFarmSectionList() {
        return farmSectionList;
    }

    public void setFarmsectionList(ArrayList<FarmSection> farmSectionList) {
        this.farmSectionList = farmSectionList;
    }


    public int getFarmID() {
        return farmID;
    }

    public void setFarmID(int farmID) {
        this.farmID = farmID;
    }



    public boolean isIsHarmFulPestPresent() {
        return isHarmFulPestPresent;
    }

    public void setIsHarmFulPestPresent(boolean isHarmFulPestPresent) {
        this.isHarmFulPestPresent = isHarmFulPestPresent;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public ClimaticFactorsHistory getFarmClimaticFactorHistory() {
        return farmClimaticFactorHistory;
    }

    public void setFarmClimaticFactorHistory(ClimaticFactorsHistory farmClimaticFactorHistory) {
        this.farmClimaticFactorHistory = farmClimaticFactorHistory;
    }

 
   
    

   

  
    
    
    
    
    
}
