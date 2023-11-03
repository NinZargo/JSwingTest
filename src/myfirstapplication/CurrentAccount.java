package myfirstapplication;

import javax.swing.*;

public class CurrentAccount extends Account{
    protected double Overdraft;
    protected String Conditions;
    protected double AvailableBalance;

    public CurrentAccount(){
    }

    public CurrentAccount(int AccountNo, String SortCode){
        super(AccountNo, SortCode);

        Overdraft = 100.0;
    }

    public void CreateAccount(String SortCode, String BankName, Double Rate){
        super.create(SortCode, BankName, Rate);
    }

    public void DisplayCurrentAccount(JTextArea src){
        src.setLineWrap(true);
        src.append(super.toString());
        src.append("\n" + "Overdraft = " + Overdraft + "\n" + "Available Balance = " + AvailableBalance + "\n");
    }
}
