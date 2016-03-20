/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author nehal
 */
public class ReadFile {
    private String path ;
    private ArrayList<String> textRead ;
     
    public ReadFile(String filePath){
     path = filePath;    
     textRead = new  ArrayList<>();
    }
    
    public ArrayList<String> readFileContents() throws IOException
    {
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        while ( (line = br.readLine()) != null){
              textRead.add(line.trim());
       }
       br.close();
       fr.close();
       return textRead;
        
    }
}
