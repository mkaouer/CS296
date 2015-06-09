package threads;

//InsufficientFundException.java: An exception class for describing
//insufficient fund exception
public class InsufficientFundException extends Exception 
{
/** Information to be passed to the handlers */
private Account account;
private double amount;

/** Construct an insufficient exception */
public InsufficientFundException(Account account, double amount) {
 super("Insufficient amount");
 this.account = account;
 this.amount = amount;
}

/** Override the "toString" method */
public String toString() {
 return "Account balance is " + account.getBalance();
}
}
