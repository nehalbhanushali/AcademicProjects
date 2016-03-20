/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Organization;

import BusinessLogic.Role.ExpertRole;
import BusinessLogic.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author nehal
 */
public class ExpertOrganization extends Organization{

   public ExpertOrganization() {
        super(Type.EXPERT.getValue());
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
       roles.add(new ExpertRole());
        return roles;
    }
    
}
