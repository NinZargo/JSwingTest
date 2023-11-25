package myfirstapplication;

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

@Getter @Setter
public abstract class Account implements Serializable {

    protected String SortCode;
    protected Integer AccountNo;
    protected Double Balance;
    protected String NameOfBank;
    protected Double Rate;
    protected String filename;
    protected LocalDate LastReportedDate;
    private static final long serialVersionUID = 1L;

    public Account() {
        SortCode = "";
        AccountNo = 0;
        Balance = 0.0;
        NameOfBank = "";
        Rate = 0.0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy"); // As DateTimeFormatter is not serializable it must be declared locally
        LastReportedDate = LocalDate.parse("1/01/2000", formatter);

        filename = ("Accounts/" + this.AccountNo + "_" + this.SortCode + "Account.txt");
    }

    public Account(int AccountNo, String SortCode) {
        this.SortCode = SortCode;
        this.AccountNo = AccountNo;
        NameOfBank = "";
        Rate = 0.0;
        LastReportedDate = LocalDate.parse("01/01/1900");

        filename = ("Accounts/" + this.AccountNo + "_" + this.SortCode + "Account.txt");
    }

    public Account(String SortCode, String NameOfBank, Double Rate) {
        this.SortCode = SortCode;
        this.NameOfBank = NameOfBank;
        this.Rate = Rate;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
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

        FileWriter writer;

        try {
            writer = new FileWriter(filename, true);
            writer.write(this.NameOfBank + ", " + this.Rate + "\n"); // Apply File Header info

            System.out.println("File created: " + filename);
        } catch (IOException ioe) {
            System.out.println("Error in writing/creating file " + ioe);
        }
    }

    protected void Accessed() {
        LastReportedDate = LocalDate.now();
    }

    public void Deposit(double inAmount) {
        Balance += inAmount;
        this.Accessed();
        addToStatement(inAmount, "", "");
    }

    public void Withdraw(double outAmount) {
        Balance -= outAmount;
        this.Accessed();
        addToStatement(outAmount, "", "");
    }

    public void outTransfer(double outAmount, @NotNull Account toAccount) {
        toAccount.Deposit(outAmount);
        this.Withdraw(outAmount);
        this.Accessed();
        addToStatement(outAmount, toAccount.getSortCode(), String.valueOf(toAccount.getAccountNo()));
    }

    public void interest(){
        LocalDate date = LocalDate.now();
        double interest;

        if(date.getDayOfMonth() == date.lengthOfMonth()){
            interest = (this.Balance * this.Rate) - this.Balance;
            this.Deposit(interest);
        }
    }

    protected void addToStatement(double Amount, String SortCode, String AccountNo) {
        FileWriter writer;

        boolean transactionType;

        try {
            writer = new FileWriter(filename, true);

            writer.write(LocalDateTime.now().toString() + ": ");

            if (Amount > 0) {
                writer.write("+ £");
                transactionType = true;
            } else {
                writer.write("- £");
                transactionType = false;
            }

            writer.write(Amount + "From: ");

            if (Objects.equals(AccountNo, "") && Objects.equals(SortCode, "")) {
                writer.write(transactionType ? "Deposit" : "Withdraw");
            } else {
                writer.write(AccountNo + " " + SortCode);
            }

            writer.write("\n" + this.Balance + ", " + this.Rate + ", " + this.NameOfBank + "\n");

            writer.flush();
            writer.close();
            writer = null;
        } catch (IOException ignored) {
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getName() + " ", " ")
                .add("\nSortCode='" + SortCode + "'")
                .add("\nAccountNo=" + AccountNo)
                .add("\nBalance=" + Balance)
                .add("\nNameOfBank='" + NameOfBank + "'")
                .add("\nRate=" + Rate)
                .add("\nfilename='" + filename + "'")
                .add("\nLastReportedDate=" + LastReportedDate)
                .toString();
    }

    public void Display(JTextArea src) {
        src.setLineWrap(true);
        src.append(toString());
    }
}