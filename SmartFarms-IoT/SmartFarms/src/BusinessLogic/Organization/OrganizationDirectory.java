/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Organization;

import BusinessLogic.Organization.Organization.Type;
import java.util.ArrayList;

/**
 *
 * @author nehal
 */
public class OrganizationDirectory {
    
    private ArrayList<Organization> organizationList;

    public OrganizationDirectory() {
        organizationList = new ArrayList<>();
    }

    public ArrayList<Organization> getOrganizationList() {
        return organizationList;
    }
    
    public Organization createOrganization(Type type){
        Organization organization = null;
        if (type.getValue().equals(Type.FARMER.getValue())){
            organization = new FarmerOrganization();
            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.EXPERT.getValue())){
            organization = new ExpertOrganization();
            organizationList.add(organization);
        }
        else if (type.getValue().equals(Type.SUPPLIER.getValue())){
            organization = new SupplierOrganization();
            organizationList.add(organization);
        }
       
         
        return organization;
    }
}