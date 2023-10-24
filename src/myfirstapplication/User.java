/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myfirstapplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author ethan
 */
public class User {
    private String name;
    private String role;
    private String password;
    private String filename;
    
    public User(){
        filename = "login.txt";
        this.resetRole();
    }
    
    public Boolean isRegistered(String newName, String newPassword, String newRole){
        boolean isRegistered;
        name = newName;
        password = newPassword;
        FileWriter writer;
        
        if (!newRole.equals("")) {
            role = newRole;
        } 
        
        try{
            writer = new FileWriter(filename, true);
            writer.write(name+ " " + password + System.getProperty("line.separator"));
            writer.write(role + System.getProperty("line.separator"));
            writer.write("##"+System.getProperty("line.separator"));
            isRegistered = true;
            writer.flush();
            writer.close();
            writer = null;
        }catch (IOException ioe){
            isRegistered = false;
        }
        
        return isRegistered;
    }
    
    public void resetRole(){
        role = "Bank Employee";
    }
    
    public Boolean isUser(String newName, String newPassword){
        boolean isFound = false;
        name = newName;
        password = newPassword;
        String record;
        FileReader reader;
        
        try{
            reader = new FileReader(filename);
            BufferedReader bin = new BufferedReader(reader);
            record = new String();
            while ((record = bin.readLine()) != null){
                if(record.contentEquals(name + " " + password)){
                    isFound = true;
                    record = bin.readLine();
                    role = record;
                }
            }
            bin.close();
            bin = null;
        } catch ( IOException ioe){
            isFound = false;
        }
        return isFound;
    }
    
    public String getRole(){
        return(role);
    }
    
    public Boolean isManager(){
        return(role.equals("Bank Manager"));
    }
}
