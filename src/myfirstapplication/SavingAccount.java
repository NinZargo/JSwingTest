package myfirstapplication;

import javax.swing.*;

public class SavingAccount extends Account{
    protected double WithdrawLimit;

    public SavingAccount(int AccountNo, String SortCode){
        super(AccountNo, SortCode);
        WithdrawLimit = 200;
    }
    @Override
    public void Withdraw(double outAmount){
        if(outAmount <= WithdrawLimit){
            Balance -= outAmount;
            super.Accessed();
            addToStatement(outAmount, "", "");
        }
    }

    public void DisplaySavings(JTextArea src){
        src.setLineWrap(true);
        src.append(super.toString());
        src.append("\nWithdraw Limit = " + WithdrawLimit + "\n");
    }

}
