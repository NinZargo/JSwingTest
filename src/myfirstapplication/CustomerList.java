package myfirstapplication;

import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class CustomerList {
    ArrayList<Person> Clients;

    public CustomerList(String filename){
        Clients = new ArrayList<Person>();

        this.LoadFromFile(filename);
    }

    public void addClient(Person src){
        Clients.add(src);
    }

    public void removeClient(Person src){
        Clients.remove(src);
    }

    public void removeClient(String Surname){
        for ( int i = 0; i < Clients.size(); i++){
            if ( Clients.get(i).getSurname().equals(Surname)){
                Clients.remove(i);
            }
        }
    }

    public Person find(Person src) {
        for (Person tempPerson : Clients) {
            if(Objects.equals(src.getName(), tempPerson.getName()) && Objects.equals(src.getDOB(), tempPerson.getDOB())){
                return tempPerson;
            }
        }
        return null;
    }

    public void Display(JTextArea jClientsTextArea){
        for ( int i = 0; i < Clients.size(); i++){
            Clients.get(i).Display(jClientsTextArea);

            jClientsTextArea.append("\n\n##\n\n");
        }
    }

    public String[][] convertToArray(){
        int rowCount = Clients.size();
        String [][] resultArray = new String[rowCount][9];

        for (int i = 0; i < rowCount; i++){
            Person temp = Clients.get(i);
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
                for(int i = 0; i < 9; i++){
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

                Person tempPerson = new Person();
                tempPerson.Edit(split[0], split[1], split[2], split[3]);
                tempPerson.editAddress("", Integer.valueOf(split[4]), split[5], "", split[6], split[7], split[8]);

                this.addClient(tempPerson);
            }

        } catch ( IOException ioe){
        }
    }


}
