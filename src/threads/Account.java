package threads;

public class Account {
	  // Two data fields in an account
	  private int id;
	  private double balance;

	  /** Construct an account with specified id and balance */
	  public Account(int id, double balance) {
	    this.id = id;
	    this.balance = balance;
	  }

	  /** Return id */
	  public int getId() {
	    return id;
	  }

	  /** Setter method for balance */
	  public void setBalance(double balance) {
	    this.balance = balance;
	  }

	  /** Return balance */
	  public double getBalance() {
	    return balance;
	  }

	  /** Deposit an amount to this account */
	  public void deposit(double amount)
	    throws NegativeAmountException {
	    if (amount < 0)
	      throw new NegativeAmountException
	        (this, amount, "deposit");
	    balance = balance + amount;
	  }

	  /** Withdraw an amount from this account */
	  public void withdraw(double amount)
	    throws NegativeAmountException, InsufficientFundException {
	    if (amount < 0)
	      throw new NegativeAmountException
	        (this, amount, "withdraw");
	    if (balance < amount)
	      throw new InsufficientFundException(this, amount);
	    balance = balance - amount;
	  }
	}
