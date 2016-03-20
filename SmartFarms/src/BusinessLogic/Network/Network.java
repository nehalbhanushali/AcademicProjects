/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Network;

import BusinessLogic.Bean.ClimaticFactors;
import BusinessLogic.Bean.ClimaticFactorsHistory;
import BusinessLogic.Enterprise.EnterpriseDirectory;

/**
 *
 * @author nehal
 */
public class Network {
    
     private String name;
    private EnterpriseDirectory enterpriseDirectory;
       private ClimaticFactorsHistory climaticFactorHistory;

    public Network() {
        enterpriseDirectory = new EnterpriseDirectory();
        climaticFactorHistory = new ClimaticFactorsHistory();
    }

    public EnterpriseDirectory getEnterpriseDirectory() {
        return enterpriseDirectory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
       public ClimaticFactorsHistory getClimaticFactorHistory() {
        return climaticFactorHistory;
    }

    public void setClimaticFactorHistory(ClimaticFactorsHistory climaticFactorHistory) {
        this.climaticFactorHistory = climaticFactorHistory;
    }

    
    
    @Override
    public String toString() {
        return name;
    }
    
}
