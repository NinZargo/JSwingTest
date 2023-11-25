package myfirstapplication;

import javax.swing.*;
import java.io.Serial;
import java.time.LocalDate;

public class ISAAccount extends Account{
    protected double MaximumLimitPerYear;
    protected double DepositThisYear;
    private static final long serialVersionUID = 1L;

    public ISAAccount(){
        super();
        MaximumLimitPerYear = 3250;
        DepositThisYear = 0;
    }
    public ISAAccount(String SortCode, String NameOfBank, Double Rate){
        super(SortCode, NameOfBank, Rate);
        MaximumLimitPerYear = 3250;
        DepositThisYear = 0;
    }

    @Override
    public void Deposit(double inAmount){
        DepositThisYear += inAmount;
        if( DepositThisYear > MaximumLimitPerYear){
            Balance += inAmount;
            super.Accessed();
            addToStatement(inAmount, "", "");
        }
    }

    public void Display(JTextArea src){
        src.setLineWrap(true);
        src.append("ISA Account : ");
        src.append(super.toString());
        src.append("\n Deposited This Year = " + DepositThisYear + "\n Maximum Limit Per Year = " + MaximumLimitPerYear + "\n");
    }

    public void interest(){
        double interest;

        if(LocalDate.now().getDayOfYear() == LocalDate.now().lengthOfYear()){
            interest = (this.Balance * this.Rate) - this.Balance;
            this.Deposit(interest);
        }
    }

    public String toString(){
        return "SortCode = " + SortCode + '\n' +
                ", AccountNo=" + AccountNo +
                ", \nBalance = " + Balance +
                ", \nNameOfBank = " + NameOfBank + '\n' +
                ", \nRate = " + Rate +
                ", \nLast Accessed=" + LastReportedDate;
    }
}
