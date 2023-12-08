package myfirstapplication;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.io.Serial;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter @Setter
public class SavingAccount extends Account{
    private double fee;
    private Boolean reducedRate = false;
    @Serial
    private static final long serialVersionUID = 1L;

    public SavingAccount(int AccountNo, String SortCode){
        super(AccountNo, SortCode);
    }

    public SavingAccount(){
        super();
    }
    public SavingAccount(String SortCode, String NameOfBank, Double Rate){
        super(SortCode, NameOfBank, Rate);
    }
    @Override
    public void Withdraw(double outAmount){
        long daysDifference = ChronoUnit.DAYS.between(super.LastReportedDate, LocalDate.now());
        if(daysDifference < 90){
            fee += 10;
            if(!reducedRate){
                reducedRate = true;
                Rate -= 0.49;
            }
        }

        super.Withdraw(outAmount);

    }

    @Override
    public void interest(){
        long daysDifference = ChronoUnit.DAYS.between(super.LastReportedDate, LocalDate.now());

        if (daysDifference < 90) {
            super.interest();
        }
    }

    public void Display(JTextArea src){
        src.append("Savings Account : ");
        super.Display(src);
        src.append("\nFee: " + fee);
    }

}
