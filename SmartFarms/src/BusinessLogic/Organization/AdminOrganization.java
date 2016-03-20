/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Organization;

import BusinessLogic.Role.AdminRole;
import BusinessLogic.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author nehal
 */
public class AdminOrganization extends Organization{

    public AdminOrganization() {
        super(Type.ADMIN.getValue());
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new AdminRole());
        return roles;
    }
    
}
