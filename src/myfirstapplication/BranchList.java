package myfirstapplication;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BranchList {
    ArrayList<Branch> Branches;

    public BranchList(String filename){
        Branches = new ArrayList<Branch>();

        this.LoadFromFile(filename);
    }

    public void addBranch(Branch src){
        Branches.add(src);
    }

    public void removeBranch(Branch src){
        Branches.remove(src);
    }

    public void Display(JTextArea jClientsTextArea){
        for ( int i = 0; i < Branches.size(); i++){
            Branches.get(i).Display(jClientsTextArea);

            jClientsTextArea.append("\n\n##\n\n");
        }
    }

    public String[][] convertToArray(){
        int rowCount = Branches.size();
        String [][] resultArray = new String[rowCount][9];

        for (int i = 0; i < rowCount; i++){
            Branch temp = Branches.get(i);
            resultArray[i] = temp.convertToArray();
        }

        return resultArray;
    }

    public void SaveToFile(String filename){
        FileWriter writer;

        String[][] dataArray = this.convertToArray();

        try{
            writer = new FileWriter(filename, false);

            for(String[] row : dataArray){
                for(int i = 0; i < 16; i++){
                    writer.write(row[i] + ", ");
                }
                writer.write("\n");
            }

            writer.flush();
            writer.close();
            writer = null;
        }catch (IOException ioe){
        }
    }

    public void LoadFromFile(String filename){
        FileReader reader;
        String record;

        try{
            reader = new FileReader(filename);
            BufferedReader bin = new BufferedReader(reader);

            record = new String();

            while ((record = bin.readLine()) != null){
                String[] split = record.split(", ");

                Branch tempBranch = new Branch("");
                Person tempManager = new Person();

                tempManager.Edit(split[2], split[3], split[4], split[5]);
                tempManager.editAddress("", Integer.valueOf(split[6]), split[7], "", split[8], split[9], split[10]);

                tempBranch.assignManager(tempManager);

                tempBranch.Edit(split[0], split[1]);
                tempBranch.addressEdit("", "", Integer.valueOf(split[11]), split[12], "", split[14], split[13], split[15]);

                this.addBranch(tempBranch);
            }
        } catch ( IOException ioe){
        }

    }
}
