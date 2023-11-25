package myfirstapplication;

import javax.swing.*;
import java.io.Serial;

public class CurrentAccount extends Account{
    protected double Overdraft;
    protected String Conditions;
    protected double AvailableBalance;
    @Serial
    private static final long serialVersionUID = 1L;

    public CurrentAccount(){
        super();

        Overdraft = 100.0;
    }
    public CurrentAccount(String SortCode, String NameOfBank, Double Rate){
        super(SortCode, NameOfBank, Rate);
        Overdraft = 100.0;
    }

    public void Display(JTextArea src){
        src.setLineWrap(true);
        src.append("Current Account : ");
        src.append(super.toString());
        src.append("\n" + "Overdraft = " + Overdraft + "Available Balance = " + AvailableBalance + "\n");
    }

    public void calculateAvailableBalance(){
        AvailableBalance = Balance + Overdraft;
    }

    @Override
    public void Withdraw(double outAmount){
        calculateAvailableBalance();

        if(outAmount > AvailableBalance) Withdraw(25);
        super.Withdraw(outAmount);
    }

}
