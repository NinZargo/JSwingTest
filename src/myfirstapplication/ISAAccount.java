package myfirstapplication;

public class ISAAccount extends Account{
    protected double MaximumLimitPerYear;
    protected double DepositThisYear;

    public ISAAccount(int AccountNo, String SortCode){
        super(AccountNo, SortCode);
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
}
