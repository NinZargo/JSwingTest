/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package myfirstapplication;

import lombok.Getter;
import lombok.Setter;

import javax.swing.JTextArea;
import java.io.Serializable;

/**
 *
 * @author ethan
 */
@Getter
@Setter
public class IAddress implements Serializable {
    private String name;
    private String house_name;
    private Integer house_no;
    private String street;
    private String area;
    private String post_code;
    private String town;
    private String country;
    
    public IAddress() {
        name = "";
        house_name = "";
        house_no = 0;
        street = "";
        area = "";
        post_code = "";
        town = "";
        country = "";
    }
    
    public void Display(JTextArea jAddressTextArea) {
        jAddressTextArea.setLineWrap(true);
        jAddressTextArea.append(toString());
    }
    
    public void Edit(String strname, String strhouse_name, Integer inthouse_no, String strstreet, String strarea, String strpost_code, String strtown, String strcountry) {
        name = strname;
        house_name = strhouse_name;
        house_no = inthouse_no;
        street = strstreet;
        area = strarea;
        post_code = strpost_code;
        town = strtown;
        country = strcountry;
    }
    
    @Override
    public String toString() {
        return( name + ", " + house_name + ", " + house_no + " " + street + ", \n" + area + ", " + post_code + ", \n" + town + ", " + country);
    }
    
    public String toFileString() {
        return( name + "\n" + String.valueOf(house_no) + "\n" + street + "\n" + post_code + "\n" + town + "\n" + country);
    }

    public String[] convertToArray(){
        String [] resultArray = new String[5];

        resultArray[0] = String.valueOf(house_no);
        resultArray[1] = street;
        resultArray[2] = town;
        resultArray[3] = post_code;
        resultArray[4] = country;

        return resultArray;
    }

    public void convertFromArray(String[] src){
        this.Edit("", "", Integer.valueOf(src[0]), src[1], "", src[2], src[3], src[4]);
    }
}
