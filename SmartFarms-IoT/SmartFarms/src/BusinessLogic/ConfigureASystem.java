package BusinessLogic;

import BusinessLogic.Bean.ClimaticFactors;
import BusinessLogic.Employee.Admin;
import BusinessLogic.Employee.Employee;
import BusinessLogic.Employee.Farmer;
import BusinessLogic.Employee.Gadget;
import BusinessLogic.Employee.Gadget.GadgetType;
import BusinessLogic.Employee.GadgetCatalog;
import BusinessLogic.Employee.Supplier;
import BusinessLogic.Enterprise.Enterprise;
import BusinessLogic.Network.Network;
import BusinessLogic.Bean.Crop;
import BusinessLogic.Bean.CropCatalog;
import BusinessLogic.Employee.Expert;
import BusinessLogic.Organization.Organization;
import BusinessLogic.Role.AdminRole;
import BusinessLogic.Role.ExpertRole;
import BusinessLogic.Role.FarmerRole;
import BusinessLogic.Role.SupplierRole;
import BusinessLogic.Role.SystemAdminRole;
import BusinessLogic.UserAccount.UserAccount;
import Constants.ReadFile;
import static java.time.Clock.system;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author nehal
 */
public class ConfigureASystem {
    
    public static EcoSystem configure(){
        
        EcoSystem system = EcoSystem.getInstance();
        
          Employee employee = new Admin();
         employee.setName("Ramesh");       
         system.getEmployeeDirectory().createEmployee(employee);
        

         
         UserAccount ua = system.getUserAccountDirectory().createUserAccount("sysadmin", "sysadmin", employee, new SystemAdminRole());
        
        
        //Create a network
        
         
          String[] networks = {"East Coast","West Coast","South East","North East","North West"};
         
          for(int i = 0;i<networks.length;i++){
              
             Network network = system.createAndAddNetwork(); 
             
             setClimateToNetwork(network);

             network.setName(networks[i]);
          }

          for(Network n: system.getNetworkList()){
            
            //create an enterprise 
            for( Enterprise.EnterpriseType eType : Enterprise.EnterpriseType.values()){
                String eName = n.getName()+" "+eType.getValue();
               // System.err.println("ent name " +n.getName()+" "+eType.getValue());
            Enterprise enterprise = n.getEnterpriseDirectory().createAndAddEnterprise(eName, eType); 
            //initialize some organizations 
            if(enterprise.getEnterpriseType().getValue().equals("Agriculture Enterprise")){
              for(Organization.Type oType : Organization.Type.values()){
                 enterprise.getOrganizationDirectory().createOrganization(oType);   
              }   
            }
           
            
                
            }
        }

        //have some employees 
           
           
           
        //create user account
       String[] n1Farmers = {"Jeevan","Kisan"};
          
       String[] n1FarUserIDAndPassword = {"jee","kis"};
          
       String[] n1Suppliers = {"Supreme"};
 
       String[] n1SuppUserIDAndPassword = {"sup"};
       
        String[] n1Experts = {"Nehal","Rydham"};
 
       String[] n1ExpUserIDAndPassword = {"neh","ryd"};

            //--------soil-------------------     
        ArrayList<String> soilType = new ArrayList<>();
 
        String[] soils = {"Sandy","Loam","Clay"};
          
          for(int i = 0;i<soils.length;i++){
             
              soilType.add(soils[i]);
          }
     //--------soil/-------------------  
          
          //------------gadget------------
          GadgetCatalog gadgetCatalog = new GadgetCatalog();
                   for (GadgetType gadgetType : Gadget.GadgetType.values()){
   Gadget g = new Gadget();
                        g.setName(gadgetType.getValue());
                       g.setTypeOfGadget(gadgetType);
                 Gadget gad = gadgetCatalog.createGadget(g);  
  

    }
       
          
          //-------------/gadget------------
          
          
          //-----------------crop---------------
         final String FARMERFILEPATH = System.getProperty("user.dir")+"\\src\\Constants\\FarmerDirectory.txt"; //  // path to the data store
          
         ReadFile rf = new ReadFile(FARMERFILEPATH);
         ArrayList<String> textFile = new ArrayList<>();
        

         try{
          textFile =  rf.readFileContents(); 

          String cropNames = textFile.get(0);
          String[] crops = cropNames.split("\\|");
          
          String devpDays = textFile.get(1);
          String[] days = devpDays.split("\\|");
          
             String moistureThreshold = textFile.get(2);
          String[] mt = moistureThreshold.split("\\|");
          
             CropCatalog cropCatalog = new CropCatalog();
           for (int j =0; j<crops.length;j++){
          Crop crop = new Crop();
          cropCatalog.addCrop(crop);
          crop.setCropName(crops[j]);
          crop.setDevelopmentTimeInDays(Integer.parseInt(days[j]));
          crop.setMoistureThreshold(Integer.parseInt(mt[j]));
           }
           
          
          
          //---------------- crop /---------------
          
       
for(Enterprise e : system.getNetworkList().get(0).getEnterpriseDirectory().getEnterpriseList()){
    if(e.getEnterpriseType().getValue().equals("Agriculture Enterprise")){
  
        Employee entAdmin = new Admin();
       entAdmin.setName("Rydham");
       e.getEmployeeDirectory().createEmployee(entAdmin);

         UserAccount adminUA = e.getUserAccountDirectory().createUserAccount("entadmin", "entadmin", entAdmin, new AdminRole());
        
      
      for( Organization o:e.getOrganizationDirectory().getOrganizationList() ){
      if(o.getName().equals("Farmer Organization")){
     for (int i =0; i<n1Farmers.length;i++){
              
          Employee farmer = new Farmer();
           farmer.setName(n1Farmers[i]);
          // Collections.shuffle(soilType);
           ((Farmer) farmer).getFarm().setSoilType(soilType.get(0));
           o.getEmployeeDirectory().createEmployee(farmer);
           o.getUserAccountDirectory().createUserAccount(n1FarUserIDAndPassword[i],n1FarUserIDAndPassword[i], farmer, new FarmerRole());
    
          }
      }
      
           if(o.getName().equals("Expert Organization")){
     for (int i =0; i<n1Experts.length;i++){
              
          Employee expert = new Expert();
           expert.setName(n1Experts[i]);
           o.getEmployeeDirectory().createEmployee(expert);
           o.getUserAccountDirectory().createUserAccount(n1ExpUserIDAndPassword[i],n1ExpUserIDAndPassword[i], expert, new ExpertRole());
    
          }
      }
      
       if(o.getName().equals("Supplier Organization")){
     for (int i =0; i<n1Suppliers.length;i++){
              
          Employee supplier = new Supplier();
           supplier.setName(n1Suppliers[i]);
          // Collections.shuffle(soilType);
           
          o.getEmployeeDirectory().createEmployee(supplier);
          ((Supplier) supplier).setMasterGadgetCatalog(gadgetCatalog);
          
        for(Gadget g :   ((Supplier) supplier).getMasterGadgetCatalog().getGadgetList()){
            
            g.setSupplier( ((Supplier) supplier));
        }
          
          ((Supplier) supplier).setMasterCropCatalog(cropCatalog);
          
        for(Crop c :   ((Supplier) supplier).getMasterCropCatalog().getCropList()){
            
            c.setSupplier( ((Supplier) supplier));
        }  
           o.getUserAccountDirectory().createUserAccount(n1SuppUserIDAndPassword[i],n1SuppUserIDAndPassword[i], supplier, new SupplierRole());

          }
      }
    }  
    }

}     
       
   } catch(Exception e){
             
           
         }      

        return system;
    }
    
    
    public static void setClimateToNetwork(Network n){
        
                
         final String NETWORKLOCATION = System.getProperty("user.dir")+"\\src\\Constants\\NetworkAndEnterpriseAdminDirectory.txt"; //  // path to the data store
          
         ReadFile rf = new ReadFile(NETWORKLOCATION);
         ArrayList<String> textFile = new ArrayList<>();
         
            try{
          textFile =  rf.readFileContents(); 

          String skyString = textFile.get(1);
          String[] skies = skyString.split("\\|");
          
       
          
             ArrayList<String> skyList = new ArrayList<>();
           for (int j =0; j<skies.length;j++){
         
          skyList.add(skies[j]);
           }
           
 

       
        for(int i = 0;i<3;i++){
            
            ClimaticFactors cf = new ClimaticFactors();

         int temperature =randInt(0, 15);
         int humidity = randInt(30, 55);
         int windSpeed = randInt(10, 15);
         
            Collections.shuffle(skyList);
          cf.setSky(skyList.get(0));
          cf.setHumidity(humidity);
          cf.setTemperature(temperature);
          cf.setWindSpeed(windSpeed);
          
          n.getClimaticFactorHistory().getClimaticFactorList().add(cf);
        
        
        }
       

        
        
         }catch(Exception e){
                
                JOptionPane.showMessageDialog(null, "Constants file missing");
            }
    }
    
    
     public static int randInt(int min, int max) {

    // Usually this can be a field rather than a method variable
    Random rand = new Random();

    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
    int randomNum = rand.nextInt((max - min) + 1) + min;

    return randomNum;
}
    
}
