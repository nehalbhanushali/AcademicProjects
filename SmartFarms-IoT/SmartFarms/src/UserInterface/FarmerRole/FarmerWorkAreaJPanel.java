/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface.FarmerRole;

import BusinessLogic.Bean.ClimaticFactors;
import BusinessLogic.EcoSystem;
import BusinessLogic.Employee.Employee;
import BusinessLogic.Employee.Farmer;
import BusinessLogic.Employee.Gadget;
import BusinessLogic.Employee.Supplier;
import BusinessLogic.Enterprise.Enterprise;
import BusinessLogic.Bean.Crop;
import BusinessLogic.Bean.Farm;
import BusinessLogic.Bean.FarmSection;
import BusinessLogic.Bean.SectionStatus;
import BusinessLogic.Employee.Expert;
import BusinessLogic.Network.Network;
import BusinessLogic.Organization.ExpertOrganization;
import BusinessLogic.Organization.FarmerOrganization;
import BusinessLogic.Organization.Organization;
import BusinessLogic.Bean.Training;
import BusinessLogic.UserAccount.UserAccount;
import BusinessLogic.WorkQueue.Response;
import BusinessLogic.WorkQueue.WorkRequest;
import Util.Utility;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
//import java.util.Timer;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author nehal
 */
public class FarmerWorkAreaJPanel extends javax.swing.JPanel {

