/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLogic.Organization;

import BusinessLogic.Employee.EmployeeDirectory;
import BusinessLogic.Role.Role;
import BusinessLogic.UserAccount.UserAccountDirectory;
import BusinessLogic.WorkQueue.WorkQueue;
import java.util.ArrayList;

/**
 *
 * @author nehal
 */
public abstract class Organization {
    
    
    private String name;
    private WorkQueue workQueue;
    private EmployeeDirectory employeeDirectory;
    private UserAccountDirectory userAccountDirectory;
    private int organizationID;
    private static int counter;
    
   
    
       public Organization(String name) {
        this.name = name;
        
        workQueue = new WorkQueue();
        employeeDirectory = new EmployeeDirectory();
        userAccountDirectory = new UserAccountDirectory();
        organizationID = counter;
        ++counter;
    }

    
    public enum Type{
        ADMIN("Admin Organization") {
//            //@Override
//            public Organization createOrganization() {
//                return new AdminOrganization();
//            }
        }, FARMER("Farmer Organization"){
//            public Organization createOrganization() {
//                return new DoctorOrganization();
//            }
        }, EXPERT("Expert Organization"){
//            public Organization createOrganization() {
//                return new LabOrganization();
//            }
     
        },SUPPLIER("Supplier Organization"){
//            public Organization createOrganization() {
//                return new LabOrganization();
//            }
     
        };
        private String value;
        private Type(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
//        public Organization createOrganization(Type t) {
//            return t.createOrganization();
//        }
    }

 
    public abstract ArrayList<Role> getSupportedRole();
    
    public UserAccountDirectory getUserAccountDirectory() {
        return userAccountDirectory;
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public EmployeeDirectory getEmployeeDirectory() {
        return employeeDirectory;
    }
    
    public String getName() {
        return name;
    }

    public WorkQueue getWorkQueue() {
        return workQueue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWorkQueue(WorkQueue workQueue) {
        this.workQueue = workQueue;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
}
