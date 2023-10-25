/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myfirstapplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JTextArea;

/**
 *
 * @author ethan
 */
public class Branch {
    private String WorkingHours;
    private String SortCode;
    private Boolean Loaded;
    private Person Manager;
    private final IAddress theAddress;
    
    public final void Edit(String strWorkingHours, String strSortCode){
        if (!strSortCode.equals("")){
            SortCode = strSortCode;
        }
        
        WorkingHours = strWorkingHours;
        
    }
    
    
    // Branch cant exist without an address so it has been added to Constructor
    public Branch(String filename){
        theAddress = new IAddress();
        Manager = new Person();
        
        Loaded = this.LoadFromFile(filename);
    }
    
    public void assignManager(Person newManager){
        Manager = newManager;
    }
    
     public void addressEdit(String strname, String strhouse_name, Integer inthouse_no, String strstreet, String strarea, String strpost_code, String strtown, String strcountry) {
         theAddress.Edit(strname, strhouse_name, inthouse_no, strstreet, strarea, strpost_code, strtown, strcountry);
     }
    
    //@Override because toString is a built-in method
    @Override
    public String toString() {
        return(WorkingHours + "\n" + SortCode);
    }
    
    public Boolean SaveToFile(String filename){
        FileWriter writer;
        boolean succesful;
        
        try{
            writer = new FileWriter(filename, false);
            writer.write(this.toString());
            writer.write("\n" + Manager.getName());
            writer.write("\n" + theAddress.toFileString());
            writer.write("\n##\n");
            writer.flush();
            writer.close();
            writer = null;
            succesful = true;
        } catch (IOException ioe) {
            succesful = false;
        }
      return succesful;
    }
    
    public Boolean LoadFromFile(String filename) {
        boolean succesful;
        
        String record;
        FileReader reader;
        
        try{
            reader = new FileReader(filename);
            BufferedReader bin = new BufferedReader(reader);
            record = new String();
            
            // File read line by line
            WorkingHours = bin.readLine();
            SortCode = bin.readLine();
            
            String tempStr = bin.readLine();
            String[] split = tempStr.split("\\s+");
            
            Person fileManager = new Person();
            fileManager.Edit(split[0], split[1], "1/01/2000", "1/01/2000");
            
            this.assignManager(fileManager);
            
            this.addressEdit(bin.readLine(),"",Integer.valueOf(bin.readLine()),bin.readLine(),"",bin.readLine(),bin.readLine(),bin.readLine());
            
            bin.close();
            bin = null;
            
            succesful = true;
        } catch ( IOException ioe){
            succesful = false;
        }
        
        return succesful;
    }
    
    public void Display(JTextArea jAddressTextArea){
        jAddressTextArea.setLineWrap(true);
        jAddressTextArea.append(toString());
        
        jAddressTextArea.append(String.format("\n\nManager: %s\n\n", Manager.getName()));
        
        theAddress.Display(jAddressTextArea);
    }

    public String[] convertToArray(){
        String [] branchArray = new String[2];
        String [] resultArray = new String[16];

        branchArray[0] = WorkingHours;
        branchArray[1] = SortCode;

        String [] managerArray = new String[9];
        managerArray = Manager.convertToArray();

        String [] addressArray = new String[5];
        addressArray = theAddress.convertToArray();

        System.arraycopy(branchArray, 0, resultArray, 0, 2);
        System.arraycopy(managerArray, 0, resultArray, 2, 9);
        System.arraycopy(addressArray, 0, resultArray, 11, 5);

        return resultArray;
    }


}