    /**
     * Creates new form FarmerWorkAreaJPanel
     */
    private JPanel userProcessContainer;
    private UserAccount account;
    private FarmerOrganization farmerOrganization;
    private EcoSystem ecoSystem;
    private Network network;
    private Employee employee;
    private Enterprise enterprise;
    private Farm farm;
    private ClimaticFactors currentCF;  
    // private JTable farmJTable ;  
// Calendar calendar ;
// 
// Date d1 ;
    public FarmerWorkAreaJPanel(JPanel userProcessContainer, UserAccount account, FarmerOrganization farmerOrganization,Enterprise enterprise,Network network, EcoSystem ecoSystem) {
        initComponents();

        this.userProcessContainer = userProcessContainer;
        this.ecoSystem = ecoSystem;
        this.enterprise = enterprise;
        this.account = account;
        this.farmerOrganization = farmerOrganization;
        this.network = network;
      
        this.employee = account.getEmployee();
        this.farm = ((Farmer) employee).getFarm();
//       d1 = new Date();
//        calendar = Calendar.getInstance();
//calendar.setTime(new Date());
//int hours = calendar.get(Calendar.HOUR_OF_DAY);
//int minutes = calendar.get(Calendar.MINUTE);
//int seconds = calendar.get(Calendar.SECOND);
        populateSupplierComboboxForCrop();//## remove this //## why ??
      
        networkJLabel.setText(network.getName());
        irrigationJSlider.setMinimum(10);
        
        irrigationJSlider.setMaximum(20);
         String welcomeMsg = "Hello "+employee.getName();
        farmerNameJlabel.setText(welcomeMsg);

    
     populateMyGadgetsTable();
     
      gadgetSupplierJPanel.setVisible(false);
     showGadgetDetailsSection1(false);
     showGadgetDetailsSection2(false);
     
    
returnGadgetJButton.setVisible(false);
getBackJButton.setVisible(false);
     
 // these things happen to farm irrespective of use of technology  
   currentCF = measureClimateFactors(); 
   checkBirdOrAnimalPresence();
 //----------------------------------------------------------------   

    setFarmMoistureLevel();   // section status is not added to history unless smartirrigator is on
     refreshInbox();
     showFarms();// populateFarmTable
      populateCropCombo();
     populateMySeedTable();
     climateJPanel.setVisible(false);
     climateInfoJLabel.setVisible(true);
     
     getGadgetsGoing(); // overrides the above statement
   
     setFarmUsageToggle();
     
  //   addImagesToTabs(); // ## to do $$$$$$$$$$
    //eventListener for table row click
     
         
    GadgetsAvailableTable.addMouseListener(new java.awt.event.MouseAdapter() {
        
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
  
        int row = GadgetsAvailableTable.rowAtPoint(evt.getPoint());
        int col = GadgetsAvailableTable.columnAtPoint(evt.getPoint());
        
        if (row >= 0 && col >= 0) {
          
            
            viewTheDetails();

        }
    }
}); 
    
    
    
            
     messageDetailsArea.setVisible(false);
    inboxJTable.addMouseListener(new java.awt.event.MouseAdapter() {
        
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
  
        int row = inboxJTable.rowAtPoint(evt.getPoint());
        int col = inboxJTable.columnAtPoint(evt.getPoint());
        
        if (row >= 0 && col >= 0) {
          
             messageDetailsArea.setVisible(true);
            viewMessageDetails();

        }
    }
}); 
    
     
     
    gadgetTable.addMouseListener(new java.awt.event.MouseAdapter() {
        
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
  
        int row = gadgetTable.rowAtPoint(evt.getPoint());
        int col = gadgetTable.columnAtPoint(evt.getPoint());
        
        if (row >= 0 && col >= 0) {
            String g = (String)gadgetTable.getValueAt(row, 1); // ## change to check obj value
            
            Gadget gadgetAvailable = (Gadget)gadgetTable.getValueAt(row, 0);
            
            Utility u = new Utility();
            ImageIcon start = u.createImageIcon("images/start32.png");
            ImageIcon stop = u.createImageIcon("images/stop32.png");
             ImageIcon pause = u.createImageIcon("images/pause32.png"); 
            if(((Farmer) employee) == gadgetAvailable.getCurrentOwner()){
                   startStopJButon.setEnabled(true);
                   
                   getBackJButton.setVisible(false);
                  
             
                if(g.equals("Stopped")){
              
              startStopJButon.setText("Start");
              startStopJButon.setIcon(start);
               }else if(g.equals("Started")){
              startStopJButon.setText("Stop"); 
                startStopJButon.setIcon(stop);
                 }else{
               startStopJButon.setText("Start/Stop");
               
                }
                
                if(gadgetAvailable.getLender() != ((Farmer)employee)){
               
                returnGadgetJButton.setVisible(true);
                }  else{
                   returnGadgetJButton.setVisible(false);  
                }
                
                if(gadgetAvailable.isOnRent()){
                    
                    getBackJButton.setVisible(true);
                }// add else if req
                
       
            }else{
                
                startStopJButon.setEnabled(false);
                getBackJButton.setVisible(true);
                returnGadgetJButton.setVisible(false);    
            }
            
            
           viewMyGadgetDetails();   
           
           

        }
    }
});
    ////////////////////inventory////////////////////////
    
    
     showSeedDetailsSection1(false);
     showSeedDetailsSection2(false);
    
    seedSupplierPanel.setVisible(false);
    
     mySeedTable.addMouseListener(new java.awt.event.MouseAdapter() {
        
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
  
        int row = mySeedTable.rowAtPoint(evt.getPoint());
        int col = mySeedTable.columnAtPoint(evt.getPoint());
        
        if (row >= 0 && col >= 0) {
          
            
           viewMySeedDetails();

        }
    }
}); 

                seedJTable.addMouseListener(new java.awt.event.MouseAdapter() {
        
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
  
        int row = seedJTable.rowAtPoint(evt.getPoint());
        int col = seedJTable.columnAtPoint(evt.getPoint());
        
        if (row >= 0 && col >= 0) {
          
            
            viewMarketSeedDetails();

        }
    }
}); 
    
    
    //////////////---------------inventory-------------//////
    
    
    ////////////////   training ///////////////////////////
    
    populateDiscussionForum(true);
    populateExpertsComboBox();
    
     //eventListener for table row click
    queryResponseJTable.addMouseListener(new java.awt.event.MouseAdapter() {
        
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
  
        int row = queryResponseJTable.rowAtPoint(evt.getPoint());
        int col = queryResponseJTable.columnAtPoint(evt.getPoint());
        
        if (row >= 0 && col >= 0) {
            WorkRequest discussion = (WorkRequest)queryResponseJTable.getValueAt(row, 0); // ## change to check obj value
            
        
            if(discussion.getQueryResponseDirectory().getResponseList().isEmpty()){
               JOptionPane.showMessageDialog(null, "No discussion has happened on this yet");
               forumJTextArea.setText("");
            }
            else{
                
                  String forum = discussion.getRequestTitle()+"\n\n";
                  
               
                  
               for(Response r : discussion.getQueryResponseDirectory().getResponseList()){
                   
                forum+= "Expert : "+r.getResponder().getName()+" : \n Suggestion : "+r.getReply()+"\n\n";
                   
               }
               
              forumJTextArea.setText(forum);
            }
            
           

        }
    }
});
    
    ////-----------training ---------------------------//////////////
      
    }
    ///////////////////general//////////////////////////
    
    public void refreshInbox(){
    for(WorkRequest history : account.getWorkQueue().getWorkRequestList())
      {
           account.getWorkQueue().getOldRequestList().add(history);
           
       } 
 //   account.getWorkQueue().setOldRequestList(account.getWorkQueue().getWorkRequestList());
   
   for(WorkRequest history : account.getWorkQueue().getOldRequestList())
      {
          if(history.getReceiver() == history.getSender()){
            account.getWorkQueue().getWorkRequestList().remove(history);

          }
 
       } 

   populateInbox();
//        account.getWorkQueue().getWorkRequestList().clear();    
    }
     
    
    ///-----------------general--------------------//
    //////////////////////////////settings/////////////////////////
    
     public void setFarmUsageToggle(){
       if(farm.getUsage().equals("Cultivated")){
           farmStateJToggleButton.setSelected(true);
       }else{
           farmStateJToggleButton.setSelected(false); 
           suggestLending();
       }
        
        
    }
    
    
 /////------------------------settings----------------------------////
     
     
     
     
     
     
 /////////////////////////////////farms////////////////////////////////////
     
     
    public void setFarmMoistureLevel(){
     
        ClimaticFactors cf = currentCF;
      for(FarmSection f: farm.getFarmSectionList()){
         
          int size =  f.getSectionStatusHistory().getSectionStatusList().size();
          SectionStatus latestStatus = f.getSectionStatusHistory().getSectionStatusList().get(size-1);
           
          SectionStatus  ss = new SectionStatus();    
          int initialMoistureLevel = latestStatus.getMoistureLevel();
          int moistureLevel = initialMoistureLevel;
          int difference = 0;
          if(cf.getTemperature()>=0 && cf.getTemperature()<=7){
              difference = (7-cf.getTemperature())/2;
              moistureLevel = moistureLevel+difference;   
          }else{
              difference = (cf.getTemperature()-8)/2;
              moistureLevel = moistureLevel-difference;
          }
          if(cf.getHumidity()>= 30 && cf.getHumidity()<43){
              difference = (43-cf.getHumidity())/2;
               moistureLevel = moistureLevel-difference;
          }else{
              difference = (cf.getHumidity()-43)/2;
               moistureLevel = moistureLevel+difference;
          }
          //sunny|partlySunny|cloudy|slightRains|thunderStorms
          
          
          if(cf.getSky().equals("sunny")){
              moistureLevel = moistureLevel-3;
          }else if(cf.getSky().equals("partlySunny")){
              moistureLevel = moistureLevel-2; 
          }else if(cf.getSky().equals("cloudy")){
              moistureLevel = moistureLevel+1; 
          }else if(cf.getSky().equals("slightRains")){
              moistureLevel = moistureLevel+2; 
          }else if(cf.getSky().equals("thunderStorms")){
              moistureLevel = moistureLevel+2; 
          }
          if(cf.getWindSpeed()>=10 &&cf.getWindSpeed()<13 ){
              moistureLevel = moistureLevel+2;
          }else{
              moistureLevel = moistureLevel-2; 
          }
        if(moistureLevel<10){
            
            moistureLevel = 10;
        }
          ss.setMoistureLevel(moistureLevel);
          ss.setIsSown(latestStatus.isIsSown());
          ss.setIsPesticideApplied(latestStatus.isIsPesticideApplied());
          ss.setCf(cf);
     f.getSectionStatusHistory().getSectionStatusList().add(ss);
      } 
      
  // return diff;
      
  }
     
     
     
    //////-----------------farms------------------------------/////////////////// 
    
    
    
    //////////////////////////////inventory///////////////////////////////////////
    
   public void viewMySeedDetails(){
        
      
       
       Crop myCrop;
        int selectedRow = mySeedTable.getSelectedRow();

        if (selectedRow >= 0) {
            
            myCrop = (Crop)mySeedTable.getValueAt(selectedRow, 0);

        
         Crop supplierCrop = null;  
       for(Crop c : myCrop.getSupplier().getMasterCropCatalog().getCropList()){
           
           if(c == myCrop){
               
             supplierCrop = c;  
               
           }
       }
         try{
      Utility u = new Utility();
            ImageIcon unlike = u.createImageIcon("images/unlike.png");
            ImageIcon like = u.createImageIcon("images/like.png");
             likersJlabel.setText(String.valueOf(supplierCrop.getSeedLikers().size())+" people like this seed");
             usersJLabel.setText(String.valueOf(supplierCrop.getSeedUsers().size())+ " people are using this seed");
             
             if(supplierCrop.getSeedLikers().isEmpty()){
                 
                likeSeedJButton.setText("Like");  
                likeSeedJButton.setIcon(like);
             }
             else{
                 
             for(Farmer f : supplierCrop.getSeedLikers()){
                 
                 if(f == (Farmer)employee){
                    likeSeedJButton.setText("Unlike"); 
                    likeSeedJButton.setIcon(unlike);
                 }else{
                     likeSeedJButton.setText("Like");  
                     likeSeedJButton.setIcon(like);
                 }
               }
             }
   
              showSeedDetailsSection1(true);
       showSeedDetailsSection2(true);
              
         }catch(Exception e){
             
             JOptionPane.showMessageDialog(this, "Details of this seed are no more available");
         }

        }else {
            JOptionPane.showMessageDialog(null, "Choose a seed");
            return;
        }
       
       
       
       
    }
   
   
  public void  viewMessageDetails(){
           Crop cropToGet;
        
         int selectedRow = inboxJTable.getSelectedRow();

        if (selectedRow >= 0) {
            
             WorkRequest wr  = (WorkRequest)inboxJTable.getValueAt(selectedRow, 0);
             wr.setStatus("Read");
             populateInbox();
             
             messageDetailsArea.setText("Sender: "+wr.getSender().getEmployee().getName()+" \n\nSubject: "+wr.getRequestTitle()+"\n\nMessage: "+wr.getMessage());
        }
   }
   
    public void viewMarketSeedDetails(){
       
       
       Crop cropToGet;
        
         int selectedRow = seedJTable.getSelectedRow();

        if (selectedRow >= 0) {
            
             cropToGet  = (Crop)seedJTable.getValueAt(selectedRow, 0);

        
             likersJlabel.setText(String.valueOf(cropToGet.getSeedLikers().size())+" people like this seed");
             usersJLabel.setText(String.valueOf(cropToGet.getSeedUsers().size())+ " people are using this gadget");
             showSeedDetailsSection1(true);
       showSeedDetailsSection2(false); 
              
             
            if(cropToGet.getSharingFarmer() != null){
             //     lenderNameJLabel.setText("Lent by "+gadgetToBuy.getLender().getName()); // #### to do $$$$
            }

 
        }else {
            JOptionPane.showMessageDialog(null, "Choose a crop");
            return;
        }
        
       
        
    }
    
     public void showSeedDetailsSection1(boolean flag){
         
       usersAndlikersJPanel.setVisible(flag);
     }
     public void showSeedDetailsSection2(boolean flag){
         
       likeSeedJButton.setVisible(flag);
     }
    
   public void  populateSupplierComboboxForGadget(){
        supplierJComboBox.removeAllItems();
      
         for(Organization o: enterprise.getOrganizationDirectory().getOrganizationList()){
          
            if(o.getName().equals("Supplier Organization")){ // ### o instance of supplierorg not working
              
                
                for( Employee e : o.getEmployeeDirectory().getEmployeeList()){
              //      System.err.println("supp name >> "+e.getName());
                 supplierJComboBox.addItem((Supplier)e);
               
               }
                
            }
            
        }
   }
   
   
           
    public void populateGadgetsForRent(){
        DefaultTableModel model = (DefaultTableModel) GadgetsAvailableTable.getModel();
        
        model.setRowCount(0);
               
        for(Gadget gadgetOnRent: farmerOrganization.getLentGadgetCatalog().getGadgetList())
         {
             
             if(gadgetOnRent.getLender().getId() != ((Farmer) employee).getId()){
                   Gadget.GadgetType theGadgetType= gadgetOnRent.getTypeOfGadget() ;       
             
            Object[] row = new Object[4];
            
            row[0] = gadgetOnRent;
            row[1] = gadgetOnRent.getName();
            row[2] = theGadgetType.getGadGetLikers().size();
            row[3] = gadgetOnRent.getLender();
       
            model.addRow(row); 
             }
          
            //System.err.println("loaded table "+model.getRowCount());
        }
   }
    
    /////----------------------manageInventory--------------/////////
     public void  populateSupplierComboboxForCrop(){
       
        supplierJComboBox1.removeAllItems();
         for(Organization o: enterprise.getOrganizationDirectory().getOrganizationList()){
          
            if(o.getName().equals("Supplier Organization")){ // ### o instance of supplierorg not working
              
                
                for( Employee e : o.getEmployeeDirectory().getEmployeeList()){
              //      System.err.println("supp name >> "+e.getName());
                
               supplierJComboBox1.addItem((Supplier)e);
               }
                
            }
            
        }
   }

    public void  populateInbox(){
     DefaultTableModel model = (DefaultTableModel) inboxJTable.getModel();        
     model.setRowCount(0);
   int messageCount = 0;
   int birdmsgCount = 0;
   
   messageDetailsArea.setText("");
        for(WorkRequest myWorkReq: account.getWorkQueue().getWorkRequestList()){

           Object[] row = new Object[4];
           
            row[0] = myWorkReq;
            row[1] = myWorkReq.getSender().getEmployee().getName();
            row[2] = myWorkReq.getSender().getRole();
            row[3] = myWorkReq.getMessage();

            model.addRow(row); 
            String status = myWorkReq.getStatus() == null ? "-": myWorkReq.getStatus();
            if( !status.equals("Read")){
   messageCount++;}
            
             
        if(myWorkReq.getRequestTitle().equals("Bird/Animal found : ALERT")){
            birdmsgCount ++;
        }
        } 
      
        if(messageCount!=0){
        notificationHomeLabel.setText("You have "+messageCount+ " unread notifications");
        }else{
          notificationHomeLabel.setText("Notifications");   
        }
        
        if(birdmsgCount!=0){
        watchfarmHomeLabel.setText("Bird/Animal found in farm");
        }else{
          watchfarmHomeLabel.setText("Watch Farm");   
        }
    }
    public void checkBirdOrAnimalPresence(){
        
        for(FarmSection f : farm.getFarmSectionList()){
          Random r = new Random(); 
          int size =  f.getSectionStatusHistory().getSectionStatusList().size();
          SectionStatus latestStatus = f.getSectionStatusHistory().getSectionStatusList().get(size-1);
            
          SectionStatus bs= new SectionStatus();
          bs.setBirdOrAnimalPresent(r.nextBoolean());
          bs.setMoistureLevel(latestStatus.getMoistureLevel());
          bs.setIsSown(latestStatus.isIsSown());
          bs.setIsPesticideApplied(latestStatus.isIsPesticideApplied());
          bs.setDate(new Date());
          bs.setCf(currentCF);
          f.getSectionStatusHistory().getSectionStatusList().add(bs);
            
        }
        
     //   showFarms();
    }
    
    public void nextClimate(){
       
           Utility u = new Utility();
        int r = Utility.randInt(0, 2);
    //   dayJLabel.setText("");
       ClimaticFactors cf =   network.getClimaticFactorHistory().getClimaticFactorList().get(dayCount);
           sunshineIconLabel.setIcon(u.createImageIcon("images/"+cf.getSky()+".png")); // ## to do $$$$$$$$$$
            temperatureJLabel.setText(String.valueOf(cf.getTemperature()));
      humidityJLabel.setText(String.valueOf(cf.getHumidity()));
      sunshineJLabel.setText(String.valueOf(cf.getSky()));
      windyJLabel.setText(String.valueOf(cf.getWindSpeed()));
      
       dayCount++;
      if(dayCount == network.getClimaticFactorHistory().getClimaticFactorList().size()){ 
      dayCount = 0;
     // dayJLabel.setText("Today");
      }
      
    }
 //   int i =0;
    public ClimaticFactors measureClimateFactors() {
        
       ClimaticFactors cf= new ClimaticFactors();
             int r = Utility.randInt(0, 2);
//  //  noClimateInfoJLabel.setText(String.valueOf(r.nextInt()));
//       int i =0; 
//       Date d2 = new Date();
//       int timeDiff = (int) (d2.getTime() - d1.getTime());
//       int count = (int) Math.floor(timeDiff/5000);
//      if(count < network.getClimaticFactorHistory().getClimaticFactorList().size()){
//          i = count;
//      }
//     if(i==3){
//         
//         d1= new Date();
//     }
//      else{
//       
//       d1 = new Date();
//       timeDiff = (int) (d2.getTime() - d1.getTime());
//       count = (int) Math.floor(timeDiff/5000);  
//       
//      }
    //climateInfoJLabel.setText(String.valueOf(r));
        
          Utility u = new Utility(); 
     ClimaticFactors cfn = network.getClimaticFactorHistory().getClimaticFactorList().get(r);
//     i++;
//     if(i==3){
//         i=0;
//     }
     cf.setSky(cfn.getSky());
     cf.setHumidity(cfn.getHumidity());
     cf.setTemperature(cfn.getTemperature());
     
     cf.setWindSpeed(cfn.getWindSpeed());
     sunshineIconLabel.setIcon(u.createImageIcon("images/"+cf.getSky()+".png")); // ## to do $$$$$$$$$$
     //temperatureJLabel.setText();
      temperatureJLabel.setText(String.valueOf(cf.getTemperature()));
      humidityJLabel.setText(String.valueOf(cf.getHumidity()));
      sunshineJLabel.setText(String.valueOf(cf.getSky()));
      windyJLabel.setText(String.valueOf(cf.getWindSpeed()));
     //  network.getClimaticFactorHistory().getClimaticFactorList().add(cf);
       
       return cf;
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   public void generateWorkMessage(FarmSection fs){
  int size =  fs.getSectionStatusHistory().getSectionStatusList().size();
          SectionStatus latestStatus = fs.getSectionStatusHistory().getSectionStatusList().get(size-1);
              
       if(!latestStatus.isIsSown()){
 
           WorkRequest wr = new WorkRequest();
           
           wr.setMessage(fs.getAreaIndex()+ " is unsown ");
           wr.setRequestTitle("UnSown Farm : REMINDER");
           wr.setSender(account);
           wr.setReceiver(account);
           wr.setStatus("Sent");
           
           account.getWorkQueue().getWorkRequestList().add(wr);

       //    fs.setAlertStatus(wr.getStatus());
 
       }
//       else {
//           if(latestStatus.getMoistureLevel() < fs.getCrop().getMoistureThreshold()){
//    
//           WorkRequest wr = new WorkRequest();
//           
//           wr.setMessage("Section "+fs.getAreaIndex()+ " needs to be irrigated ");
//           wr.setRequestTitle("Irrigation : REMINDER");
//           wr.setSender(account);
//           wr.setReceiver(account);
//           wr.setStatus("Sent");
//           
//           account.getWorkQueue().getWorkRequestList().add(wr);
//  
//           }
//       }
        populateInbox();      
       
   } 
   
   // iconrenderer classes
   
    class MyModel extends AbstractTableModel{
        
        private int columns;
        private Object[] rows;
        
        public MyModel(){}
        
        public MyModel(Object[] rows,int columns){
            
            this.rows = rows;
            
            this.columns = columns;
        }

    
        
        @Override
        public int getRowCount() {
            return this.rows.length;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getColumnCount() {
            return this.columns;
           // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
           return "Indicator "+(rowIndex * columnIndex );
// throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
         @Override
    public Class<?> getColumnClass(int c) {
        //     System.err.println(Icon.class);
         return Icon.class;
//        if(c == 5){
//            return Icon.class;
//        }else{
//          return getValueAt(0, c).getClass();  
//        }
        
    }
        
        
    }
    
    class IconRenderer extends JLabel implements TableCellRenderer{

   @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
         boolean isSelected, boolean hasFocus, int row, int col) {
       JLabel lbl = new JLabel();
        ImageIcon image = new ImageIcon("images/okay.ico");
//      lbl.setText((String) value);
       lbl.setIcon(image);
       return this;
       //  return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
      }     
    } 

  public void showFarms(){
  
   //  soilTypeJLabel.setText(((Farmer)employee).getFarm().getSoilType());
  
       DefaultTableModel model = (DefaultTableModel) farmJTable.getModel();
   
        model.setRowCount(0);
        

        for(FarmSection fs : ((Farmer)employee).getFarm().getFarmSectionList())
        {
            int size =  fs.getSectionStatusHistory().getSectionStatusList().size();
          SectionStatus latestStatus = fs.getSectionStatusHistory().getSectionStatusList().get(size-1);
              
            
            generateWorkMessage(fs);
              String cropName ="";
              String status ="";
              String cropThreshold = "";
            if (latestStatus.isIsSown()){
                
               cropName =  fs.getCrop().getCropName();
              // status = fs.getCrop().getStage();
               status = "Sown";
               cropThreshold = String.valueOf(fs.getCrop().getMoistureThreshold());
              
            }else{
               cropName ="-";
               status = "Unsown"; 
               cropThreshold = "-";
            }

      //  ImageIcon renderedIcon = okay;
            
          Object[] row = new Object[6];
            row[0] = fs;
            row[1] = status;
            row[2] = cropName;
            row[3] = String.valueOf(latestStatus.getMoistureLevel());
            row[4] = cropThreshold;
            row[5] = latestStatus.isBirdOrAnimalPresent() ? "Present" : "Absent";
            


 
            model.addRow(row); 
            farmJTable.setRowHeight(30);
  
        }


  }
  
   public void populateCropCombo(){
       
    cropJComboBox.removeAllItems();
    seedJComboBox.removeAllItems();
        
        for (Crop crop : ((Farmer)employee).getMyCropCatalog().getCropList()){
                 cropJComboBox.addItem(crop);  
                 seedJComboBox.addItem(crop);  
        }
   }

    
  public void showGadgetDetailsSection1(boolean flag){
      
    viewDetailsSubPanel.setVisible(flag);
   
    }
  
   public void showGadgetDetailsSection2(boolean flag){

      likeJButton.setVisible(flag);
  
    }
    
 /////////////////////////manage gadgets//////////////////////////////
   
      private void populateAllGadgetsTable(Supplier selectedSupplier){
       
        DefaultTableModel model = (DefaultTableModel) GadgetsAvailableTable.getModel();
        
        model.setRowCount(0);
               
        for(Gadget masterGadget: selectedSupplier.getMasterGadgetCatalog().getGadgetList())
         {
             Gadget.GadgetType theGadgetType= masterGadget.getTypeOfGadget() ;       
             
            Object[] row = new Object[3];
            
            row[0] = masterGadget;
            row[1] = masterGadget.getName();
            row[2] = theGadgetType.getGadGetLikers().size();
            
            model.addRow(row);
          
        }
                 
       
    }
      
      ////-----------manage gadgets--------------------------//////
     
     private void populateSeedTable(Supplier selectedSupplier){
         
         DefaultTableModel model = (DefaultTableModel) seedJTable.getModel();
        
        model.setRowCount(0);
               
        for(Crop crop: selectedSupplier.getMasterCropCatalog().getCropList())
         {
                 
             
            Object[] row = new Object[2];
            
            row[0] = crop;
            row[1] = "-";
           
       
            model.addRow(row);
          
        } 
         
     }
        private boolean isPresentGadget(Gadget.GadgetType gadgetType){
        
        boolean isPresent = false;
        
         for(Gadget myGadget: ((Farmer)employee).getMyGadgetCatalog().getGadgetList()) {
             
                if(myGadget.getName().equals(gadgetType.getValue())) { 

               isPresent = true;
                }
               
         }
         return isPresent;
        }
      
        
        
                
       private boolean isPresentSeed(Crop crop){
        
        boolean isPresent = false;
        
         for(Crop myCrop: ((Farmer)employee).getMyCropCatalog().getCropList()) {
             
                if(myCrop.getCropName().equals(crop.getCropName())) { 

               isPresent = true;
                }
               
         }
         return isPresent;
        }
        
         private void populateMyGadgetsTable(){ // also populate all corresponging gadget tables
             
         gadgetJComboBox.removeAllItems();
        DefaultTableModel model = (DefaultTableModel) gadgetTable.getModel();
        
        model.setRowCount(0);
       
       //  System.err.println("list "+((Farmer) employee).getMyGadgetCatalog().getGadgetList()); 
         for(Gadget thisGadget: ((Farmer) employee).getMyGadgetCatalog().getGadgetList())
           {

            Object[] row = new Object[3];
            row[0] = thisGadget;
            row[1] = ( thisGadget.getCurrentOwner() == ((Farmer)employee)) ? thisGadget.getTypeOfGadget().getStatus() : "-";
            row[2] = ( thisGadget.getCurrentOwner() == ((Farmer)employee)) ?  "Available": "Lent";
            
           
            model.addRow(row);
            
            
            if((thisGadget.getLender() == ((Farmer)employee))&& (!thisGadget.isOnRent())&&(!inSharedCatalogAlready(thisGadget,((Farmer)employee))) ){ //
                gadgetJComboBox.addItem(thisGadget); 
            }
           
           }
        }

public boolean inSharedCatalogAlready(Gadget gadgetToBeShared, Farmer lender){
    
    
    for(Gadget g : farmerOrganization.getLentGadgetCatalog().getGadgetList()){
        
        if((g.getName().equals( gadgetToBeShared.getName()) )&&(g.getLender() == lender )){
            
            return true;
        }
    }
  return false;  
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        farmerWorkAreaJSplitPane = new javax.swing.JSplitPane();
        headerJPanel = new javax.swing.JPanel();
        climateJPanel = new javax.swing.JPanel();
        sunshineIconLabel = new javax.swing.JLabel();
        temperatureJLabel = new javax.swing.JLabel();
        humidityJLabel = new javax.swing.JLabel();
        sunshineJLabel = new javax.swing.JLabel();
        windyJLabel = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        degCelJLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        farmerNameJlabel = new javax.swing.JLabel();
        climateInfoJLabel = new javax.swing.JLabel();
        networkJLabel = new javax.swing.JLabel();
        myTabs = new javax.swing.JTabbedPane();
        homeTabPanel = new javax.swing.JPanel();
        notificationHomeLabel = new javax.swing.JLabel();
        NotificationsTabButton = new javax.swing.JButton();
        TrainingTabButton = new javax.swing.JButton();
        WatchFarmButton = new javax.swing.JButton();
        inventoryButton = new javax.swing.JButton();
        trainingHomeJLabel = new javax.swing.JLabel();
        watchfarmHomeLabel = new javax.swing.JLabel();
        inventoryHomeLabel = new javax.swing.JLabel();
        attendTrainingTabPanel = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        trainingDocumentsTabbedPane = new javax.swing.JPanel();
        expertJComboBox = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        documentJTable = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        ViewDiscussions = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        queryResponseJTable = new javax.swing.JTable();
        allQueriesJButton = new javax.swing.JButton();
        myQueriesJButton = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        forumJTextArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        askExpert = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        postQueryJTextArea = new javax.swing.JTextArea();
        postJButton = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        queryTitleJTextField = new javax.swing.JTextField();
        errTitleJLabel = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        errQueryJLabel = new javax.swing.JLabel();
        notificationTabPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        inboxJTable = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        messageDetailsArea = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        settingsTabPanel = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        gadgetJComboBox = new javax.swing.JComboBox();
        shareSeedJButton = new javax.swing.JButton();
        seedJComboBox = new javax.swing.JComboBox();
        lendGadgetJComboBox = new javax.swing.JButton();
        setFarmUsageLabel = new javax.swing.JLabel();
        farmStateJToggleButton = new javax.swing.JToggleButton();
        watchFarmTabPanel = new javax.swing.JPanel();
        farmLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        farmJTable = new javax.swing.JTable();
        addSectionJButton = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        cropJComboBox = new javax.swing.JComboBox();
        irrigationJSlider = new javax.swing.JSlider();
        inventoryTabbedPanel = new javax.swing.JPanel();
        gadgetsAndInventoryInnerPane = new javax.swing.JTabbedPane();
        manageGadgetsTabPanel = new javax.swing.JPanel();
        viewDetailsSubPanel = new javax.swing.JPanel();
        noOfUsersJLabel = new javax.swing.JLabel();
        ratingJLabel = new javax.swing.JLabel();
        suppliedByJLabel = new javax.swing.JLabel();
        lenderNameJLabel = new javax.swing.JLabel();
        gadgetSupplierJPanel = new javax.swing.JPanel();
        addJButton = new javax.swing.JButton();
        allGadgetScrollPane = new javax.swing.JScrollPane();
        GadgetsAvailableTable = new javax.swing.JTable();
        supplierJComboBox = new javax.swing.JComboBox();
        supplierJLabel = new javax.swing.JLabel();
        rentJLabel = new javax.swing.JLabel();
        rentJButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        myGadgetScrollPane = new javax.swing.JScrollPane();
        gadgetTable = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        getNewJButton = new javax.swing.JButton();
        startStopJButon = new javax.swing.JButton();
        rentGadgetJButton = new javax.swing.JButton();
        returnGadgetJButton = new javax.swing.JButton();
        getBackJButton = new javax.swing.JButton();
        likeJButton = new javax.swing.JButton();
        manageSeedTabbedPanel = new javax.swing.JPanel();
        seedSupplierPanel = new javax.swing.JPanel();
        suppSeedJLabel = new javax.swing.JLabel();
        supplierJComboBox1 = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        seedJTable = new javax.swing.JTable();
        getSeedJButton = new javax.swing.JButton();
        shareSeedsLabel = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        mySeedTable = new javax.swing.JTable();
        orderSeedsJButton = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        borrowSeedsJButton = new javax.swing.JButton();
        likeSeedJButton = new javax.swing.JButton();
        usersAndlikersJPanel = new javax.swing.JPanel();
        usersJLabel = new javax.swing.JLabel();
        likersJlabel = new javax.swing.JLabel();

        setLayout(new java.awt.CardLayout());

        farmerWorkAreaJSplitPane.setDividerLocation(150);
        farmerWorkAreaJSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        headerJPanel.setBackground(new java.awt.Color(211, 239, 245));
        headerJPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        climateJPanel.setBackground(new java.awt.Color(211, 239, 245));
        climateJPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        climateJPanel.add(sunshineIconLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 60, 40));

        temperatureJLabel.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        temperatureJLabel.setForeground(new java.awt.Color(21, 126, 251));
        temperatureJLabel.setText("h");
        climateJPanel.add(temperatureJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 50, -1));

        humidityJLabel.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        humidityJLabel.setForeground(new java.awt.Color(21, 126, 251));
        humidityJLabel.setText("hu");
        climateJPanel.add(humidityJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 50, -1));

        sunshineJLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sunshineJLabel.setForeground(new java.awt.Color(21, 126, 251));
        sunshineJLabel.setText("Sunshine");
        climateJPanel.add(sunshineJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 60, -1, -1));

        windyJLabel.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        windyJLabel.setForeground(new java.awt.Color(21, 126, 251));
        windyJLabel.setText("W");
        climateJPanel.add(windyJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 50, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(21, 126, 251));
        jLabel14.setText("km/h");
        climateJPanel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, -1, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(21, 126, 251));
        jLabel18.setText("%");
        climateJPanel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 20, 22, -1));

        degCelJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/FarmerRole/images/degree_celcius.png"))); // NOI18N
        climateJPanel.add(degCelJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, -1, 40));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(21, 126, 251));
        jLabel4.setText("Temperature");
        climateJPanel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(21, 126, 251));
        jLabel5.setText("Humidity");
        climateJPanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(21, 126, 251));
        jLabel6.setText("Wind");
        climateJPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, -1, -1));

        headerJPanel.add(climateJPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 790, 90));

        farmerNameJlabel.setText("farmer Name");
        headerJPanel.add(farmerNameJlabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 50, -1, -1));

        climateInfoJLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        climateInfoJLabel.setForeground(new java.awt.Color(21, 126, 251));
        climateInfoJLabel.setText("Climate information unavailable");
        headerJPanel.add(climateInfoJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 280, -1));

        networkJLabel.setText("network");
        headerJPanel.add(networkJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 100, -1, -1));

        farmerWorkAreaJSplitPane.setTopComponent(headerJPanel);

        myTabs.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        homeTabPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        notificationHomeLabel.setText("Notifications");
        homeTabPanel.add(notificationHomeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 70, 400, -1));

        NotificationsTabButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/FarmerRole/images/bell32.png"))); // NOI18N
        NotificationsTabButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NotificationsTabButtonActionPerformed(evt);
            }
        });
        homeTabPanel.add(NotificationsTabButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, 90, 80));

        TrainingTabButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/FarmerRole/images/expert32.png"))); // NOI18N
        TrainingTabButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TrainingTabButtonActionPerformed(evt);
            }
        });
        homeTabPanel.add(TrainingTabButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 160, 90, 80));

        WatchFarmButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/FarmerRole/images/watch32.png"))); // NOI18N
        WatchFarmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WatchFarmButtonActionPerformed(evt);
            }
        });
        homeTabPanel.add(WatchFarmButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 270, 90, 80));

        inventoryButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/FarmerRole/images/inventory32 (1).png"))); // NOI18N
        inventoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inventoryButtonActionPerformed(evt);
            }
        });
        homeTabPanel.add(inventoryButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 390, 90, 80));

        trainingHomeJLabel.setText("Training");
        homeTabPanel.add(trainingHomeJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 200, 400, 30));

        watchfarmHomeLabel.setText("Watch Farm");
        homeTabPanel.add(watchfarmHomeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 310, 370, -1));

        inventoryHomeLabel.setText("Gadgets and Inventory");
        homeTabPanel.add(inventoryHomeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 420, 320, -1));

        myTabs.addTab("Home ", new javax.swing.ImageIcon(getClass().getResource("/UserInterface/FarmerRole/images/home16.png")), homeTabPanel); // NOI18N

        attendTrainingTabPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        trainingDocumentsTabbedPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        expertJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        expertJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expertJComboBoxActionPerformed(evt);
            }
        });
        trainingDocumentsTabbedPane.add(expertJComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 32, 129, 37));

        jLabel16.setText("Expert");
        trainingDocumentsTabbedPane.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 40, -1, -1));

        documentJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title", "Document Name", "Number of Downloads"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        documentJTable.setRowHeight(30);
        jScrollPane8.setViewportView(documentJTable);
        if (documentJTable.getColumnModel().getColumnCount() > 0) {
            documentJTable.getColumnModel().getColumn(2).setResizable(false);
        }

        trainingDocumentsTabbedPane.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 87, 591, 181));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/FarmerRole/images/download32.png"))); // NOI18N
        jButton5.setText("Download");
        jButton5.setPreferredSize(new java.awt.Dimension(200, 40));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        trainingDocumentsTabbedPane.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(324, 286, 181, 52));

        jTabbedPane2.addTab("Download training documents >>", trainingDocumentsTabbedPane);

        ViewDiscussions.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        queryResponseJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title", "Query", "No of responses"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        queryResponseJTable.setRowHeight(30);
        jScrollPane6.setViewportView(queryResponseJTable);

        ViewDiscussions.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 104, 385, 250));

        allQueriesJButton.setText("All Queries");
        allQueriesJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allQueriesJButtonActionPerformed(evt);
            }
        });
        ViewDiscussions.add(allQueriesJButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 40, 120, 40));

        myQueriesJButton.setText("My Queries");
        myQueriesJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myQueriesJButtonActionPerformed(evt);
            }
        });
        ViewDiscussions.add(myQueriesJButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(297, 40, 120, 40));

        forumJTextArea.setEditable(false);
        forumJTextArea.setColumns(20);
        forumJTextArea.setRows(5);
        forumJTextArea.setEnabled(false);
        jScrollPane7.setViewportView(forumJTextArea);

        ViewDiscussions.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(503, 104, 360, 259));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/FarmerRole/images/comments32.png"))); // NOI18N
        jLabel2.setText("Expert Comments");
        ViewDiscussions.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(503, 44, 170, 42));

        jTabbedPane2.addTab("View Discussions >> ", ViewDiscussions);

        askExpert.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Post a query");
        askExpert.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(356, 16, -1, -1));

        postQueryJTextArea.setColumns(20);
        postQueryJTextArea.setRows(5);
        postQueryJTextArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                postQueryJTextAreaFocusLost(evt);
            }
        });
        jScrollPane5.setViewportView(postQueryJTextArea);

        askExpert.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 177, 398, 111));

        postJButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/FarmerRole/images/send.png"))); // NOI18N
        postJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postJButtonActionPerformed(evt);
            }
        });
        askExpert.add(postJButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 200, 70, 50));

        jLabel15.setText("Title");
        askExpert.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, -1, -1));

        queryTitleJTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                queryTitleJTextFieldFocusLost(evt);
            }
        });
        queryTitleJTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                queryTitleJTextFieldActionPerformed(evt);
            }
        });
        askExpert.add(queryTitleJTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 70, 150, 40));

        errTitleJLabel.setForeground(new java.awt.Color(255, 51, 102));
        askExpert.add(errTitleJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 128, 280, -1));

        jLabel13.setText("Query");
        askExpert.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 177, -1, -1));

        errQueryJLabel.setForeground(new java.awt.Color(255, 51, 102));
        askExpert.add(errQueryJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 304, 310, -1));

        jTabbedPane2.addTab("Ask an Expert >>", askExpert);

        attendTrainingTabPanel.add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 58, 917, 487));

        myTabs.addTab("Training ", new javax.swing.ImageIcon(getClass().getResource("/UserInterface/FarmerRole/images/training20.png")), attendTrainingTabPanel); // NOI18N

        notificationTabPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inboxJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title", "Sender", "Organization", "Message"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        inboxJTable.setRowHeight(30);
        jScrollPane3.setViewportView(inboxJTable);
        if (inboxJTable.getColumnModel().getColumnCount() > 0) {
            inboxJTable.getColumnModel().getColumn(0).setPreferredWidth(30);
            inboxJTable.getColumnModel().getColumn(1).setPreferredWidth(30);
            inboxJTable.getColumnModel().getColumn(3).setPreferredWidth(300);
        }

        notificationTabPanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 860, 180));

        jScrollPane9.setEnabled(false);

        messageDetailsArea.setEditable(false);
        messageDetailsArea.setColumns(20);
        messageDetailsArea.setRows(5);
        jScrollPane9.setViewportView(messageDetailsArea);

        notificationTabPanel.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 320, 860, -1));

        jLabel3.setText("Select a row to view details");
        notificationTabPanel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, -1, -1));

        myTabs.addTab("Notifications ", new javax.swing.ImageIcon(getClass().getResource("/UserInterface/FarmerRole/images/bell.png")), notificationTabPanel); // NOI18N

        settingsTabPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setText("Share Resources");
        settingsTabPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 341, -1, 35));

        gadgetJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        settingsTabPanel.add(gadgetJComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(322, 430, 115, 50));

        shareSeedJButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/FarmerRole/images/share.png"))); // NOI18N
        shareSeedJButton.setText("Share Seed");
        shareSeedJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shareSeedJButtonActionPerformed(evt);
            }
        });
        settingsTabPanel.add(shareSeedJButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(481, 335, 190, 50));

        seedJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        settingsTabPanel.add(seedJComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(322, 332, 115, 53));

        lendGadgetJComboBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/FarmerRole/images/share.png"))); // NOI18N
        lendGadgetJComboBox.setText("Lend Gadget");
        lendGadgetJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lendGadgetJComboBoxActionPerformed(evt);
            }
        });
        settingsTabPanel.add(lendGadgetJComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(481, 430, 190, 50));

        setFarmUsageLabel.setText("Set Farm Usage ");
        settingsTabPanel.add(setFarmUsageLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 175, 140, 53));

        farmStateJToggleButton.setText("Cultivated");
        farmStateJToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                farmStateJToggleButtonActionPerformed(evt);
            }
        });
        settingsTabPanel.add(farmStateJToggleButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(322, 175, 184, 53));

        myTabs.addTab("Settings ", new javax.swing.ImageIcon(getClass().getResource("/UserInterface/FarmerRole/images/settings.png")), settingsTabPanel); // NOI18N

        watchFarmTabPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        farmLabel.setText("Farm");
        watchFarmTabPanel.add(farmLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        farmJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Section no.", "Section Status", "Crop", "Moisture Level", "Moisture Threshold", "Bird /Animal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(farmJTable);

        watchFarmTabPanel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 910, 130));

        addSectionJButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/FarmerRole/images/add.png"))); // NOI18N
        addSectionJButton.setText("Add Section");
        addSectionJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSectionJButtonActionPerformed(evt);
            }
        });
        watchFarmTabPanel.add(addSectionJButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 300, 180, 50));

        jButton17.setText("Irrigate Section");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        watchFarmTabPanel.add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 410, 210, 50));

        jButton19.setText("Sow");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        watchFarmTabPanel.add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 490, 210, 50));

        jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/UserInterface/FarmerRole/images/graph.png"))); // NOI18N
        jButton20.setText("View Section History ");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        watchFarmTabPanel.add(jButton20, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 300, -1, 50));

        cropJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cropJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cropJComboBoxActionPerformed(evt);
            }
        });
        watchFarmTabPanel.add(cropJComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 490, 180, 50));
        watchFarmTabPanel.add(irrigationJSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 420, -1, -1));

        myTabs.addTab("Watch Farm", new javax.swing.ImageIcon(getClass().getResource("/UserInterface/FarmerRole/images/watch.png")), watchFarmTabPanel); // NOI18N

        manageGadgetsTabPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        noOfUsersJLabel.setText("users");

        ratingJLabel.setText("likers");

        javax.swing.GroupLayout viewDetailsSubPanelLayout = new javax.swing.GroupLayout(viewDetailsSubPanel);
        viewDetailsSubPanel.setLayout(viewDetailsSubPanelLayout);
        viewDetailsSubPanelLayout.setHorizontalGroup(
            viewDetailsSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewDetailsSubPanelLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(viewDetailsSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(noOfUsersJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(viewDetailsSubPanelLayout.createSequentialGroup()
                        .addGroup(viewDetailsSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ratingJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(viewDetailsSubPanelLayout.createSequentialGroup()
                                .addGroup(viewDetailsSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lenderNameJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(suppliedByJLabel))
                                .addGap(0, 119, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        viewDetailsSubPanelLayout.setVerticalGroup(
            viewDetailsSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewDetailsSubPanelLayout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(lenderNameJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(suppliedByJLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(noOfUsersJLabel)
                .addGap(18, 18, 18)
                .addComponent(ratingJLabel)
                .addContainerGap())
        );

        manageGadgetsTabPanel.add(viewDetailsSubPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 410, 300, 150));

        addJButton.setText("Add Gadget");
        addJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addJButtonActionPerformed(evt);
            }
        });

        GadgetsAvailableTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Gadget ID", "Type", "Rating"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        allGadgetScrollPane.setViewportView(GadgetsAvailableTable);

        supplierJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        supplierJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierJComboBoxActionPerformed(evt);
            }
        });

        supplierJLabel.setText("Supplier");

        rentJLabel.setText("Equipment Available on rent");

        rentJButton.setText("Rent Gadget");
        rentJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentJButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout gadgetSupplierJPanelLayout = new javax.swing.GroupLayout(gadgetSupplierJPanel);
        gadgetSupplierJPanel.setLayout(gadgetSupplierJPanelLayout);
        gadgetSupplierJPanelLayout.setHorizontalGroup(
            gadgetSupplierJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gadgetSupplierJPanelLayout.createSequentialGroup()
                .addGroup(gadgetSupplierJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gadgetSupplierJPanelLayout.createSequentialGroup()
                        .addGroup(gadgetSupplierJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(gadgetSupplierJPanelLayout.createSequentialGroup()
                                .addComponent(supplierJLabel)
                                .addGap(27, 27, 27)
                                .addComponent(supplierJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addComponent(rentJLabel))
                            .addComponent(allGadgetScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(gadgetSupplierJPanelLayout.createSequentialGroup()
                        .addComponent(addJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rentJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        gadgetSupplierJPanelLayout.setVerticalGroup(
            gadgetSupplierJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gadgetSupplierJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gadgetSupplierJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(supplierJLabel)
                    .addComponent(supplierJComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rentJLabel))
                .addGap(28, 28, 28)
                .addComponent(allGadgetScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(gadgetSupplierJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rentJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38))
        );

        manageGadgetsTabPanel.add(gadgetSupplierJPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 30, 450, 340));

        gadgetTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Status", "Availability"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        myGadgetScrollPane.setViewportView(gadgetTable);

        jLabel9.setText("My Gadgets");

        getNewJButton.setText("Get New Gadget");
        getNewJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getNewJButtonActionPerformed(evt);
            }
        });

        startStopJButon.setText("Start/Stop");
        startStopJButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startStopJButonActionPerformed(evt);
            }
        });

        rentGadgetJButton.setText("Rent a Gadget");
        rentGadgetJButton.setPreferredSize(new java.awt.Dimension(133, 40));
        rentGadgetJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rentGadgetJButtonActionPerformed(evt);
            }
        });

        returnGadgetJButton.setText("Return Gadget");
        returnGadgetJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnGadgetJButtonActionPerformed(evt);
            }
        });

        getBackJButton.setText("Get Gadget Back");
        getBackJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getBackJButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(myGadgetScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(getNewJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rentGadgetJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(returnGadgetJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(getBackJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(157, 157, 157)
                .addComponent(startStopJButon)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(myGadgetScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startStopJButon, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(getNewJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rentGadgetJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(getBackJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(returnGadgetJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80))
        );

        manageGadgetsTabPanel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 410, 350));

        likeJButton.setText("Like");
        likeJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                likeJButtonActionPerformed(evt);
            }
        });
        manageGadgetsTabPanel.add(likeJButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(111, 510, 120, -1));

        gadgetsAndInventoryInnerPane.addTab("Manage Gadgets >> ", manageGadgetsTabPanel);

        manageSeedTabbedPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        suppSeedJLabel.setText("Supplier");

        supplierJComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        supplierJComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierJComboBox1ActionPerformed(evt);
            }
        });

        seedJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Rating"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        seedJTable.setRowHeight(30);
        jScrollPane2.setViewportView(seedJTable);

        getSeedJButton.setText("Get Seed");
        getSeedJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getSeedJButtonActionPerformed(evt);
            }
        });

        shareSeedsLabel.setText("Shared Seeds");

        javax.swing.GroupLayout seedSupplierPanelLayout = new javax.swing.GroupLayout(seedSupplierPanel);
        seedSupplierPanel.setLayout(seedSupplierPanelLayout);
        seedSupplierPanelLayout.setHorizontalGroup(
            seedSupplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seedSupplierPanelLayout.createSequentialGroup()
                .addGroup(seedSupplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(seedSupplierPanelLayout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addGroup(seedSupplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(seedSupplierPanelLayout.createSequentialGroup()
                                .addComponent(suppSeedJLabel)
                                .addGap(18, 18, 18)
                                .addComponent(supplierJComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(67, 67, 67)
                                .addComponent(shareSeedsLabel))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(seedSupplierPanelLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(getSeedJButton, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        seedSupplierPanelLayout.setVerticalGroup(
            seedSupplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seedSupplierPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(seedSupplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(seedSupplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(suppSeedJLabel)
                        .addComponent(shareSeedsLabel))
                    .addComponent(supplierJComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(getSeedJButton, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addContainerGap())
        );

        manageSeedTabbedPanel.add(seedSupplierPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 50, 510, 310));

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mySeedTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Supplier"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        mySeedTable.setRowHeight(30);
        jScrollPane1.setViewportView(mySeedTable);

        jPanel7.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 77, 356, 140));

        orderSeedsJButton.setText("Order Seeds");
        orderSeedsJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderSeedsJButtonActionPerformed(evt);
            }
        });
        jPanel7.add(orderSeedsJButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 140, 50));

        jLabel17.setText("My Seeds");
        jPanel7.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 16, -1, -1));

        borrowSeedsJButton.setText("Borrow Seeds");
        borrowSeedsJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrowSeedsJButtonActionPerformed(evt);
            }
        });
        jPanel7.add(borrowSeedsJButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(249, 240, 140, 50));

        manageSeedTabbedPanel.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 440, 330));

        likeSeedJButton.setText("Like");
        likeSeedJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                likeSeedJButtonActionPerformed(evt);
            }
        });
        manageSeedTabbedPanel.add(likeSeedJButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(191, 470, 120, 40));

        usersAndlikersJPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usersJLabel.setText("users");
        usersAndlikersJPanel.add(usersJLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 16, 280, -1));

        likersJlabel.setText("likers");
        usersAndlikersJPanel.add(likersJlabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 64, 320, -1));

        manageSeedTabbedPanel.add(usersAndlikersJPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 430, 390, 100));

        gadgetsAndInventoryInnerPane.addTab("Seed >> ", manageSeedTabbedPanel);

        javax.swing.GroupLayout inventoryTabbedPanelLayout = new javax.swing.GroupLayout(inventoryTabbedPanel);
        inventoryTabbedPanel.setLayout(inventoryTabbedPanelLayout);
        inventoryTabbedPanelLayout.setHorizontalGroup(
            inventoryTabbedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventoryTabbedPanelLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(gadgetsAndInventoryInnerPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1063, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );
        inventoryTabbedPanelLayout.setVerticalGroup(
            inventoryTabbedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventoryTabbedPanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(gadgetsAndInventoryInnerPane, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        myTabs.addTab(" Gadgets and Inventory  ", new javax.swing.ImageIcon(getClass().getResource("/UserInterface/FarmerRole/images/inventory16.png")), inventoryTabbedPanel); // NOI18N

        farmerWorkAreaJSplitPane.setRightComponent(myTabs);

        add(farmerWorkAreaJSplitPane, "card4");
    }// </editor-fold>//GEN-END:initComponents

 
    
    private Gadget mygadget;
    Gadget.GadgetType mygadgetType= null;
    
    
    private void addJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addJButtonActionPerformed
        // TODO add your handling code here:
        
        addGadget();
    }//GEN-LAST:event_addJButtonActionPerformed

    public void addGadget(){
        
         Gadget gadgetToBuy;

      int selectedRow = GadgetsAvailableTable.getSelectedRow();

        if (selectedRow >= 0) {
    
           gadgetToBuy = (Gadget)GadgetsAvailableTable.getValueAt(selectedRow, 0);
           
            Gadget.GadgetType gadgetType= gadgetToBuy.getTypeOfGadget();
           
            Supplier supplier = (Supplier) supplierJComboBox.getSelectedItem();
      
           if(!isPresentGadget(gadgetType)){// ## check if update available
             Gadget g =((Farmer)employee).getMyGadgetCatalog().addGadget(gadgetToBuy,supplier,((Farmer)employee));

             
             
             gadgetType.getNumOfUsers().add((Farmer) employee);
             
             noOfUsersJLabel.setText(String.valueOf(gadgetType.getNumOfUsers().size()+ " are using this gadget"));
          
             populateMyGadgetsTable();
             getGadgetsGoing();
                
        JOptionPane.showMessageDialog(this, "Gadget added successfully");
           }else{
               
        JOptionPane.showMessageDialog(this, "Gadget already added");     
           }


        } else {
            JOptionPane.showMessageDialog(null, "Choose a gadget");
            return;
        }
    }
    public void viewMyGadgetDetails(){
      int selectedRow = gadgetTable.getSelectedRow();

        if (selectedRow >= 0) {
            
            mygadget = (Gadget)gadgetTable.getValueAt(selectedRow, 0);
             
            mygadgetType= mygadget.getTypeOfGadget();    
           
             Gadget supplierGadget = null;  
       for(Gadget g : mygadget.getSupplier().getMasterGadgetCatalog().getGadgetList()){
           
           if(g.getTypeOfGadget() == mygadgetType){
             supplierGadget = g;  
               
           }
       }
         try{
             Utility u = new Utility();
            ImageIcon unlike = u.createImageIcon("images/unlike.png");
            ImageIcon like = u.createImageIcon("images/like.png");
             ratingJLabel.setText(String.valueOf(supplierGadget.getTypeOfGadget().getGadGetLikers().size())+" people like this gadget");
             noOfUsersJLabel.setText(String.valueOf(supplierGadget.getTypeOfGadget().getNumOfUsers().size())+ " people are using this gadget");
             
             if(supplierGadget.getTypeOfGadget().getGadGetLikers().isEmpty()){
                 
                likeJButton.setText("Like");  
                likeJButton.setIcon(like);
             }
             else{
                 
             for(Farmer f : supplierGadget.getTypeOfGadget().getGadGetLikers()){
                 
                 if(f == (Farmer)employee){
                    likeJButton.setText("Unlike");  
                     likeJButton.setIcon(unlike);
                 }else{
                     likeJButton.setText("Like");  
                      likeJButton.setIcon(like);
                 }
               }
             }
   
              showGadgetDetailsSection1(true);
              showGadgetDetailsSection2(true);
              
         }catch(Exception e){
             
             JOptionPane.showMessageDialog(this, "Details of this gadget are no more available");
         }

        }else {
            JOptionPane.showMessageDialog(null, "Choose a gadget");
            return;
        }
}
    
    public void viewTheDetails(){
        
        
         Gadget gadgetToBuy;
        
         int selectedRow = GadgetsAvailableTable.getSelectedRow();

        if (selectedRow >= 0) {
            
             gadgetToBuy  = (Gadget)GadgetsAvailableTable.getValueAt(selectedRow, 0);

        
             ratingJLabel.setText(String.valueOf(gadgetToBuy.getTypeOfGadget().getGadGetLikers().size())+" people like this gadget");
             noOfUsersJLabel.setText(String.valueOf(gadgetToBuy.getTypeOfGadget().getNumOfUsers().size())+ " people are using this gadget");
              showGadgetDetailsSection1(true);
              showGadgetDetailsSection2(false);
              
             
            if(gadgetToBuy.getLender() != null){
                  lenderNameJLabel.setText("Lent by "+gadgetToBuy.getLender().getName());
            }
          
            
        
              
 
        }else {
            JOptionPane.showMessageDialog(null, "Choose a gadget");
            return;
        }
        
    }
    
    private void getNewJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getNewJButtonActionPerformed
        // TODO add your handling code here:
        populateSupplierComboboxForGadget();
         gadgetSupplierJPanel.setVisible(true);
      
           
           // ## to do invert selection
      supplierJLabel.setVisible(true);
      supplierJComboBox.setVisible(true);
     addJButton.setVisible(true);
   
        rentJButton.setVisible(false);
        rentJLabel.setVisible(false);
        lenderNameJLabel.setVisible(false);
        lenderNameJLabel.setVisible(false);
        
    }//GEN-LAST:event_getNewJButtonActionPerformed

    private void likeJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_likeJButtonActionPerformed
        // TODO add your handling code here:
        
        likeGadget();
        
        
    }//GEN-LAST:event_likeJButtonActionPerformed

   public void likeGadget(){
       Gadget supplierGadget = null;  
       for(Gadget g : mygadget.getSupplier().getMasterGadgetCatalog().getGadgetList()){
           
           if(g.getTypeOfGadget() == mygadgetType){
             supplierGadget = g;  
               
           }
       }
       
    try{
       Utility u = new Utility();
          if(likeJButton.getText().equals("Like"))
        {

            
            
          supplierGadget.getTypeOfGadget().getGadGetLikers().add((Farmer)employee);
          ImageIcon unlike = u.createImageIcon("images/unlike.png");
          likeJButton.setText("Unlike");
          likeJButton.setIcon(unlike);
          
        }else{
   
             supplierGadget.getTypeOfGadget().getGadGetLikers().remove((Farmer)employee);
              ImageIcon like = u.createImageIcon("images/like.png");
            likeJButton.setText("Like");
            likeJButton.setIcon(like);
        }
        
      //  populateAllGadgetsTable();//##
        populateMyGadgetsTable();
        
        ratingJLabel.setText(String.valueOf(supplierGadget.getTypeOfGadget().getGadGetLikers().size())+" people like this gadget");
         }catch(Exception e){
             
             JOptionPane.showMessageDialog(this, "Details of this gadget are no more available");
         }
   }
    
     public void removeGadget(){
        
        int selectedRow = gadgetTable.getSelectedRow();

        if (selectedRow >= 0) {
            
          mygadget = (Gadget)gadgetTable.getValueAt(selectedRow, 0);

          mygadgetType= mygadget.getTypeOfGadget();
        
        ((Farmer)employee).getMyGadgetCatalog().removeGadget(mygadget);
 
      //  populateAllGadgetsTable();   // ##
        populateMyGadgetsTable();
        
//        int numberOfUsers = mygadgetType.getNumOfUsers().size();

        mygadgetType.getNumOfUsers().remove((Farmer)employee); 
        noOfUsersJLabel.setText(String.valueOf(mygadgetType.getNumOfUsers().size())+ " are using this gadget");

        }
        else {
            JOptionPane.showMessageDialog(null, "Choose a gadget");
            return;
        }
    }
    
    private void startStopJButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startStopJButonActionPerformed
        // TODO add your handling code here:
        
        startStopAction();
    }//GEN-LAST:event_startStopJButonActionPerformed

    public void startStopAction(){
                int selectedRow = gadgetTable.getSelectedRow();
  Utility u = new Utility();
            ImageIcon start = u.createImageIcon("images/start32.png");
            ImageIcon stop = u.createImageIcon("images/stop32.png");
             ImageIcon pause = u.createImageIcon("images/pause32.png");
        if (selectedRow >= 0) {
            
         Gadget gad = (Gadget)gadgetTable.getValueAt(selectedRow, 0);
                   
         String btnStatus = startStopJButon.getText();
        
        if(btnStatus.equals("Start")){
           gad.getTypeOfGadget().setStatus("Started");
           
           startGadget(gad.getTypeOfGadget());
           startStopJButon.setText("Stop");
           startStopJButon.setIcon(stop);
           //// start
        }else if(btnStatus.equals("Stop")){
           gad.getTypeOfGadget().setStatus("Stopped"); 
           stopGadget(gad.getTypeOfGadget());
            startStopJButon.setText("Start");
            startStopJButon.setIcon(start);
        }
        populateMyGadgetsTable();
        }else{
           JOptionPane.showMessageDialog(null, "Choose a gadget");
            return; 
            
        }
    }
    private void addSectionJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSectionJButtonActionPerformed
        // TODO add your handling code here:
        
           SectionStatus firstStatus = ((Farmer) employee).getFarm().addSection();
           firstStatus.setCf(currentCF);
           
       refreshInbox();
       showFarms();
       getGadgetsGoing();
    }//GEN-LAST:event_addSectionJButtonActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        
        irrigateSection();
    }//GEN-LAST:event_jButton17ActionPerformed

    public void irrigateSection(){
        
         int selectedRow = farmJTable.getSelectedRow();

        if (selectedRow >= 0) {
             
          
          FarmSection selectedSection = (FarmSection)farmJTable.getValueAt(selectedRow, 0);
          int size =  selectedSection.getSectionStatusHistory().getSectionStatusList().size();
          SectionStatus latestStatus = selectedSection.getSectionStatusHistory().getSectionStatusList().get(size-1);
              
          if(latestStatus.isIsSown()){
              
           int irrVal = irrigationJSlider.getValue();  

              
              int moistureLevel = latestStatus.getMoistureLevel();
              
              latestStatus.setMoistureLevel(moistureLevel+irrVal);
              
              showFarms();
  
          }
 
        }else{
            
           JOptionPane.showMessageDialog(null, "Choose a section");
            return;  
        }
    
}
    
    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        
        sowSection();
    }//GEN-LAST:event_jButton19ActionPerformed

    public void sowSection(){
        
         int selectedRow = farmJTable.getSelectedRow();

        if (selectedRow >= 0) {
            
          Crop myCrop = (Crop) cropJComboBox.getSelectedItem(); 
          
          FarmSection selectedSection = (FarmSection)farmJTable.getValueAt(selectedRow, 0);
          SectionStatus ss = ((Farmer)employee).getFarm().sowCrop(myCrop, selectedSection);
          ss.setCf(currentCF);
          myCrop.setStage("");
         // selectedSection.
           JOptionPane.showMessageDialog(this, myCrop.getCropName()+" sown in section "+selectedSection.getAreaIndex());

           showFarms();
            getGadgetsGoing(); 
        }else{
            
           JOptionPane.showMessageDialog(null, "Choose a section");
            return;  
        }
       
    }
    FarmSection selectedSection = null;
    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        // TODO add your handling code here:
            int selectedRow = farmJTable.getSelectedRow();

        if (selectedRow >= 0) {
            

          
          selectedSection = (FarmSection)farmJTable.getValueAt(selectedRow, 0); 
        createGraph();
        }else{
            
            JOptionPane.showMessageDialog(this, "Please select a section");
        }
        
    }//GEN-LAST:event_jButton20ActionPerformed

     public void createGraph(){
        
        XYDataset ds = createDataset();
        JFreeChart chart = ChartFactory.createXYLineChart(((Farmer)employee).getName()+"'s Farm : Section "+selectedSection+"Status",
                "x", "y", ds, PlotOrientation.VERTICAL, true, true,
                false);

    //    ChartPanel cp = new ChartPanel(chart);
                
       ChartFrame frame = new ChartFrame("charts", chart);
        
        frame.setSize(600, 400);
       // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
        private XYDataset createDataset() {

        DefaultXYDataset ds = new DefaultXYDataset();
        
       ArrayList<SectionStatus> list = selectedSection.getSectionStatusHistory().getSectionStatusList();
       int size = selectedSection.getSectionStatusHistory().getSectionStatusList().size();
        double[] moistureLevel = new double[size];
        double[] temperature = new double[size];
        double[] humidity = new double[size];
        double[] windspeed = new double[size];
        double[] dataDate = new double[size];
        double[] moistureThreshold = new double[size];
        
        
        

        for (int i = 0; i < list.size(); i++) {
            moistureLevel[i] = list.get(i).getMoistureLevel();
            temperature[i] = list.get(i).getCf().getTemperature();
            humidity[i] = list.get(i).getCf().getHumidity();
            windspeed[i] = list.get(i).getCf().getWindSpeed();
            moistureThreshold[i] = list.get(i).isIsSown() ? selectedSection.getCrop().getMoistureThreshold(): 0;
            dataDate[i] = i;

        }
        double[][] moistureLevelArray = {dataDate, moistureLevel};
        double[][] temperatureArray = {dataDate, temperature};
        double[][] humidityArray = {dataDate, humidity};
        double[][] windspeedArray = {dataDate, windspeed};
        double[][] moistureThresholdArray = {dataDate, moistureThreshold};
        ds.addSeries("Soil Moisture Level", moistureLevelArray);
        ds.addSeries("Temperature", temperatureArray);
        ds.addSeries("Humidity", humidityArray);
        ds.addSeries("Windspeed", windspeedArray);
         ds.addSeries("Moisture Threshold", moistureThresholdArray);

        return ds;
    }
    
    
    private void supplierJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierJComboBoxActionPerformed
        // TODO add your handling code here:
         Supplier selectedSupplier = (Supplier) supplierJComboBox.getSelectedItem();
         
        if (selectedSupplier != null){
            populateAllGadgetsTable(selectedSupplier);
           
        }
        
        
    }//GEN-LAST:event_supplierJComboBoxActionPerformed

    private void supplierJComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierJComboBox1ActionPerformed
        // TODO add your handling code here:
        
          Supplier selectedSupplier = (Supplier) supplierJComboBox1.getSelectedItem();
         
        if (selectedSupplier != null){
            populateSeedTable(selectedSupplier);
           
        }
    }//GEN-LAST:event_supplierJComboBox1ActionPerformed

    private void cropJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cropJComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cropJComboBoxActionPerformed

    private void getSeedJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getSeedJButtonActionPerformed
        // TODO add your handling code here:
        
       addSeed();
        
    }//GEN-LAST:event_getSeedJButtonActionPerformed
