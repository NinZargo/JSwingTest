package myfirstapplication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

@Getter
@Setter
public class CustomerList extends AbstractListModel<Person> implements Serializable {
    ArrayList<Person> Clients;

    public CustomerList(String filename){
        Clients = new ArrayList<Person>();

        this.LoadFromFile(filename);
    }

    public void addClient(Person src){
        Clients.add(src);
        this.fireIntervalAdded(this, Clients.size() - 1, Clients.size() - 1);
    }

    public void removeClient(Person src){
        Clients.remove(src);
        this.fireIntervalRemoved(this, Clients.size() - 1, Clients.size() - 1);
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
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this.Clients);

            oos.flush();
            oos.close();
        }
        catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "Cust Error: " + ioe, "File Read Error", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(null, "Cust Error: " + ioe, "File Read Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    public void LoadFromFile(String filename){
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            Object obj = in.readObject();

            if (obj instanceof ArrayList<?> && !((ArrayList<?>) obj).isEmpty() && ((ArrayList<?>) obj).get(0) instanceof Person) {
                this.Clients = (ArrayList<Person>) obj;
            } else { // Checking object cast
                    JOptionPane.showMessageDialog(null, "File is empty or not of type Person ", "File Read Error", JOptionPane.ERROR_MESSAGE);
            }


            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "File Read Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    @Override
    public int getSize() {
        return Clients.size();
    }

    @Override
    public Person getElementAt(int index) {
        return Clients.get(index);
    }

    public int getIndexOf(Person person){
        return Clients.indexOf(person);
    }
}
