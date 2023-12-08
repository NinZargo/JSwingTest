package myfirstapplication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;
import java.util.StringJoiner;

@Getter @Setter @AllArgsConstructor
public abstract class Account implements Serializable {

    protected String SortCode;
    protected Integer AccountNo;
    protected Double Balance;
    protected String NameOfBank;
    protected Double Rate;
    protected String filename;
    protected Integer transactions;
    protected LocalDate LastReportedDate;
    protected transient DateTimeFormatter formatter; // transient stops it being used for serialization
    @Serial
    private static final long serialVersionUID = 1L;

    public Account() {
        SortCode = "";
        AccountNo = 0;
        Balance = 0.0;
        NameOfBank = "";
        Rate = 0.0;

        formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LastReportedDate = LocalDate.parse("1/01/2000", formatter);

        filename = ("Accounts/" + this.AccountNo + "_" + this.SortCode + "Account.txt");
    }

    public Account(int AccountNo, String SortCode) {
        this.SortCode = SortCode;
        this.AccountNo = AccountNo;

        Balance = 0.0;
        NameOfBank = "";
        Rate = 0.0;
        formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        LastReportedDate = LocalDate.parse("01/01/1900", formatter);

        filename = ("Accounts/" + this.AccountNo + "_" + this.SortCode + "Account.txt");
    }

    public Account(String SortCode, String NameOfBank, Double Rate) {
        this.SortCode = SortCode;
        this.NameOfBank = NameOfBank;
        this.Rate = Rate;

        this.Balance = 0.0;

        formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LastReportedDate = LocalDate.parse("01/01/1900", formatter);

        filename = ("Accounts/" + this.AccountNo + "_" + this.SortCode + "Account.txt");
    }

    public void updateFilename() {
        filename = ("Accounts/" + this.AccountNo + "_" + this.SortCode + "Account.txt");
    }

    public void create() {
        Random rnd = new Random();
        AccountNo = 10000000 + rnd.nextInt(90000000); // Random 8 digit number
        filename = ("Accounts/" + this.AccountNo + "_" + this.SortCode + this.getClass().getSimpleName() + ".txt");
    }

    protected void Accessed() {
        LastReportedDate = LocalDate.now();
    }

    public void Deposit(double inAmount) {
        Balance += inAmount;
        this.Accessed();
        transactions++;
    }

    public void Withdraw(double outAmount) {
        Balance -= outAmount;
        this.Accessed();
        transactions++;
    }

    public void outTransfer(double outAmount, @NotNull Account toAccount) {
        toAccount.Deposit(outAmount);
        this.Withdraw(outAmount);
        this.Accessed();
        transactions++;
    }

    public boolean isEndOfMonth() {
        LocalDate date = LocalDate.now();
        double interest;

        return date.getDayOfMonth() == date.lengthOfMonth();
    }

    public void interest(){
        if(isEndOfMonth()){
            double interest = (this.Balance * this.Rate) - this.Balance;
            this.Deposit(interest);
        }
    }

    public void endMonthUtil(JTextField field){
        String statement;
        if(isEndOfMonth()){
            field.setText("Transactions : " + this.transactions + " Balance : £" + this.Balance + this.getClass().getSimpleName());
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        return new StringJoiner(", ", this.getClass().getSimpleName() + " ", " ")
                .add(SortCode)
                .add(String.valueOf(AccountNo))
                .add("£" + Balance)
                .add(NameOfBank)
                .add(String.valueOf(Rate))
                .add(LastReportedDate.format(formatter))
                .toString();
    }

    public void Display(JTextArea src) {
        src.setLineWrap(true);
        src.append(toString());
    }
}