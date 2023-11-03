/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myfirstapplication;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JTextArea;

/**
 *
 * @author ethan
 */
public class Person {

    @Getter @Setter
    private String FirstName;
    @Getter @Setter
    private String Surname;
    @Getter
    private LocalDate DOB;
    @Getter
    private IAddress HomeAddress;
    private Account myAccount;
    private LocalDate CustomerSince;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    
    public Person(){
        
        HomeAddress = new IAddress();

        myAccount = new Account();
        
        Edit("","","1/01/2000", "1/01/2000");
    }

    public void setDOB(String DOB) {

        this.DOB = LocalDate.parse(DOB, formatter);;
    }

    public String getName(){
        return(String.format("%s %s", FirstName, Surname));
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
    
    public void Display(JTextArea jAddressTextArea){
        jAddressTextArea.setLineWrap(true);
        jAddressTextArea.append(toString());
        
        jAddressTextArea.append("\n \n");
        
        HomeAddress.Display(jAddressTextArea);
    }

    public String[] convertToArray(){
        String [] personArray = new String[4];
        String [] resultArray = new String[9];

        personArray[0] = FirstName;
        personArray[1] = Surname;
        personArray[2] = DOB.format(formatter);
        personArray[3] = CustomerSince.format(formatter);

        String [] addressArray = new String[5];
        addressArray = HomeAddress.convertToArray();

        System.arraycopy(personArray, 0, resultArray, 0, 4);
        System.arraycopy(addressArray, 0, resultArray, 4, 5);

        return resultArray;
    }

    public void convertFromArray(String[] src){
        this.Edit(src[0], src[1], src[2], src[3]);

        String[] addressArray = new String[5];

        System.arraycopy(src, 4, addressArray, 0, 5);

        HomeAddress.convertFromArray(addressArray);
    }
}
