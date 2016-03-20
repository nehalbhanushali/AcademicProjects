/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.WorkQueue;

import java.util.ArrayList;

/**
 *
 * @author nehal
 */
public class ResponseDirectory {
    
    private ArrayList<Response> responseList;
    
    public ResponseDirectory(){
        
        responseList = new ArrayList<>();
    }

    public ArrayList<Response> getResponseList() {
        return responseList;
    }

    public void setResponseList(ArrayList<Response> responseList) {
        this.responseList = responseList;
    }
    
    
    
}
