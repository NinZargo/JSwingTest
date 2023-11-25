package myfirstapplication;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class BranchList  implements Serializable {
    private ArrayList<Branch> Branches;
    private final String filename = "Branches/BranchesList.txt";

    public BranchList(){
        Branches = new ArrayList<Branch>();

        this.LoadFromFile();
    }

    public void addBranch(Branch src){
        Branches.add(src);
    }

    public void removeBranch(Branch src){
        Branches.remove(src);
    }

    public void Display(JTextArea jClientsTextArea){
        for (Branch branch : Branches) {
            branch.Display(jClientsTextArea);

            jClientsTextArea.append("\n\n##\n\n");
        }
    }

    public void SaveToFile(){
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this.Branches);

            oos.flush();
            oos.close();
        }
        catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "Error: " + ioe, "File Read Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    public void LoadFromFile(){
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            Object obj = in.readObject();

            if (obj instanceof ArrayList<?> && !((ArrayList<?>) obj).isEmpty() && ((ArrayList<?>) obj).get(0) instanceof Branch) {
                this.Branches = (ArrayList<Branch>) obj;
            } else { // Checking object cast
                JOptionPane.showMessageDialog(null, "File is empty or not of type Branch ", "File Read Error", JOptionPane.ERROR_MESSAGE);
            }


            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Branch Error: " + e, "File Read Error", JOptionPane.ERROR_MESSAGE);
        }

    }
}