public boolean isSeedAlreadyShared(Crop selectedCrop){
    
    for(Crop c: farmerOrganization.getSharedCropCatalog().getCropList()){
        
        if((c.getSharingFarmer() == ((Farmer)employee)) && ((c.getCropName() == selectedCrop.getCropName()))){
            
        return true;    
        }
    }
    
    return false;
}
    private void rentGadgetJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentGadgetJButtonActionPerformed
        // TODO add your handling code here:
       populateGadgetsForRent(); 
        // ## to do  invert selection 
          gadgetSupplierJPanel.setVisible(true);
          
           
           // ## to do invert selection
      supplierJLabel.setVisible(false);
      supplierJComboBox.setVisible(false);
      addJButton.setVisible(false);
   
        rentJButton.setVisible(true);
        rentJLabel.setVisible(true);
        lenderNameJLabel.setVisible(true);
        lenderNameJLabel.setVisible(true);
    }//GEN-LAST:event_rentGadgetJButtonActionPerformed

    private void rentJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rentJButtonActionPerformed
        // TODO add your handling code here:
        
        rentGadget();
        
        
    }//GEN-LAST:event_rentJButtonActionPerformed
//////////////////////////////training/////////////////////////////////
    
    public void populateExpertsComboBox(){
        
        expertJComboBox.removeAllItems();
        
        
        for(Organization o : enterprise.getOrganizationDirectory().getOrganizationList()){
            
            if(o instanceof ExpertOrganization){
                
               for(Employee e : o.getEmployeeDirectory().getEmployeeList()){
                   
                   expertJComboBox.addItem((Expert)e); 
               } 
               
                
            }
        }
         
        
       
    }
    
    
    private void postJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postJButtonActionPerformed
       
        boolean flag = true;
        
        String queryTitle = queryTitleJTextField.getText();
        String query = postQueryJTextArea.getText();
        if(query.equals("")){
          
       errQueryJLabel.setText("Please enter your query");
       flag = false;
        }
        
        if(queryTitle.equals("")){
          
       errTitleJLabel.setText("Please enter a title for the query");
       flag = false;
        }
        if(flag){
            
      
            
            WorkRequest queryRequest = new WorkRequest();
           queryRequest.setRequestTitle(queryTitle); 
           queryRequest.setMessage(query);
           queryRequest.setSender(account);
           queryRequest.setRequestType("Query");
            queryRequest.setRequestDate(new Date());
            queryRequest.setStatus("Not Answered");
            queryRequest.setIsTemporary(false);
           
          ecoSystem.getWorkQueue().getWorkRequestList().add(queryRequest); // availavble globally
        
        JOptionPane.showMessageDialog(this, "Query posted successfully");
         populateDiscussionForum(true);
         queryTitleJTextField.setText("");
         postQueryJTextArea.setText("");
        }
        
        
    }//GEN-LAST:event_postJButtonActionPerformed

    public void populateDiscussionForum(boolean allQueries){
        
        
        DefaultTableModel model = (DefaultTableModel) queryResponseJTable.getModel();
        
        model.setRowCount(0);
               int myQueryTotalResponses = 0;
        for(WorkRequest wr: ecoSystem.getWorkQueue().getWorkRequestList())
         {
              
            if(wr.getSender() == account){
                if(wr.getQueryResponseDirectory().getResponseList().size()>0){
                    myQueryTotalResponses++;
                }
                // myQueryTotalResponses += wr.getQueryResponseDirectory().getResponseList().size();
            }
            if(allQueries){ 
            Object[] row = new Object[3];
            
            row[0] = wr;
            row[1] = wr.getMessage();
            row[2] = wr.getQueryResponseDirectory().getResponseList().size();
            
           
       
            model.addRow(row);
            }else{
                
                if(wr.getSender() == account){
                    
                  Object[] row = new Object[3];
            
            row[0] = wr;
            row[1] = wr.getMessage();
            row[2] = wr.getQueryResponseDirectory().getResponseList().size();
            
          
            model.addRow(row);   
                }
            }
            //System.err.println("loaded table "+model.getRowCount());
        } 
        if(myQueryTotalResponses>0){
        trainingHomeJLabel.setText("Experts are have responses to"+myQueryTotalResponses+" of your queries");
        }else{
         trainingHomeJLabel.setText("Training");   
        }
    }
    
    
    
    private void queryTitleJTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_queryTitleJTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_queryTitleJTextFieldActionPerformed
