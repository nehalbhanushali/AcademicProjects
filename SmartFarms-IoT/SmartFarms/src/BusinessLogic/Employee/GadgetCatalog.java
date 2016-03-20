/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Employee;

import BusinessLogic.Employee.Gadget.GadgetType;
import java.util.ArrayList;

/**
 *
 * @author nehal
 */
public class GadgetCatalog {
    
    private ArrayList<Gadget> gadgetList;
    
    
    
    public GadgetCatalog(){
        
        gadgetList = new ArrayList<>();
    }

    public ArrayList<Gadget> getGadgetList() {
        return gadgetList;
    }

    public void setGadgetList(ArrayList<Gadget> gadgetList) {
        this.gadgetList = gadgetList;
    }
    
    public Gadget addGadget(Gadget g,Supplier supplier,Farmer self){
        
        Gadget g2 = new Gadget();
        g2.setName(g.getName());
        g2.setDescription(g.getDescription());
        g2.setTypeOfGadget(g.getTypeOfGadget());
        g2.getTypeOfGadget().setStatus("Stopped");
        g2.setOnRent(false);
        g2.setOwned(true);
        g2.setCurrentOwner(self);
        g2.setSupplier(supplier);
        g2.setLender(self);
        // select gadget by type
        gadgetList.add(g2);
      
        return g2;
   }
    
    public Gadget borrowGadget(Gadget bg, Farmer borrower){
     
       bg.setOwned(true);
       bg.setOnRent(false);
       bg.setBorrower(borrower);
       bg.setCurrentOwner(borrower);
       bg.getTypeOfGadget().setStatus("Stopped");
       
                
        
     gadgetList.add(bg); 
     return bg;
    }
    
     public Gadget removeGadget(Gadget g){
         
        
        // select gadget by type
        gadgetList.remove(g);
      
        return g;
   }
    
    public Gadget createGadget(Gadget gadget){
    
         Gadget  g = new Gadget();
        g.setTypeOfGadget(gadget.getTypeOfGadget());
        g.setName(gadget.getName());
        
        gadgetList.add(g);
         
//        if (gadgetType.getValue().equals(GadgetType.SWATHCONTROL.getValue())){
//        //   gadget = new SwathControlGadget();
//            gadgetList.add(gadget);
//        }
//        else if (gadgetType.getValue().equals(GadgetType.IRRIGATOR.getValue())){
//        //    gadget = new IrrigatorOrganization();
//            gadgetList.add(gadget);
//        }
        return g;
    }
    
    
    
}
