package threads;

//NegativeAmountException.java: Negative amount exception
public class NegativeAmountException extends Exception {
/** Account information to be passed to the handlers */
private Account account;
private double amount;
private String transactionType;

/** Construct an negative amount exception */
public NegativeAmountException(Account account,
                              double amount,
                              String transactionType) {
 super("Negative amount");
 this.account = account;
 this.amount = amount;
 this.transactionType = transactionType;
}
}
