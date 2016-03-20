/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.net.URL;
import java.util.Random;
import javax.swing.ImageIcon;

/**
 *
 * @author nehal
 */
public class Utility {
    public static boolean isValidName(String s){
        
        return s.matches("[a-zA-Z\\s]*$");
    }
    
    public static boolean isValidAlphanumericName(String s){
        
        return s.matches("[a-zA-Z0-9\\s]*$");
    }
    
    public static boolean notLongerThanRequired(int stringLength, int requiredLength){
        
        return (requiredLength > stringLength);
    }
    
    public static boolean hasSpaces(String s){
        
       return s.matches("^\\\\s*$");
    } 
    
    public static boolean validPassword(String s){
        
        return s.matches("[0-9a-zA-Z_&^%$#!~@-]*$");
    }
    
        public static boolean isNumeric(String s){
        
         if(!s.equals("")){
       
          
          //  System.out.println("age "+ageJTextField.getText());
         try{ 
               int num = Integer.parseInt(s); 
            
             return true;
                
            
        }
        
        catch(Exception e){
           
         return false;
            
        }
    } else{
                
   return true;
                }
    }
    
    public static boolean isAlphanumeric(String s){
       boolean result = s.matches("^[a-zA-Z0-9]*$");
        return s.matches("^[a-zA-Z0-9]*$");
        
    }
    
  
    public static boolean isAlpha(String name) {
    
    if (name.equals("")){
        
        return true;
    }
    return name.matches("^[a-zA-Z]*$");
} 
    

     public static int randInt(int min, int max) {

    // Usually this can be a field rather than a method variable
    Random rand = new Random();

    // nextInt is normally exclusive of the top value,
    // so add 1 to make it inclusive
    int randomNum = rand.nextInt((max - min) + 1) + min;

    return randomNum;
}
     
     
         /** Returns an ImageIcon, or null if the path was invalid. */
    public ImageIcon createImageIcon(String path) {
      //  java.net.URL imgURL = FarmerWorkAreaJPanel.class.getResource(path);
  
      URL imgURL = getClass().getResource(path);     
        
     // URL imgURL1 =  new URL("//UserInterface//images//arrow.jpg");
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
        
 
    }
    
}
