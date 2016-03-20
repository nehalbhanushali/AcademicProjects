/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Enterprise;

import BusinessLogic.Role.Role;
import java.util.ArrayList;

/**
 *
 * @author nehal
 */
public class AgricultureEnterprise extends Enterprise{
    
    
      public AgricultureEnterprise(String name) {
        super(name, Enterprise.EnterpriseType.AGRICULTURE );
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        return null;
     //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