Expert selectedExpert= null;
    private void expertJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expertJComboBoxActionPerformed
        // TODO add your handling code here:
         selectedExpert = (Expert) expertJComboBox.getSelectedItem();
         
        if (selectedExpert != null){
            populateDocumentTable(selectedExpert);
           
        }
     
        
    }//GEN-LAST:event_expertJComboBoxActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        
     int selectedRow = documentJTable.getSelectedRow();

        if (selectedRow >= 0) {
            
          Training selectedTraining = (Training)documentJTable.getValueAt(selectedRow, 0); 
           String fileName = (String)documentJTable.getValueAt(selectedRow, 1); 
         File destinationFolder = null;
         File destination = null;
         File source = new File(selectedTraining.getReferenceDocumentPath());
          DateFormat dateFormat = new SimpleDateFormat("yyyymmdd");
        JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setDialogTitle("Save As");
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.setAcceptAllFileFilterUsed(false);

    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
      destinationFolder =  chooser.getSelectedFile();
      String destinationFileString = destinationFolder.toString()+"\\"+dateFormat.format(Calendar.getInstance().getTime())+"_"+fileName;
     destination = new File(destinationFileString);
      
       try {
            copyFileUsingJava7Files(source, destination);
            
            selectedTraining.setNumberOfDownloads(selectedTraining.getNumberOfDownloads()+1);
            ((Farmer) employee).getMyAttendedTrainingCatalog().getTrainingList().add(selectedTraining);
            JOptionPane.showMessageDialog(this, fileName+" download complete");
            populateDocumentTable(selectedExpert);
        } catch (IOException ioe) {
            
            ///////////////////////////////////////////////
            ///////////////////////////////////////////////
            //////////    LOGGING         /////////////////
            ///////////////////////////////////////////////   
         //   Logger.getLogger(ExpertWorkAreaJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    } else {
      JOptionPane.showMessageDialog(this, "Please choose a location to save the file");
    }
        
        }
        else{
            
            JOptionPane.showMessageDialog(this, "Please select a Training");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void orderSeedsJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderSeedsJButtonActionPerformed
        // TODO add your handling code here:
        
        seedSupplierPanel.setVisible(true);
        suppSeedJLabel.setVisible(true);
        supplierJComboBox1.setVisible(true);
        shareSeedsLabel.setVisible(false);
    }//GEN-LAST:event_orderSeedsJButtonActionPerformed

    private void allQueriesJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allQueriesJButtonActionPerformed
        // TODO add your handling code here:
        
        populateDiscussionForum(true);
    }//GEN-LAST:event_allQueriesJButtonActionPerformed

    private void myQueriesJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myQueriesJButtonActionPerformed
        // TODO add your handling code here:
        
         populateDiscussionForum(false);
    }//GEN-LAST:event_myQueriesJButtonActionPerformed
 // red  [255,51,102]
    private void borrowSeedsJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrowSeedsJButtonActionPerformed
        // TODO add your handling code here:
        seedSupplierPanel.setVisible(true);
        suppSeedJLabel.setVisible(false);
        supplierJComboBox1.setVisible(false);
        shareSeedsLabel.setVisible(true);
        
        populateSharedSeedTable();
        
    }//GEN-LAST:event_borrowSeedsJButtonActionPerformed

    private void returnGadgetJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_returnGadgetJButtonActionPerformed
        // TODO add your handling code here:
        
        returnGadget();
        
         showGadgetDetailsSection1(false);
              showGadgetDetailsSection2(false);
    }//GEN-LAST:event_returnGadgetJButtonActionPerformed

    private void getBackJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getBackJButtonActionPerformed
        // TODO add your handling code here:
        
        sendRequestToBorrower();
    }//GEN-LAST:event_getBackJButtonActionPerformed

    private void queryTitleJTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_queryTitleJTextFieldFocusLost
        // TODO add your handling code here:
        
       String queryTitle = queryTitleJTextField.getText();
       
        if(!queryTitle.isEmpty()){
            
            errTitleJLabel.setText("");
        }
    }//GEN-LAST:event_queryTitleJTextFieldFocusLost

    private void postQueryJTextAreaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_postQueryJTextAreaFocusLost
        // TODO add your handling code here:
        String query = postQueryJTextArea.getText();
       
        if(!query.isEmpty()){
            
            errQueryJLabel.setText("");
        }
        
    }//GEN-LAST:event_postQueryJTextAreaFocusLost
