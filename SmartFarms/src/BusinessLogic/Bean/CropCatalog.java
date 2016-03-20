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
public class CropCatalog {
    
    private ArrayList<Crop> cropList;
    
    public CropCatalog(){
        
        cropList = new ArrayList<>();
    }

    public ArrayList<Crop> getCropList() {
        return cropList;
    }

    public void setCropList(ArrayList<Crop> cropList) {
        this.cropList = cropList;
    }
    
    public Crop addCrop(Crop c){
        
        
        cropList.add(c);
        return c;
    }
    
    
    
}
