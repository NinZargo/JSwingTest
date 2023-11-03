package myfirstapplication;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;

public class Account {
    @Getter @Setter // Auto Implements Getters & Setters
    protected String SortCode;
    @Getter @Setter
    protected Integer AccountNo;
    @Getter @Setter
    protected Double Balance;
    @Setter
    protected String NameOfBank;
    @Setter
    protected Double Rate;
    protected String filename;
    @Getter
    protected LocalDate LastReportedDate;
    protected DateTimeFormatter dtf;

    public Account(){
        SortCode = "";
        AccountNo = 0;
        Balance = LoadfromFile();
        NameOfBank = "";
        Rate = 0.0;

        dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        LastReportedDate = LocalDate.parse("01/01/1900");

        filename = (this.AccountNo + "_" + this.SortCode + "Account.txt");
    }

    public Account(int AccountNo, String SortCode){
        this.SortCode = SortCode;
        this.AccountNo = AccountNo;
        Balance = LoadfromFile();
        NameOfBank = "";
        Rate = 0.0;

        dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        LastReportedDate = LocalDate.parse("01/01/1900");

        filename = (this.AccountNo + "_" + this.SortCode + "Account.txt");
    }
    public void updateFilename(){
        filename = (this.AccountNo + "_" + this.SortCode + "Account.txt");
    }
    public void create(String SortCode, String BankName, Double Rate){
        this.SortCode = SortCode;
        this.NameOfBank = BankName;
        this.Rate = Rate;

        Random rnd = new Random();
        AccountNo = 10000000 + rnd.nextInt(90000000); // Random 8 digit number
        filename = (this.AccountNo + "_" + this.SortCode + "Account.txt");

        FileWriter writer;

        try {
            writer = new FileWriter(filename, true);

            writer.write(this.NameOfBank + ", " + this.Rate + "\n"); // Apply File Header info
        } catch (IOException ignored) {}
    }

    protected void Accessed(){
        LastReportedDate = LocalDate.now();
    }

    public void Deposit(double inAmount){
        Balance += inAmount;
        this.Accessed();
        addToStatement(inAmount, "", "");
    }

    public void Withdraw(double outAmount){
        Balance -= outAmount;
        this.Accessed();
        addToStatement(outAmount, "", "");
    }

    public void DepositInterest(){
        double interest = (this.Balance * this.Rate) - this.Balance;
        this.Deposit(interest);
    }

    public void outTransfer(double outAmount, @NotNull Account toAccount){
        toAccount.Deposit(outAmount);
        this.Withdraw(outAmount);
        this.Accessed();
        addToStatement(outAmount, toAccount.getSortCode(), String.valueOf(toAccount.getAccountNo()));
    }

    protected void addToStatement(double Amount, String SortCode, String AccountNo){
        FileWriter writer;

        boolean transactionType;

        try{
            writer = new FileWriter(filename, true);

            writer.write(LocalDateTime.now().toString() + ": ");

            if(Amount > 0){
                writer.write("+ £");
                transactionType = true;
            } else {
                writer.write("- £");
                transactionType = false;
            }

            writer.write(Amount + "From: " );

            if (Objects.equals(AccountNo, "") && Objects.equals(SortCode, "")){
                writer.write(transactionType ? "Deposit" : "Withdraw");
            } else {
                writer.write(AccountNo + " " + SortCode);
            }

            writer.write("\n" + this.Balance + ", " + this.Rate + ", " + this.NameOfBank + "\n");

            writer.flush();
            writer.close();
            writer = null;
        }catch (IOException ignored){
        }
    }

    public double LoadfromFile(){
        FileReader reader;
        String last = "", line = "";

        try{
            reader = new FileReader(filename);
            BufferedReader bin = new BufferedReader(reader);

            line = bin.readLine();
            String[] split = line.split(", "); // Get Account Data from firstLine of file

            this.NameOfBank = split[0];
            this.Rate = Double.parseDouble(split[1]);

            while ((line = bin.readLine()) != null){
                last = line; // Get Last Line of file
            }
        } catch (IOException ioe){
            return(0.0);
        }

        return Double.parseDouble(last);
    }
    @Override
    public String toString() {
        return  "SortCode = " + SortCode + '\n' +
                ", AccountNo=" + AccountNo +
                ", \nBalance = " + Balance +
                ", \nNameOfBank = " + NameOfBank + '\n' +
                ", \nRate = " + Rate +
                ", \nLast Accessed=" + LastReportedDate;
    }

    public void Display(JTextArea src){
        src.setLineWrap(true);
        src.append(toString());
    }
}
