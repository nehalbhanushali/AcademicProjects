/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Role;

import BusinessLogic.EcoSystem;
import BusinessLogic.Enterprise.Enterprise;
import BusinessLogic.Network.Network;
import BusinessLogic.Organization.Organization;
import BusinessLogic.UserAccount.UserAccount;
import javax.swing.JPanel;

/**
 *
 * @author nehal
 */
public abstract class Role {

   
    
    public enum RoleType{
         ADMIN("Admin"),
        FARMER("Farmer"),
        SUPPLIER("Supplier"),
        EXPERT("Expert");
        
        private String value;
        private RoleType(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
    
      
    public abstract JPanel createWorkArea(JPanel userProcessContainer, 
            UserAccount account, 
            Organization organization, 
            Enterprise enterprise, 
            Network network,
            EcoSystem business);
    @Override
    public String toString() {
        return this.getClass().getName();
    }
    
    
}
