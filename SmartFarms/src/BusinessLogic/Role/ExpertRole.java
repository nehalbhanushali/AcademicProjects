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
import UserInterface.ExpertRole.ExpertWorkAreaJPanel;
import javax.swing.JPanel;

/**
 *
 * @author nehal
 */
public class ExpertRole extends Role{
      
 
    
    @Override
    public String toString(){
        return "Expert";
    }

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer,
            UserAccount account, Organization organization,
            Enterprise enterprise, Network network,EcoSystem business) {
        return new ExpertWorkAreaJPanel(userProcessContainer,account,organization,network,business);
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
