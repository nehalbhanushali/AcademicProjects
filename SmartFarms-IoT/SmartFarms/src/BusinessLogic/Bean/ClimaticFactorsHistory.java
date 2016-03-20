/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Bean;

import java.util.ArrayList;

/**
 *
 * @author nehal
 */
public class ClimaticFactorsHistory {
    
    
    private ArrayList<ClimaticFactors> climaticFactorList;
    
    public ClimaticFactorsHistory(){
        
        climaticFactorList = new ArrayList<>();
    }

    public ArrayList<ClimaticFactors> getClimaticFactorList() {
        return climaticFactorList;
    }

    public void setClimaticFactorList(ArrayList<ClimaticFactors> climaticFactorList) {
        this.climaticFactorList = climaticFactorList;
    }
    
    
}
