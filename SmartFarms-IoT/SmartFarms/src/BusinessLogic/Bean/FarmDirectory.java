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
public class FarmDirectory {
    
    private ArrayList<Farm> farmlist;
    
    
    public FarmDirectory(){
        
        farmlist = new ArrayList<>();
    }
    
    private Farm addFarm(){
        Farm f = new Farm();
        farmlist.add(f);
        return f;
    }
            
    
    
    
}