int dayCount = 0;
    private void likeSeedJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_likeSeedJButtonActionPerformed
        // TODO add your handling code here:
        
        likeSeed();
    }//GEN-LAST:event_likeSeedJButtonActionPerformed

    private void farmStateJToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_farmStateJToggleButtonActionPerformed
        // TODO add your handling code here:
        int dialogButton = JOptionPane.YES_NO_OPTION;

        if( farm.getUsage().equals("Cultivated")){
            if(farm.getFarmSectionList().isEmpty()){
                allowToggle();
                JOptionPane.showMessageDialog(this, "You might want to help other farmers. Lend your gadgets while you leave your farm fallow.");
                suggestLending();

            }
            else{
                int countSown = 0;
                for(FarmSection fs: farm.getFarmSectionList()){

                    int size =  fs.getSectionStatusHistory().getSectionStatusList().size();
                    SectionStatus latestStatus = fs.getSectionStatusHistory().getSectionStatusList().get(size-1);

                    if(latestStatus.isIsSown()){

                        countSown++;
                        break;
                    }

                }

                if(countSown>0){
                    int dialogResult = JOptionPane.showConfirmDialog (this, "Some Sections may not be harvested yet.Do you still want to leave the farm fallow?","Warning",dialogButton);
                    if(dialogResult == JOptionPane.YES_OPTION){
                        allowToggle();
                        JOptionPane.showMessageDialog(this, "You might want to help other farmers. Lend your gadgets while you leave your farm fallow.");
                        suggestLending();

                    }else{
                        farmStateJToggleButton.setSelected(true);
                    }
                }else{
                    allowToggle();
                }
            }
        }
        else{

            allowToggle();
            //          if(account.getWorkQueue().getWorkRequestList().size() == 1 ){ //## to do
                //          account.getWorkQueue().getWorkRequestList().clear();
                //          populateInbox();
                //          }
            //          else{
                refreshInbox();
                showFarms();// to remove old
                //    }

        }

    }//GEN-LAST:event_farmStateJToggleButtonActionPerformed

    private void lendGadgetJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lendGadgetJComboBoxActionPerformed
        // TODO add your handling code here:
        Gadget gadgetForRent = (Gadget) gadgetJComboBox.getSelectedItem();

        if (gadgetForRent != null){

            JOptionPane.showMessageDialog(this, gadgetForRent.getName()+ " successfully made available for lending to farmer org..... ");
            Gadget g = farmerOrganization.lendGadget( gadgetForRent);
            populateMyGadgetsTable();

        }

    }//GEN-LAST:event_lendGadgetJComboBoxActionPerformed

    private void shareSeedJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shareSeedJButtonActionPerformed
        // TODO add your handling code here:
        Crop selectedCrop = (Crop) seedJComboBox.getSelectedItem();

        if (selectedCrop != null){

            if(!isSeedAlreadyShared(selectedCrop)){

                farmerOrganization.shareCrop(((Farmer)employee), selectedCrop);
                JOptionPane.showMessageDialog(this, "Seed for "+selectedCrop+ " successfully made available for share  with Farmer Organization of  "+network.getName()+" network");

            }else{
                JOptionPane.showMessageDialog(this, "Seed for "+selectedCrop+ " already made available for share  with Farmer Organization of  "+network.getName()+" network");
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "No seed to share");
        }

    }//GEN-LAST:event_shareSeedJButtonActionPerformed

    private void NotificationsTabButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NotificationsTabButtonActionPerformed
         myTabs.setSelectedIndex(2);
    }//GEN-LAST:event_NotificationsTabButtonActionPerformed

    private void TrainingTabButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TrainingTabButtonActionPerformed
      myTabs.setSelectedIndex(1);
    }//GEN-LAST:event_TrainingTabButtonActionPerformed

    private void WatchFarmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WatchFarmButtonActionPerformed
         myTabs.setSelectedIndex(4);
    }//GEN-LAST:event_WatchFarmButtonActionPerformed

    private void inventoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inventoryButtonActionPerformed
         myTabs.setSelectedIndex(5);
    }//GEN-LAST:event_inventoryButtonActionPerformed
   
    
     public void previousClimate(){
        dayCount--;
      if(dayCount == 0){ 
       //   dayJLabel.setText("Today");
      dayCount = network.getClimaticFactorHistory().getClimaticFactorList().size();
      }
           Utility u = new Utility();
        int r = Utility.randInt(0, 2);
     //   dayJLabel.setText("");
       ClimaticFactors cf =   network.getClimaticFactorHistory().getClimaticFactorList().get(dayCount);
           sunshineIconLabel.setIcon(u.createImageIcon("images/"+cf.getSky()+".png")); // ## to do $$$$$$$$$$
            temperatureJLabel.setText(String.valueOf(cf.getTemperature()));
      humidityJLabel.setText(String.valueOf(cf.getHumidity()));
      sunshineJLabel.setText(String.valueOf(cf.getSky()));
      windyJLabel.setText(String.valueOf(cf.getWindSpeed()));
      
      
      
    }
    public void likeSeed(){
        
         int selectedRow = mySeedTable.getSelectedRow();

        if (selectedRow >= 0) {     
            

        
        Crop myCrop = (Crop)mySeedTable.getValueAt(selectedRow, 0);
        
         Crop supplierCrop = null;  
       for(Crop c : myCrop.getSupplier().getMasterCropCatalog().getCropList()){
           
           if(c == myCrop){
             supplierCrop = c;  
               
           }
       }
       
    try{
         Utility u = new Utility();
            ImageIcon unlike = u.createImageIcon("images/unlike.png");
            ImageIcon like = u.createImageIcon("images/like.png");
       
          if(likeSeedJButton.getText().equals("Like"))
        {

            
            
          supplierCrop.getSeedLikers().add((Farmer)employee);
          likeSeedJButton.setText("Unlike");
          likeSeedJButton.setIcon(unlike);
          
        }else{
   
              supplierCrop.getSeedLikers().remove((Farmer)employee);
            likeSeedJButton.setText("Like");
            likeSeedJButton.setIcon(like);
        }
        
      //  populateAllGadgetsTable();//##
        populateMyGadgetsTable();
        
        likersJlabel.setText(String.valueOf(supplierCrop.getSeedLikers().size())+" people like this seed");
         }catch(Exception e){
             
             JOptionPane.showMessageDialog(this, "Details of this seed are no more available");
         }
    
        }
    }
    
    public void sendRequestToBorrower(){
        
        
     int selectedRow = gadgetTable.getSelectedRow();

        if (selectedRow >= 0) {     
            
            
           Gadget  gadgetBack = (Gadget)gadgetTable.getValueAt(selectedRow, 0);
        if(gadgetBack.isOnRent()){
          
            // unrent
 
             farmerOrganization.getLentGadgetCatalog().getGadgetList().remove(gadgetBack);
             gadgetBack.setOnRent(false);
             
             populateMyGadgetsTable();
           
            getBackJButton.setVisible(false);
        }
           else{
      String input =   JOptionPane.showInputDialog(this, "This Gadget has been borrowed. Message to borrower :");
      
      if(input.isEmpty()){
           int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult =  JOptionPane.showConfirmDialog(this, "Tou havent sent any message to the borrower. Do you want to cancel", "Warning !",dialogButton);
          if(dialogResult == JOptionPane.YES_OPTION){
          return;
          }
          else{
            input =   JOptionPane.showInputDialog(this, "This Gadget has been borrowed. Message to borrower :");   
          }
         
      }
      
     WorkRequest wr = new WorkRequest();
               
               wr.setIsTemporary(false);
             
           
           wr.setMessage(input);
           wr.setRequestTitle(gadgetBack.getName()+" Requested back : NOTICE");
           wr.setSender(account);
            wr.setStatus("Sent");
          for(UserAccount ua: farmerOrganization.getUserAccountDirectory().getUserAccountList())
          {
              if(ua.getEmployee() == gadgetBack.getBorrower()){
                  
                 wr.setReceiver(ua); 
                  ua.getWorkQueue().getWorkRequestList().add(wr);
              }
          }
          JOptionPane.showMessageDialog(this, "Message sent");
        }
        }
        else{
            
            JOptionPane.showMessageDialog(this, "Plese select a gadget"); 
        }
    }
    
    public void returnGadget(){
       int selectedRow = gadgetTable.getSelectedRow();

        if (selectedRow >= 0) {
    
         Gadget  gadgetToReturn = (Gadget)gadgetTable.getValueAt(selectedRow, 0);
         
         
         farmerOrganization.returnGadget(gadgetToReturn );
         
         ((Farmer)employee).getMyGadgetCatalog().getGadgetList().remove(gadgetToReturn);
         WorkRequest wr = new WorkRequest();
         
           wr.setIsTemporary(false);
             
           
           wr.setMessage("Your "+gadgetToReturn.getName() +" has been returned ");
           wr.setRequestTitle("Gagdet Returned : NOTICE");
           wr.setSender(account);
            wr.setStatus("Sent");
          for(UserAccount ua: farmerOrganization.getUserAccountDirectory().getUserAccountList())
          {
              if(ua.getEmployee() == gadgetToReturn.getLender() ){
                  
                 wr.setReceiver(ua); 
                  ua.getWorkQueue().getWorkRequestList().add(wr);
              }
          }
          
         
         
         
           populateMyGadgetsTable();
        }
        else{
            
            JOptionPane.showMessageDialog(this, "Please select a gadget to return");
        }
        
    }
    
    public void populateSharedSeedTable(){
        
         DefaultTableModel model = (DefaultTableModel) seedJTable.getModel();
        
        model.setRowCount(0);
               
        for(Crop crop: farmerOrganization.getSharedCropCatalog().getCropList())
         {
                 
             
            Object[] row = new Object[2];
            
            row[0] = crop;
            row[1] = "-";
           
       
            model.addRow(row);
            //System.err.println("loaded table "+model.getRowCount());
        } 
        
        
    }
    
    private static void copyFileUsingJava7Files(File source, File dest) throws IOException {
    Files.copy(source.toPath(), dest.toPath());
}
   
    
    public void populateDocumentTable(Expert selectedExpert){
        
        
      DefaultTableModel model = (DefaultTableModel) documentJTable.getModel();
        
        model.setRowCount(0);
               
        for(Training trainingMaterial: selectedExpert.getExperTrainingCatalog().getTrainingList())
         {
                 
           String[] docPathArray = trainingMaterial.getReferenceDocumentPath().split("\\\\");
        int index  = docPathArray.length-1;
        String docName = docPathArray[index];  
            Object[] row = new Object[3];
            
            row[0] = trainingMaterial;
            row[1] = docName;
            row[2] = trainingMaterial.getNumberOfDownloads();
           
       
            model.addRow(row);
            //System.err.println("loaded table "+model.getRowCount());
        } 
         
        
        
    }   
    ///////////////////----------------training-------------------//////////////////////////
    
    
    
    //////////////////////////inventory////////////////////////////////////////////////////
    public void rentGadget(){
        
     Gadget gadgetToRent;

      int selectedRow = GadgetsAvailableTable.getSelectedRow();

        if (selectedRow >= 0) {
    
           gadgetToRent = (Gadget)GadgetsAvailableTable.getValueAt(selectedRow, 0);
           
            Gadget.GadgetType gadgetType= gadgetToRent.getTypeOfGadget();
          if(gadgetToRent.isOnRent()){ 
      
           if(!isPresentGadget(gadgetType)){// ## check if update available
              // owners gadget undergoes changes
               
               ((Farmer)employee).getMyGadgetCatalog().borrowGadget(gadgetToRent, ((Farmer)employee));

               WorkRequest wr = new WorkRequest();
               
               wr.setIsTemporary(false);
             
           
           wr.setMessage("Your "+gadgetToRent.getName() +" has been rented by "+gadgetToRent.getBorrower().getName());
           wr.setRequestTitle("Gagdet Rented : NOTICE");
           wr.setSender(account);
            wr.setStatus("Sent");
          for(UserAccount ua: farmerOrganization.getUserAccountDirectory().getUserAccountList())
          {
              if(ua.getEmployee() == gadgetToRent.getLender() ){
                  
                 wr.setReceiver(ua); 
                  ua.getWorkQueue().getWorkRequestList().add(wr);
              }
          }
          

          
             populateMyGadgetsTable();  
                
        JOptionPane.showMessageDialog(this, "Gadget added successfully");
           }else{
               
        JOptionPane.showMessageDialog(this, "Gadget already added");     
           }

          }
          
          else{
          JOptionPane.showMessageDialog(null, "Gadget already rented");     
              
          }
        } else {
            JOptionPane.showMessageDialog(null, "Choose a gadget");
          
        }
    }
    public void allowToggleOLD(){
     
        farmStateJToggleButton.addChangeListener(new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent event) {
            if (farmStateJToggleButton.isSelected()){
                farmStateJToggleButton.setText("Cultivated");
                farm.setUsage("Cultivated");
                
                
            } else {
                farmStateJToggleButton.setText("Fallow");
               
                farm.setUsage("Fallow");
   
            }
        }
    });
 }
 
  public void allowToggle(){
     
       
            if (farmStateJToggleButton.isSelected()){
                farmStateJToggleButton.setText("Cultivated");
                farm.setUsage("Cultivated");
                
                
            } else {
                farmStateJToggleButton.setText("Fallow");
               
                farm.setUsage("Fallow");
   
            }
    
 }
 
 public void suggestLending(){
     
   WorkRequest wr = new WorkRequest();
           
           wr.setMessage("Farm is fallow. You might consider cultivating it or lending equipment. You might want to help other farmers. Lend your gadgets while you leave your farm fallow. ");
           wr.setRequestTitle("Fallow Farm : REMINDER");
           wr.setSender(account);
           wr.setReceiver(account);
           wr.setStatus("Sent");
           
           account.getWorkQueue().getWorkRequestList().add(wr);
           
           populateInbox();
     
 }
 
    public void addSeed(){
     Crop seedToBuy;
         int selectedRow = seedJTable.getSelectedRow();

        if (selectedRow >= 0) {
    
           seedToBuy = (Crop)seedJTable.getValueAt(selectedRow, 0);
      
           
      
           if(!isPresentSeed(seedToBuy)){// ## check if update available
             ((Farmer)employee).getMyCropCatalog().addCrop(seedToBuy);

            // gadgetType.getNumOfUsers().add((Farmer) employee);
          
               seedToBuy.getSeedUsers().add((Farmer) employee);
             
             usersJLabel.setText(String.valueOf(seedToBuy.getSeedUsers().size()+ " are using this seed"));
          
             
             populateMySeedTable();  
             populateCropCombo();
                
        JOptionPane.showMessageDialog(this, "Seed added successfully");
           }else{
               
        JOptionPane.showMessageDialog(this, "Seed already added");     
           }


        } else {
            JOptionPane.showMessageDialog(null, "Choose a Seed");
            return;
        }
 }
  
 public void populateMySeedTable(){
     
      DefaultTableModel model = (DefaultTableModel) mySeedTable.getModel();
        
        model.setRowCount(0);
               
        for(Crop crop: ((Farmer) employee).getMyCropCatalog().getCropList())
         {
                 
             
            Object[] row = new Object[2];
            
            row[0] = crop;
            row[1] = crop.getSupplier().getName();
           
       
            model.addRow(row);
            //System.err.println("loaded table "+model.getRowCount());
        } 
         
     
 }
 
    public void getGadgetsGoing(){

        
        for (Gadget g : ((Farmer)employee).getMyGadgetCatalog().getGadgetList()){
            
            if( g.getCurrentOwner() == ((Farmer)employee)){
            
            if(g.getTypeOfGadget().getStatus().equals("Started") && g.isOwned()){ // ## to do $$$$ check
                
                startGadget(g.getTypeOfGadget());
            }
            
            if(g.getTypeOfGadget() == Gadget.GadgetType.SMARTSCARECROW){
                
                scareCrowAlert(); 
            }
            
            if(g.getTypeOfGadget() == Gadget.GadgetType.SMARTIRRIGATOR){
                
                moistureLevelCheckAlert();
            }
           } // if im current owner
        }
    }
    
    public void  startGadget(Gadget.GadgetType type){
 
    //## do something about this code :(
    if(type.getValue().equals("Smart Irrigator")){
  
     farm.irrigateFarm();
      refreshInbox();
     showFarms();
  
    }else if(type == Gadget.GadgetType.SMARTSCARECROW){
       
     startScareCrowAlarm();
      refreshInbox();
     showFarms();
        
    }
    else if(type.getValue().equals("Weather Monitor")){
 
        climateJPanel.setVisible(true);
        climateInfoJLabel.setVisible(false);
  
        currentCF.setDate(new Date());
     network.getClimaticFactorHistory().getClimaticFactorList().add(currentCF);
     
     
//      temperatureJLabel.setText(String.valueOf(currentCF.getTemperature()));
//      humidityJLabel.setText(String.valueOf(currentCF.getHumidity()));
//      sunshineJLabel.setText(String.valueOf(currentCF.getSky()));
//      windyJLabel.setText(String.valueOf(currentCF.getWindSpeed()));
//        if(currentCF.isDry()){
//            
//            humidityJLabel.setText("DRY");
//        }else{
//           humidityJLabel.setText("HUMID"); 
//        }
//        if(currentCF.isSunny()){
//            sunshineJLabel.setText("SUNNY");
//        }else{
//           sunshineJLabel.setText("CLOUDY"); 
//        }
//         if(currentCF.isWindy()){
//            windyJLabel.setText("WINDY");
//        }else{
//           windyJLabel.setText("LESS WINDY"); 
//        }
    }
    
}
  
    public void moistureLevelCheckAlert(){
       int count = 0; 
       for(FarmSection f : farm.getFarmSectionList()){
         int size =  f.getSectionStatusHistory().getSectionStatusList().size();
          SectionStatus latestStatus = f.getSectionStatusHistory().getSectionStatusList().get(size-1);
          
          SectionStatus ss = new SectionStatus();
          ss.setMoistureLevel(latestStatus.getMoistureLevel());
          ss.setBirdOrAnimalPresent(latestStatus.isBirdOrAnimalPresent());
          ss.setIsPesticideApplied(latestStatus.isIsPesticideApplied());
          ss.setDate(new Date());
          ss.setIsSown(latestStatus.isIsSown());
          ss.setCf(currentCF);
          if(ss.isIsSown()){
              if(f.getCrop().getMoistureThreshold() > ss.getMoistureLevel()){
                  
             
        count++;
              }
          }
           f.getSectionStatusHistory().getSectionStatusList().add(ss);
           
       }
        if(count>0){
            
                  WorkRequest wr = new WorkRequest();
           
           wr.setMessage("Moisture level in certain sections is low");
           wr.setRequestTitle("Moisture level drop : ALERT");
           wr.setSender(account);
           wr.setReceiver(account);
           wr.setStatus("Sent");
           
           account.getWorkQueue().getWorkRequestList().add(wr);
           populateInbox();
        }
        
    }
    
    
    public void scareCrowAlert(){
        
        for(FarmSection f : farm.getFarmSectionList()){
            
           int size =  f.getSectionStatusHistory().getSectionStatusList().size();
          SectionStatus latestStatus = f.getSectionStatusHistory().getSectionStatusList().get(size-1);
          
          if(latestStatus.isBirdOrAnimalPresent()){
              
             WorkRequest wr = new WorkRequest();
           
           wr.setMessage("Bird / Animal (s) Found in farm");
           wr.setRequestTitle("Bird/Animal found : ALERT");
           wr.setSender(account);
           wr.setReceiver(account);
           wr.setStatus("Sent");
           
           account.getWorkQueue().getWorkRequestList().add(wr);
           break; 
          
          }
        }
       populateInbox();
    }
    
    public void startScareCrowAlarm(){
       int count = 0;
        for(FarmSection f : farm.getFarmSectionList()){
          int size =  f.getSectionStatusHistory().getSectionStatusList().size();
          SectionStatus latestStatus = f.getSectionStatusHistory().getSectionStatusList().get(size-1);
          
          if(latestStatus.isBirdOrAnimalPresent()){
              count++;
          }
          
           SectionStatus ss = new SectionStatus();
           ss.setMoistureLevel(latestStatus.getMoistureLevel());
           ss.setIsPesticideApplied(latestStatus.isIsPesticideApplied());
           ss.setIsSown(latestStatus.isIsSown());
           ss.setBirdOrAnimalPresent(false);
           ss.setCf(currentCF);
           ss.setDate(new Date());
           
           f.getSectionStatusHistory().getSectionStatusList().add(ss);
            
        }
     if(count >0){
         
          WorkRequest wr = new WorkRequest();
           
           wr.setMessage("Bird / Animal (s) frightened away from the farm");
           wr.setRequestTitle("Bird/Animal frightened : SUCCESS");
           wr.setSender(account);
           wr.setReceiver(account);
           wr.setStatus("Sent");
           
           account.getWorkQueue().getWorkRequestList().add(wr);
           
          
     }
    }

