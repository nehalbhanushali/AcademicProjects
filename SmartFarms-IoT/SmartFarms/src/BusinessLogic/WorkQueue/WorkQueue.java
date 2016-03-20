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
public class WorkQueue {
    
    
    private ArrayList<WorkRequest> workRequestList;
    private ArrayList<WorkRequest> oldRequestList;
    
    
    
    public WorkQueue(){
        workRequestList = new ArrayList<WorkRequest>();
        oldRequestList = new ArrayList<WorkRequest>();
    }

    public ArrayList<WorkRequest> getWorkRequestList() {
        return workRequestList;
    }

    public void setWorkRequestList(ArrayList<WorkRequest> workRequestList) {
        this.workRequestList = workRequestList;
    }

    public ArrayList<WorkRequest> getOldRequestList() {
        return oldRequestList;
    }

    public void setOldRequestList(ArrayList<WorkRequest> oldRequestList) {
        this.oldRequestList = oldRequestList;
    }
    
    
}
