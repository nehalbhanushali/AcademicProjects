/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Organization;

import BusinessLogic.Role.Role;
import BusinessLogic.Role.SupplierRole;
import java.util.ArrayList;

/**
 *
 * @author nehal
 */
public class SupplierOrganization extends Organization{
    
       public SupplierOrganization() {
        super(Organization.Type.SUPPLIER.getValue());
    }
    
    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(new SupplierRole());
        return roles;
    }
    
    
}