public void  stopGadget(Gadget.GadgetType type){
    //to do ####
     if(type.getValue().equals("Smart Irrigator")){
  
   //  ((Farmer) employee).getFarm().irrigateFarm();
     
  //   showFarms();
  
    }else if(type.getValue().equals("Swath Control")){
        
    }
    else if(type.getValue().equals("Weather Monitor")){
    climateJPanel.setVisible(false);
     climateInfoJLabel.setVisible(true);
    }
    
}




//////////////////////////////////////training/////////////////////////






//////////////////---training--------------///////////////////////////

public void addImagesToTabs(){

    Utility u = new Utility();
    
       myTabs.setIconAt(0, u.createImageIcon("images/home.png"));
        myTabs.setIconAt(1, u.createImageIcon("images/expert.png"));//expert
       myTabs.setIconAt(2, u.createImageIcon("images/bell.png"));
       myTabs.setIconAt(3, u.createImageIcon("images/settings.png"));
        myTabs.setIconAt(4, u.createImageIcon("images/watch.png"));
         myTabs.setIconAt(5,u. createImageIcon("images/inventory.png"));
        
       
 
    
}

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable GadgetsAvailableTable;
    private javax.swing.JButton NotificationsTabButton;
    private javax.swing.JButton TrainingTabButton;
    private javax.swing.JPanel ViewDiscussions;
    private javax.swing.JButton WatchFarmButton;
    private javax.swing.JButton addJButton;
    private javax.swing.JButton addSectionJButton;
    private javax.swing.JScrollPane allGadgetScrollPane;
    private javax.swing.JButton allQueriesJButton;
    private javax.swing.JPanel askExpert;
    private javax.swing.JPanel attendTrainingTabPanel;
    private javax.swing.JButton borrowSeedsJButton;
    private javax.swing.JLabel climateInfoJLabel;
    private javax.swing.JPanel climateJPanel;
    private javax.swing.JComboBox cropJComboBox;
    private javax.swing.JLabel degCelJLabel;
    private javax.swing.JTable documentJTable;
    private javax.swing.JLabel errQueryJLabel;
    private javax.swing.JLabel errTitleJLabel;
    private javax.swing.JComboBox expertJComboBox;
    private javax.swing.JTable farmJTable;
    private javax.swing.JLabel farmLabel;
    private javax.swing.JToggleButton farmStateJToggleButton;
    private javax.swing.JLabel farmerNameJlabel;
    private javax.swing.JSplitPane farmerWorkAreaJSplitPane;
    private javax.swing.JTextArea forumJTextArea;
    private javax.swing.JComboBox gadgetJComboBox;
    private javax.swing.JPanel gadgetSupplierJPanel;
    private javax.swing.JTable gadgetTable;
    private javax.swing.JTabbedPane gadgetsAndInventoryInnerPane;
    private javax.swing.JButton getBackJButton;
    private javax.swing.JButton getNewJButton;
    private javax.swing.JButton getSeedJButton;
    private javax.swing.JPanel headerJPanel;
    private javax.swing.JPanel homeTabPanel;
    private javax.swing.JLabel humidityJLabel;
    private javax.swing.JTable inboxJTable;
    private javax.swing.JButton inventoryButton;
    private javax.swing.JLabel inventoryHomeLabel;
    private javax.swing.JPanel inventoryTabbedPanel;
    private javax.swing.JSlider irrigationJSlider;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton5;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JButton lendGadgetJComboBox;
    private javax.swing.JLabel lenderNameJLabel;
    private javax.swing.JButton likeJButton;
    private javax.swing.JButton likeSeedJButton;
    private javax.swing.JLabel likersJlabel;
    private javax.swing.JPanel manageGadgetsTabPanel;
    private javax.swing.JPanel manageSeedTabbedPanel;
    private javax.swing.JTextArea messageDetailsArea;
    private javax.swing.JScrollPane myGadgetScrollPane;
    private javax.swing.JButton myQueriesJButton;
    private javax.swing.JTable mySeedTable;
    private javax.swing.JTabbedPane myTabs;
    private javax.swing.JLabel networkJLabel;
    private javax.swing.JLabel noOfUsersJLabel;
    private javax.swing.JLabel notificationHomeLabel;
    private javax.swing.JPanel notificationTabPanel;
    private javax.swing.JButton orderSeedsJButton;
    private javax.swing.JButton postJButton;
    private javax.swing.JTextArea postQueryJTextArea;
    private javax.swing.JTable queryResponseJTable;
    private javax.swing.JTextField queryTitleJTextField;
    private javax.swing.JLabel ratingJLabel;
    private javax.swing.JButton rentGadgetJButton;
    private javax.swing.JButton rentJButton;
    private javax.swing.JLabel rentJLabel;
    private javax.swing.JButton returnGadgetJButton;
    private javax.swing.JComboBox seedJComboBox;
    private javax.swing.JTable seedJTable;
    private javax.swing.JPanel seedSupplierPanel;
    private javax.swing.JLabel setFarmUsageLabel;
    private javax.swing.JPanel settingsTabPanel;
    private javax.swing.JButton shareSeedJButton;
    private javax.swing.JLabel shareSeedsLabel;
    private javax.swing.JButton startStopJButon;
    private javax.swing.JLabel sunshineIconLabel;
    private javax.swing.JLabel sunshineJLabel;
    private javax.swing.JLabel suppSeedJLabel;
    private javax.swing.JLabel suppliedByJLabel;
    private javax.swing.JComboBox supplierJComboBox;
    private javax.swing.JComboBox supplierJComboBox1;
    private javax.swing.JLabel supplierJLabel;
    private javax.swing.JLabel temperatureJLabel;
    private javax.swing.JPanel trainingDocumentsTabbedPane;
    private javax.swing.JLabel trainingHomeJLabel;
    private javax.swing.JPanel usersAndlikersJPanel;
    private javax.swing.JLabel usersJLabel;
    private javax.swing.JPanel viewDetailsSubPanel;
    private javax.swing.JPanel watchFarmTabPanel;
    private javax.swing.JLabel watchfarmHomeLabel;
    private javax.swing.JLabel windyJLabel;
    // End of variables declaration//GEN-END:variables
}