/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myfirstapplication;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JTextArea;

/**
 *
 * @author ethan
 */

@Getter @Setter
public class Person implements Serializable {
    private String FirstName;
    private String Surname;
    private LocalDate DOB;
    private IAddress HomeAddress;

    private Account[] Accounts;
    private LocalDate CustomerSince;
    @Serial
    private static final long serialVersionUID = 1L;
    
    public Person(){
        
        HomeAddress = new IAddress();

        Accounts = new Account[4];

        Accounts[0] = new CurrentAccount();
        Accounts[1] = new ISAAccount();
        Accounts[2] = new SavingAccount();
        Accounts[3] = new SavingAccount();
        
        Edit("","","1/01/2000", "1/01/2000");
    }

    public void setDOB(String DOB) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        
        String DOBString = DOB.format(formatter);
        String CustomerSinceString = CustomerSince.format(formatter);
        
        
        return(FirstName + " " + Surname + ", \n" + DOBString + ", \n" + CustomerSinceString);
    }
    
    public boolean checkDOB(String strgivenDOB){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate givenDOB = LocalDate.parse(strgivenDOB, formatter);
        
        return(givenDOB.equals(DOB));
    }
    
    public void Display(JTextArea jAddressTextArea){
        jAddressTextArea.setLineWrap(true);
        jAddressTextArea.append(toString());
        
        jAddressTextArea.append("\n \n");
        
        HomeAddress.Display(jAddressTextArea);

        jAddressTextArea.append("\n \n");

        for(Account account : Accounts){
            account.Display(jAddressTextArea);
            jAddressTextArea.append("\n");
        }

        jAddressTextArea.append("\n ### \n\n");
    }

    public String[] convertToArray(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String [] personArray = new String[12];
        String [] resultArray = new String[17];

        personArray[0] = FirstName;
        personArray[1] = Surname;
        personArray[2] = DOB.format(formatter);
        personArray[3] = CustomerSince.format(formatter);

        int j = 0;

        for(int i = 4; i < 12 ; ++i){

            personArray[i] = String.valueOf(Accounts[j].getAccountNo());
            personArray[i++] = Accounts[j].getSortCode();

            j++;
        }


        String [] addressArray = new String[5];
        addressArray = HomeAddress.convertToArray();

        System.arraycopy(personArray, 0, resultArray, 0, 12);
        System.arraycopy(addressArray, 0, resultArray, 12, 5);

        return resultArray;
    }

    public void convertFromArray(String[] src){
        this.Edit(src[0], src[1], src[2], src[3]);

        String[] addressArray = new String[5];

        System.arraycopy(src, 4, addressArray, 0, 5);

        HomeAddress.convertFromArray(addressArray);
    }

    public void createISA(String SortCode, String BankName, Double Rate){
        ISAAccount isa = new ISAAccount(SortCode, BankName, Rate);
        isa.create();
        Accounts[3] = isa;
    }

    public void createSavings(String SortCode, String BankName, Double Rate1, Double Rate2){
        SavingAccount savings1 = new SavingAccount(SortCode, BankName, Rate1);
        savings1.create();
        SavingAccount savings2 = new SavingAccount(SortCode, BankName, Rate2);
        savings2.create();
        Accounts[1] = savings1;
        Accounts[2] = savings2;
    }

    public void createCurrent(String SortCode, String BankName, Double Rate){
        CurrentAccount current = new CurrentAccount(SortCode, BankName, Rate);
        current.create();
        Accounts[0] = current;
    }
}
