/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.WorkQueue;

import BusinessLogic.Employee.Employee;
import java.util.Date;

/**
 *
 * @author nehal
 */
public class Response {
    
   private static int count = 1;
   
   private int responseNumber;
    private Employee responder;
    
    private String reply;
    
    private Date replyDate;
    
    
    public Response(){
        responseNumber = count++;
    }

    public Employee getResponder() {
        return responder;
    }

    public void setResponder(Employee responder) {
        this.responder = responder;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }
    
    
    
    
}
