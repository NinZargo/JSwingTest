/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myfirstapplication;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JTextArea;

/**
 *
 * @author ethan
 */
public class Person {
    private String FirstName;
    private String Surname;
    private LocalDate DOB;
    private IAddress HomeAddress;
    private LocalDate CustomerSince;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    
    public Person(){
        
        HomeAddress = new IAddress();
        
        Edit("","","1/01/2000", "1/01/2000");
    }
    
    public void Edit(String strfirstname, String strsurname, String strdob, String strcustomersince) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        
        FirstName = strfirstname;
        Surname = strsurname;
        DOB = LocalDate.parse(strdob, formatter);
        CustomerSince = LocalDate.parse(strcustomersince, formatter);
        
    }
    
    public void editAddress(String strhouse_name, Integer inthouse_no, String strstreet, String strarea, String strpost_code, String strtown, String strcountry){
        HomeAddress.Edit((FirstName + " " + Surname), strhouse_name, inthouse_no, strstreet, strarea, strpost_code, strtown, strcountry);
    }
    
    @Override
    public String toString() {
        
        String DOBString = DOB.format(formatter);
        String CustomerSinceString = CustomerSince.format(formatter);
        
        
        return(FirstName + " " + Surname + ", \n" + DOBString + ", \n" + CustomerSinceString);
    }
    
    public boolean checkDOB(String strgivenDOB){
        LocalDate givenDOB = LocalDate.parse(strgivenDOB, formatter);
        
        return(givenDOB.equals(DOB));
    }
    
    public String getName(){
        return(String.format("%s %s", FirstName, Surname));
    }
    
    public void Display(JTextArea jAddressTextArea){
        jAddressTextArea.setLineWrap(true);
        jAddressTextArea.append(toString());
        
        jAddressTextArea.append("\n \n");
        
        HomeAddress.Display(jAddressTextArea);
    }        
}
