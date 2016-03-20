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
public class SectionStatusHistory {
    
    private ArrayList<SectionStatus> sectionStatusList;
    
    public SectionStatusHistory(){
        
     sectionStatusList = new ArrayList<>();
    }

    public ArrayList<SectionStatus> getSectionStatusList() {
        return sectionStatusList;
    }

    public void setSectionStatusList(ArrayList<SectionStatus> sectionStatusList) {
        this.sectionStatusList = sectionStatusList;
    }
    
    
    
}
