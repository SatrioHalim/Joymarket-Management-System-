package model;

public class Customer extends User{
	private double balance;

	public Customer(int id, String fullName, String email, String password, String phone, String address, String role,
			String gender,double balance) {
		super(id, fullName, email, password, phone, address, role,gender);
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	
	
}
