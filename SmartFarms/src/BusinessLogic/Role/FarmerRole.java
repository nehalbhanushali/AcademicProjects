/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Role;

import BusinessLogic.EcoSystem;
import BusinessLogic.Enterprise.Enterprise;
import BusinessLogic.Network.Network;
import BusinessLogic.Organization.FarmerOrganization;
import BusinessLogic.Organization.Organization;
import BusinessLogic.UserAccount.UserAccount;
import UserInterface.FarmerRole.FarmerWorkAreaJPanel;

import javax.swing.JPanel;

/**
 *
 * @author nehal
 */
public class FarmerRole extends Role{
  
    
    @Override
    public String toString(){
        return "Farmer";
    }

    @Override
     public JPanel createWorkArea(JPanel userProcessContainer,
            UserAccount account, Organization organization,
            Enterprise enterprise, Network network,EcoSystem business) {
       return new FarmerWorkAreaJPanel(userProcessContainer, account, (FarmerOrganization)organization,enterprise,network, business);
    }
    
   
}
